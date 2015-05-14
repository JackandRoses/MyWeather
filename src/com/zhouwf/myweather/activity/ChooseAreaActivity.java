package com.zhouwf.myweather.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
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

  /*
   * (non-Javadoc)
   * 
   * @see android.app.Activity#onCreate(android.os.Bundle)
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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
          queryCities();
        }
      }
    });

  }

  /**
   * 查询选中省内的所有市 从数据库查询
   */
  protected void queryCities() {
    cityList = myWeatherDbOPerations.loadCity(selectedProvince.getProvinceId());
  }
}
