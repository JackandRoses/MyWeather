package com.zhouwf.myweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DB 创建类
 * 
 * 
 * @author zhouwf
 *
 */
public class MyWeatherSQLiteOpenHelper extends SQLiteOpenHelper {

  /** CREATE_PROVINCE 省份 */
  public static final String CREATE_PROVINCE = //
  "create table Province ("//
      + "id integer primary key autoincrement,"//
      + "province_name text,"//
      + "province_code text)";

  /** CREATE_CITY 城市 */
  public static final String CREATE_CITY = //
  "create table City ("//
      + "id integer primary key autoincrement,"//
      + "city_name text,"//
      + "city_code text," //
      + "province_id integer)";

  /** CREATE_COUNTRY 县 */
  public static final String CREATE_COUNTRY = //
  "create table Country ("//
      + "id integer primary key autoincrement,"//
      + "country_name text,"//
      + "country_code text,"//
      + "city_id integer)";

  public MyWeatherSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
    super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_PROVINCE);
    db.execSQL(CREATE_COUNTRY);
    db.execSQL(CREATE_CITY);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
}
