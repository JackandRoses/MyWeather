package com.zhouwf.myweather.util;

public interface HttpCallbackListener {

  void onFinish(String response);

  void onError(Exception e);
}
