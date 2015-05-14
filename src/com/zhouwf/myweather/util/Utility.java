package com.zhouwf.myweather.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.res.AssetManager;
import android.text.TextUtils;

import com.zhouwf.myweather.db.MyWeatherDbOPerations;
import com.zhouwf.myweather.db.dbfield.CityTbFields;
import com.zhouwf.myweather.db.dbfield.CountryTbFields;
import com.zhouwf.myweather.db.dbfield.ProvinceTbFields;
import com.zhouwf.myweather.model.City;
import com.zhouwf.myweather.model.Country;
import com.zhouwf.myweather.model.Province;

/**
 * @author zhouwf
 *
 */
public class Utility {
  /**
   * 省级信息获取及存储
   * 
   * @param myWeatherDbOP DB操作类
   * @param fileName 省市县代号文件名
   * @return 处理结果(boolean)
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
      // 存储操作
      for (Province eachProvince : provinceList) {
        myWeatherDbOP.saveProvince(eachProvince);
      }
      return true;
    }
    return false;
  }

  /**
   * 市级信息获取及存储
   * 
   * @param myWeatherDbOP DB操作类
   * @param fileName 省市县代号文件名
   * @param provinceId 省ID
   * @return 处理结果(boolean)
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
          // 判断城市ID是否对应省份ID
          if (CityTbFields.TB_NAME.toLowerCase(Locale.US).equals(xmlPullParser.getName()) && xmlPullParser.getAttributeValue(null, "id").substring(0, 2).equals(provinceCode)) {
            city = new City();
            city.setProvinceOfCityId(provinceId);
            city.setCityCode(xmlPullParser.getAttributeValue(null, "id"));
            city.setCityName(xmlPullParser.getAttributeValue(null, "name"));
          }
        }
        if (eventType == XmlPullParser.END_TAG) {
          if (CityTbFields.TB_NAME.toLowerCase(Locale.US).equals(xmlPullParser.getName())) {
            cityList.add(city);
          }
        }
        eventType = xmlPullParser.next();
      }
      // 存储操作
      for (City eachCity : cityList) {
        myWeatherDbOP.saveCity(eachCity);
      }
      return true;
    }
    return false;
  }

  /**
   * 县级信息获取及存储
   * 
   * @param myWeatherDbOP DB操作类
   * @param fileName 省市县代号文件名
   * @param cityId 县ID
   * @return 处理结果(boolean)
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
          // 判断城市ID是否对应省份ID
          if (CountryTbFields.TB_NAME.toLowerCase(Locale.US).equals(xmlPullParser.getName()) && xmlPullParser.getAttributeValue(null, "id").substring(0, 2).equals(cityCode)) {
            country = new Country();
            country.setCityOfCountryId(cityId);
            country.setCountryCode(xmlPullParser.getAttributeValue(null, "id"));
            country.setCountryName(xmlPullParser.getAttributeValue(null, "name"));
          }
        }
        if (eventType == XmlPullParser.END_TAG) {
          if (CountryTbFields.TB_NAME.toLowerCase(Locale.US).equals(xmlPullParser.getName())) {
            countryList.add(country);
          }
        }
        eventType = xmlPullParser.next();
      }
      // 存储操作
      for (Country eachCountry : countryList) {
        myWeatherDbOP.saveCountry(eachCountry);
      }
      return true;
    }
    return false;
  }
}