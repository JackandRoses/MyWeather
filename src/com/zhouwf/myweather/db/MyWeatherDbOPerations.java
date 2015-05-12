package com.zhouwf.myweather.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhouwf.myweather.db.dbfield.CityTbFields;
import com.zhouwf.myweather.db.dbfield.CountryTbFields;
import com.zhouwf.myweather.db.dbfield.ProvinceTbFields;
import com.zhouwf.myweather.model.City;
import com.zhouwf.myweather.model.Country;
import com.zhouwf.myweather.model.Province;
import com.zhouwf.myweather.util.ApplicationContext;

/**
 * DB ������
 * 
 * @author zhouwf
 *
 */
public class MyWeatherDbOPerations {

  /** DB_NAME */
  public static final String DB_NAME = "my_weather_db";

  /** DB_VERSION */
  public static final int DB_VERSION = 1;

  /** ���db���� */
  private SQLiteDatabase db;

  /**
   * ˽�й��췽��
   * 
   * @param context
   */
  private MyWeatherDbOPerations(Context context) {
    MyWeatherSQLiteOpenHelper myWeatherSQLiteOpenHelper = new MyWeatherSQLiteOpenHelper(context, DB_NAME, null, DB_VERSION);
    db = myWeatherSQLiteOpenHelper.getWritableDatabase();
  }

  private static class MyWeatherDbOPerationsHolder {
    private static final MyWeatherDbOPerations INSTANCE = new MyWeatherDbOPerations(ApplicationContext.getContext());
  }

  /**
   * ���������̰߳�ȫ��
   * 
   * <pre>
   * public class Singleton {  
   *     private static Singleton instance;  
   *     private Singleton (){}  
   *     public static synchronized Singleton getInstance() {  
   *     if (instance == null) {  
   *         instance = new Singleton();  
   *     }  
   *     return instance;  
   *     }
   * </pre>
   * 
   * }
   */

  /**
   * ����ģʽ
   * 
   * @return MyWeatherDbOPerations��ʵ��
   */
  public static final MyWeatherDbOPerations getInstance() {
    return MyWeatherDbOPerationsHolder.INSTANCE;
  }

  /**
   * ��province�洢�����ݿ�
   * 
   * @param province
   */
  public void saveProvince(Province province) {
    if (province != null) {
      ContentValues values = new ContentValues();
      values.put(ProvinceTbFields.NAME, province.getProvinceName());
      values.put(ProvinceTbFields.CODE, province.getProvinceCode());
      db.insert(ProvinceTbFields.TB_NAME, null, values);
    }
  }

  /**
   * @return ʡ����Ϣ
   */
  public List<Province> loadProvince() {
    List<Province> list = new ArrayList<Province>();
    Cursor cursor = db.query(ProvinceTbFields.TB_NAME, null, null, null, null, null, null);
    // ������������Ϊ0
    if (cursor.moveToFirst()) {
      do {
        Province province = new Province();
        province.setProvinceId(cursor.getInt(cursor.getColumnIndex(ProvinceTbFields.ID)));
        province.setProvinceName(cursor.getString(cursor.getColumnIndex(ProvinceTbFields.NAME)));
        province.setProvinceCode(cursor.getString(cursor.getColumnIndex(ProvinceTbFields.CODE)));
        list.add(province);
      } while (cursor.moveToNext());
    }
    return list;
  }

  /**
   * ��city�洢�����ݿ�
   * 
   * @param province
   */
  public void saveCity(City city) {
    if (city != null) {
      ContentValues values = new ContentValues();
      values.put(CityTbFields.NAME, city.getCityName());
      values.put(CityTbFields.CODE, city.getCityCode());
      values.put(CityTbFields.PROVINCE_ID, city.getProvinceOfCityId());
      db.insert(CityTbFields.TB_NAME, null, values);
    }
  }

  /**
   * @param provinceId
   * @return ������Ϣ
   */
  public List<City> loadCity(int provinceId) {
    List<City> list = new ArrayList<City>();
    Cursor cursor = db.query(CityTbFields.TB_NAME, null, CityTbFields.PROVINCE_ID + "= ?", new String[] { String.valueOf(provinceId) }, null, null, null);
    // ������������Ϊ0
    if (cursor.moveToFirst()) {
      do {
        City city = new City();
        city.setCityId(cursor.getInt(cursor.getColumnIndex(CityTbFields.ID)));
        city.setCityName(cursor.getString(cursor.getColumnIndex(CityTbFields.NAME)));
        city.setCityCode(cursor.getString(cursor.getColumnIndex(CityTbFields.CODE)));
        city.setProvinceOfCityId(provinceId);
        list.add(city);
      } while (cursor.moveToNext());
    }
    return list;
  }

  /**
   * ��country�洢�����ݿ�
   * 
   * @param province
   */
  public void saveCountry(Country country) {
    if (country != null) {
      ContentValues values = new ContentValues();
      values.put(CountryTbFields.NAME, country.getCountryName());
      values.put(CountryTbFields.CODE, country.getCountryCode());
      values.put(CountryTbFields.CITY_ID, country.getCityOfCountryId());
      db.insert(CountryTbFields.TB_NAME, null, values);
    }
  }

  /**
   * @param cityId
   * @return ������Ϣ
   */
  public List<Country> loadCountry(int cityId) {
    List<Country> list = new ArrayList<Country>();
    Cursor cursor = db.query(CountryTbFields.TB_NAME, null, CountryTbFields.CITY_ID + "= ?", new String[] { String.valueOf(cityId) }, null, null, null);
    // ������������Ϊ0
    if (cursor.moveToFirst()) {
      do {
        Country country = new Country();
        country.setCountryId(cursor.getInt(cursor.getColumnIndex(CountryTbFields.ID)));
        country.setCountryName(cursor.getString(cursor.getColumnIndex(CountryTbFields.NAME)));
        country.setCountryCode(cursor.getString(cursor.getColumnIndex(CountryTbFields.CODE)));
        country.setCityOfCountryId(cityId);
        list.add(country);
      } while (cursor.moveToNext());
    }
    return list;
  }

}
