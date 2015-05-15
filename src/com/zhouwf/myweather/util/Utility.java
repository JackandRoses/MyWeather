package com.zhouwf.myweather.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.zhouwf.myweather.db.MyWeatherDbOPerations;
import com.zhouwf.myweather.db.dbfield.CityTbFields;
import com.zhouwf.myweather.db.dbfield.CountryTbFields;
import com.zhouwf.myweather.db.dbfield.ProvinceTbFields;
import com.zhouwf.myweather.model.City;
import com.zhouwf.myweather.model.Country;
import com.zhouwf.myweather.model.Province;
import com.zhouwf.myweather.model.WeatherInfo;

/**
 * @author zhouwf
 * 
 */
public class Utility {
  /**
   * ʡ����Ϣ��ȡ���洢
   * 
   * @param myWeatherDbOP DB������
   * @param fileName ʡ���ش����ļ���
   * @return ������(boolean)
   * @throws IOException
   * @throws XmlPullParserException
   */
  public synchronized static boolean handleProvincesResponse(MyWeatherDbOPerations myWeatherDbOP, String fileName) throws IOException, XmlPullParserException {
    if (!TextUtils.isEmpty(fileName)) {
      AssetManager assetManager = ApplicationContext.getContext().getAssets();
      InputStream is = assetManager.open(fileName);
      XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
      // xmlPullParserFactory.setNamespaceAware(true);
      XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
      xmlPullParser.setInput(is, "utf-8");
      List<Province> provinceList = new ArrayList<Province>();
      Province province = null;
      int eventType = xmlPullParser.getEventType();
      while (eventType != XmlPullParser.END_DOCUMENT) {
        if (eventType == XmlPullParser.START_TAG) {
          if (ProvinceTbFields.TB_NAME.toLowerCase(Locale.US).equals(xmlPullParser.getName())) {
            province = new Province();
            province.setProvinceCode(xmlPullParser.getAttributeValue(null, "id"));
            province.setProvinceName(xmlPullParser.getAttributeValue(null, "name"));
          }
        }
        if (eventType == XmlPullParser.END_TAG) {
          if (ProvinceTbFields.TB_NAME.toLowerCase(Locale.US).equals(xmlPullParser.getName())) {
            provinceList.add(province);
          }
        }
        eventType = xmlPullParser.next();
      }
      // �洢����
      for (Province eachProvince : provinceList) {
        myWeatherDbOP.saveProvince(eachProvince);
      }
      return true;
    }
    return false;
  }

  /**
   * �м���Ϣ��ȡ���洢
   * 
   * @param myWeatherDbOP DB������
   * @param fileName ʡ���ش����ļ���
   * @param provinceId ʡID
   * @param provinceCode ʡCode
   * @return ������(boolean)
   * @throws IOException
   * @throws XmlPullParserException
   */
  public synchronized static boolean handleCitiesResponse(MyWeatherDbOPerations myWeatherDbOP, String fileName, int provinceId, String provinceCode) throws IOException, XmlPullParserException {
    if (!TextUtils.isEmpty(fileName)) {
      AssetManager assetManager = ApplicationContext.getContext().getAssets();
      InputStream is = assetManager.open(fileName);
      XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
      // xmlPullParserFactory.setNamespaceAware(true);
      XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
      xmlPullParser.setInput(is, "utf-8");
      List<City> cityList = new ArrayList<City>();
      City city = null;
      int eventType = xmlPullParser.getEventType();
      while (eventType != XmlPullParser.END_DOCUMENT) {
        if (eventType == XmlPullParser.START_TAG) {
          // �жϳ���ID�Ƿ��Ӧʡ��ID
          if (CityTbFields.TB_NAME.toLowerCase(Locale.US).equals(xmlPullParser.getName()) && xmlPullParser.getAttributeValue(null, "id").substring(0, 2).equals(provinceCode)) {
            city = new City();
            city.setProvinceOfCityId(provinceId);
            city.setCityCode(xmlPullParser.getAttributeValue(null, "id"));
            city.setCityName(xmlPullParser.getAttributeValue(null, "name"));
            cityList.add(city);
          }
        }
        // if (eventType == XmlPullParser.END_TAG) {
        // if
        // (CityTbFields.TB_NAME.toLowerCase(Locale.US).equals(xmlPullParser.getName()))
        // {
        // cityList.add(city);
        // }
        // }
        eventType = xmlPullParser.next();
      }
      // �洢����
      for (City eachCity : cityList) {
        myWeatherDbOP.saveCity(eachCity);
      }
      return true;
    }
    return false;
  }

  /**
   * �ؼ���Ϣ��ȡ���洢
   * 
   * @param myWeatherDbOP DB������
   * @param fileName ʡ���ش����ļ���
   * @param cityId ��ID
   * @return ������(boolean)
   * @throws IOException
   * @throws XmlPullParserException
   */
  public synchronized static boolean handleCountriesResponse(MyWeatherDbOPerations myWeatherDbOP, String fileName, int cityId, String cityCode) throws IOException, XmlPullParserException {
    if (!TextUtils.isEmpty(fileName)) {
      AssetManager assetManager = ApplicationContext.getContext().getAssets();
      InputStream is = assetManager.open(fileName);
      XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
      // xmlPullParserFactory.setNamespaceAware(true);
      XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
      xmlPullParser.setInput(is, "utf-8");
      List<Country> countryList = new ArrayList<Country>();
      Country country = null;
      int eventType = xmlPullParser.getEventType();
      while (eventType != XmlPullParser.END_DOCUMENT) {
        if (eventType == XmlPullParser.START_TAG) {
          // �жϳ���ID�Ƿ��Ӧʡ��ID
          if (CountryTbFields.TB_NAME.toLowerCase(Locale.US).equals(xmlPullParser.getName()) && xmlPullParser.getAttributeValue(null, "id").substring(0, 4).equals(cityCode)) {
            country = new Country();
            country.setCityOfCountryId(cityId);
            country.setCountryCode(xmlPullParser.getAttributeValue(null, "id"));
            country.setCountryName(xmlPullParser.getAttributeValue(null, "name"));
            countryList.add(country);
          }
        }
        // if (eventType == XmlPullParser.END_TAG) {
        // if
        // (CountryTbFields.TB_NAME.toLowerCase(Locale.US).equals(xmlPullParser.getName()))
        // {
        // countryList.add(country);
        // }
        // }
        eventType = xmlPullParser.next();
      }
      // �洢����
      for (Country eachCountry : countryList) {
        myWeatherDbOP.saveCountry(eachCountry);
      }
      return true;
    }
    return false;
  }

  /**
   * �����ӷ�����������JSON����
   * 
   * @throws JSONException
   */
  public static void handleWeatherInfoResponse(Context context, String response) throws JSONException {
    JSONObject jsonObject = new JSONObject(response);
    JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
    WeatherInfo wInfo = new WeatherInfo();
    wInfo.setCityname(weatherInfo.getString("city"));
    wInfo.setWeatherCode(weatherInfo.getString("cityid"));
    wInfo.setTemp1(weatherInfo.getString("temp1"));
    wInfo.setTemp2(weatherInfo.getString("temp2"));
    wInfo.setWeatherDesp(weatherInfo.getString("weather"));
    wInfo.setPublishTime(weatherInfo.getString("ptime"));
    // �������������ݱ��浽sharePreference��
    saveWeatherInfo(context, wInfo);

  }

  /**
   * �������������ݱ��浽sharePreference��
   * 
   * @param wInfo
   */
  private static void saveWeatherInfo(Context context, WeatherInfo wInfo) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy��M��d��", Locale.CHINA);
    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    editor.putBoolean("city_selected", true);
    editor.putString("city_name", wInfo.getCityname());
    editor.putString("weather_code", wInfo.getWeatherCode());
    editor.putString("temp1", wInfo.getTemp1());
    editor.putString("temp2", wInfo.getTemp2());
    editor.putString("weather_desp", wInfo.getWeatherDesp());
    editor.putString("publish_time", wInfo.getPublishTime());
    editor.putString("current_date", simpleDateFormat.format(new Date()));
    editor.commit();
  }
}