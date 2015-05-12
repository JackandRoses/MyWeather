package com.zhouwf.myweather.util;

import android.app.Application;
import android.content.Context;

public class ApplicationContext extends Application {

  private static Context context;

  /*
   * (non-Javadoc)
   * 
   * @see android.app.Application#onCreate()
   */
  @Override
  public void onCreate() {
    context = getApplicationContext();
    super.onCreate();
  }

  public static Context getContext() {
    return context;
  }
}
