package com.zhouwf.myweather.activity;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myweather.R;
import com.zhouwf.myweather.util.HttpCallbackListener;
import com.zhouwf.myweather.util.HttpUtil;
import com.zhouwf.myweather.util.Utility;

public class WeatherActivity extends Activity implements OnClickListener {
    public static final String defValue = "";
    private LinearLayout weatehrInfoLayout;
    /** cityNameText */
    private TextView cityNameText;
    /** publishText */
    private TextView publishText;
    /** weatherDespText */
    private TextView weatherDespText;
    /** temp1Text */
    private TextView temp1Text;
    /** temp2Text */
    private TextView temp2Text;
    /** currentDateText */
    private TextView currentDateText;

    private Button switchCityButton;

    private Button refreshWeatherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);
        // 初始化控件
        weatehrInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        currentDateText = (TextView) findViewById(R.id.current_date);
        switchCityButton = (Button) findViewById(R.id.switch_city);
        refreshWeatherButton = (Button) findViewById(R.id.refresh_weather);
        String countryCode = getIntent().getStringExtra("country_code");
        if (!TextUtils.isEmpty(countryCode)) {
            // 有县级代号就去查询天气
            publishText.setText("同步中...");
            weatehrInfoLayout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeatherInfo(countryCode);
        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String weatherCode = prefs.getString("weather_code", "");
            if (!TextUtils.isEmpty(weatherCode)) {
                queryWeatherInfo(weatherCode);
            }
        }

        switchCityButton.setOnClickListener(this);
        refreshWeatherButton.setOnClickListener(this);
    }

    /**
     * 查询 县级天气
     * 
     * @param countryCode
     */
    private void queryWeatherInfo(String countryCode) {
        if (!TextUtils.isEmpty(countryCode) && countryCode.length() <= 6) {
            String address = "http://m.weather.com.cn/atad/" + "101" + countryCode + ".html";
            // http://m.weather.com.cn/atad/101010100.html
            queryFromServer(address);
        } else {
            queryFromServer("http://m.weather.com.cn/atad/" + countryCode + ".html");
        }
    }

    private void queryFromServer(String address) {
        if (!TextUtils.isEmpty(address)) {
            HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

                @Override
                public void onFinish(String response) throws JSONException {
                    Utility.handleWeatherInfoResponse(WeatherActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            publishText.setText("同步失败！");
                        }
                    });
                }
            });
        }
    }

    private void showWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString("city_name", defValue));
        publishText.setText(prefs.getString("publish_time", defValue) + "发布");
        currentDateText.setText(prefs.getString("current_date", defValue));
        weatherDespText.setText(prefs.getString("weather_desp", defValue));
        temp1Text.setText(prefs.getString("temp1", defValue));
        temp2Text.setText(prefs.getString("temp2", defValue));
        weatehrInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.switch_city:
            Intent intent = new Intent(this, ChooseAreaActivity.class);
            intent.putExtra("from_weather_activity", true);
            startActivity(intent);
            finish();
            break;
        case R.id.refresh_weather:
            publishText.setText("同步中...");
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String weatherCode = prefs.getString("weather_code", "");
            if (!TextUtils.isEmpty(weatherCode)) {
                queryWeatherInfo(weatherCode);
            }
        default:
            break;
        }
    }
}
