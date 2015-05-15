package com.zhouwf.myweather.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myweather.R;
import com.zhouwf.myweather.db.MyWeatherDbOPerations;
import com.zhouwf.myweather.model.City;
import com.zhouwf.myweather.model.Country;
import com.zhouwf.myweather.model.Province;
import com.zhouwf.myweather.util.Districts;
import com.zhouwf.myweather.util.Utility;

public class ChooseAreaActivity extends Activity {
  public static final int LEVEL_PROVINCE = 0;
  public static final int LEVEL_CITY = 1;
  public static final int LEVEL_COUNTRY = 2;

  private ProgressDialog progressDialog;
  private TextView titleText;
  private ListView listView;
  private ArrayAdapter<String> adapter;
  private MyWeatherDbOPerations myWeatherDbOPerations;
  private List<String> dataList = new ArrayList<String>();

  /** provinceList */
  private List<Province> provinceList;

  /** cityList */
  private List<City> cityList;

  /** countryList */
  private List<Country> countryList;

  /** selectedProvince */
  private Province selectedProvince;

  /** selectedCity */
  private City selectedCity;

  /** currentLevel */
  private int currentLevel;

  /** handle result */
  private static boolean result = false;

  /*
   * (non-Javadoc)
   * 
   * @see android.app.Activity#onCreate(android.os.Bundle)
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    if (prefs.getBoolean("city_selected", false)) {
      Intent intent = new Intent(this, WeatherActivity.class);
      startActivity(intent);
      finish();
    }
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.choose_aera);
    listView = (ListView) findViewById(R.id.list_view);
    titleText = (TextView) findViewById(R.id.title_text);
    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
    listView.setAdapter(adapter);
    myWeatherDbOPerations = MyWeatherDbOPerations.getInstance();
    listView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (currentLevel == LEVEL_PROVINCE) {
          selectedProvince = provinceList.get(position);
          try {
            queryCities();
          } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
          }
        } else if (currentLevel == LEVEL_CITY) {
          selectedCity = cityList.get(position);
          try {
            queryCountries();
          } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
          }
        } else if (currentLevel == LEVEL_COUNTRY) {
          String countryCode = countryList.get(position).getCountryCode();
          Intent intent = new Intent(ChooseAreaActivity.this, WeatherActivity.class);
          intent.putExtra("country_code", countryCode);
          startActivity(intent);
          finish();
        }

      }
    });
    // 启动时加载省级数据
    try {
      queryProvinces();
    } catch (IOException | XmlPullParserException e) {
      e.printStackTrace();
    }
  }

  /**
   * 查询选中省内的所有市 从数据库查询
   * 
   * @throws XmlPullParserException
   * @throws IOException
   */
  protected void queryCities() throws IOException, XmlPullParserException {
    cityList = myWeatherDbOPerations.loadCity(selectedProvince.getProvinceId());
    if (cityList != null && cityList.size() > 0) {
      dataList.clear();
      for (City eachCity : cityList) {
        dataList.add(eachCity.getCityName());
      }
      // 通知listView刷新
      adapter.notifyDataSetChanged();
      listView.setSelection(0);
      titleText.setText(selectedProvince.getProvinceName());
      currentLevel = LEVEL_CITY;
    } else {
      queryFromXml(selectedProvince.getProvinceCode(), selectedProvince.getProvinceId(), Districts.CITY);
    }
  }

  /**
   * 查询所有省
   * 
   * @throws IOException
   * @throws XmlPullParserException
   */
  protected void queryProvinces() throws IOException, XmlPullParserException {
    provinceList = myWeatherDbOPerations.loadProvince();
    if (provinceList != null && provinceList.size() > 0) {
      dataList.clear();
      for (Province eachProvince : provinceList) {
        dataList.add(eachProvince.getProvinceName());
      }
      // 通知listView刷新
      adapter.notifyDataSetChanged();
      listView.setSelection(0);
      titleText.setText("中国");
      currentLevel = LEVEL_PROVINCE;
    } else {
      queryFromXml("0", 0, Districts.PROVINCE);
    }
  }

  /**
   * 查询所有区县
   * 
   * @throws IOException
   * @throws XmlPullParserException
   */
  protected void queryCountries() throws IOException, XmlPullParserException {
    countryList = myWeatherDbOPerations.loadCountry((selectedCity.getCityId()));
    if (countryList != null && countryList.size() > 0) {
      dataList.clear();
      for (Country eachCountry : countryList) {
        dataList.add(eachCountry.getCountryName());
      }
      // 通知listView刷新
      adapter.notifyDataSetChanged();
      listView.setSelection(0);
      titleText.setText(selectedCity.getCityName());
      currentLevel = LEVEL_COUNTRY;
    } else {
      queryFromXml(selectedCity.getCityCode(), selectedCity.getCityId(), Districts.COUNTRY);
    }

  }

  /**
   * 从xml中查询省市数据
   * 
   * @param code 省、市CODE
   * @param id 省、市ID
   * @param type 地区类型
   * @throws XmlPullParserException
   * @throws IOException
   */
  private void queryFromXml(final String code, final int id, final Districts type) throws IOException, XmlPullParserException {
    if (!TextUtils.isEmpty(code)) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          switch (type) {
          case CITY:
            try {
              if (Utility.handleCitiesResponse(myWeatherDbOPerations, "location.xml", id, code)) {
                result = true;
              }
            } catch (IOException | XmlPullParserException e) {
              e.printStackTrace();
            }
            break;
          case COUNTRY:
            try {
              if (Utility.handleCountriesResponse(myWeatherDbOPerations, "location.xml", id, code)) {
                result = true;
              }
            } catch (IOException | XmlPullParserException e) {
              e.printStackTrace();
            }
            break;
          case PROVINCE:
            try {
              if (Utility.handleProvincesResponse(myWeatherDbOPerations, "location.xml")) {
                result = true;
              }
            } catch (IOException | XmlPullParserException e) {
              e.printStackTrace();
            }
            break;
          default:
            // Toast.makeText(this, "数据加载失败!",
            // Toast.LENGTH_SHORT).show();
            break;
          }

          if (result) {
            // 通过runOnUiThread()回到主线程
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                switch (type) {
                case PROVINCE:
                  try {
                    queryProvinces();
                  } catch (IOException | XmlPullParserException e1) {
                  }
                  break;
                case CITY:
                  try {
                    queryCities();
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                  break;
                case COUNTRY:
                  try {
                    queryCountries();
                  } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                  }
                default:
                  break;
                }
              }
            });
          }
        }
      }).start();
    }
  }

  private void showProgressDialog() {
    if (progressDialog == null) {
      progressDialog = new ProgressDialog(this);
      progressDialog.setMessage("正在加载...");
      progressDialog.setCanceledOnTouchOutside(false);
    }
    progressDialog.show();
  }

  private void closeProgressDialog() {
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see android.app.Activity#onBackPressed()
   */
  @Override
  public void onBackPressed() {
    if (currentLevel == LEVEL_COUNTRY) {
      try {
        queryCities();
      } catch (IOException | XmlPullParserException e) {
        e.printStackTrace();
      }
    } else if (currentLevel == LEVEL_CITY) {
      try {
        queryProvinces();
      } catch (IOException | XmlPullParserException e) {
        e.printStackTrace();
      }
    } else {
      finish();
    }
  }

}