package com.zhouwf.myweather.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;

public class HttpUtil {
  private static final String GET = "GET";

  public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        HttpURLConnection connection = null;
        try {
          URL url = new URL(address);
          connection = (HttpURLConnection) url.openConnection();
          connection.setReadTimeout(8000);
          connection.setRequestMethod(GET);
          connection.setConnectTimeout(8000);
          InputStream in = connection.getInputStream();
          BufferedReader reader = new BufferedReader(new InputStreamReader(in));
          StringBuilder response = new StringBuilder();
          String line;
          while ((line = reader.readLine()) != null) {
            response.append(line);
          }
          if (listener != null) {
            // callBack onFinish()
            listener.onFinish(response.toString());
          }
        } catch (IOException | JSONException e) {
          // callBack onError()
          if (listener != null) {
            listener.onError(e);
          }
          e.printStackTrace();
        }
      }
    }).start();
  }
}
