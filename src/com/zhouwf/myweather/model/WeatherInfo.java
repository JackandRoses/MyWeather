package com.zhouwf.myweather.model;

public class WeatherInfo {
  private String cityname;
  private String weatherCode;
  private String temp1;
  private String temp2;
  private String weatherDesp;
  private String publishTime;

  /**
   * @return the cityname
   */
  public String getCityname() {
    return cityname;
  }

  /**
   * @param cityname the cityname to set
   */
  public void setCityname(String cityname) {
    this.cityname = cityname;
  }

  /**
   * @return the weatherCode
   */
  public String getWeatherCode() {
    return weatherCode;
  }

  /**
   * @param weatherCode the weatherCode to set
   */
  public void setWeatherCode(String weatherCode) {
    this.weatherCode = weatherCode;
  }

  /**
   * @return the temp1
   */
  public String getTemp1() {
    return temp1;
  }

  /**
   * @param temp1 the temp1 to set
   */
  public void setTemp1(String temp1) {
    this.temp1 = temp1;
  }

  /**
   * @return the temp2
   */
  public String getTemp2() {
    return temp2;
  }

  /**
   * @param temp2 the temp2 to set
   */
  public void setTemp2(String temp2) {
    this.temp2 = temp2;
  }

  /**
   * @return the weatherDesp
   */
  public String getWeatherDesp() {
    return weatherDesp;
  }

  /**
   * @param weatherDesp the weatherDesp to set
   */
  public void setWeatherDesp(String weatherDesp) {
    this.weatherDesp = weatherDesp;
  }

  /**
   * @return the publishTime
   */
  public String getPublishTime() {
    return publishTime;
  }

  /**
   * @param publishTime the publishTime to set
   */
  public void setPublishTime(String publishTime) {
    this.publishTime = publishTime;
  }

}
