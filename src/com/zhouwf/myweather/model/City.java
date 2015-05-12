package com.zhouwf.myweather.model;

public class City {
  /** cityId */
  private int cityId;

  /** cityName */
  private String cityName;

  /** cityCode */
  private String cityCode;

  /** provinceOfCityId */
  private int provinceOfCityId;

  /**
   * @return the cityId
   */
  public int getCityId() {
    return cityId;
  }

  /**
   * @param cityId the cityId to set
   */
  public void setCityId(int cityId) {
    this.cityId = cityId;
  }

  /**
   * @return the cityName
   */
  public String getCityName() {
    return cityName;
  }

  /**
   * @param cityName the cityName to set
   */
  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  /**
   * @return the cityCode
   */
  public String getCityCode() {
    return cityCode;
  }

  /**
   * @param cityCode the cityCode to set
   */
  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  /**
   * @return the provinceOfCityId
   */
  public int getProvinceOfCityId() {
    return provinceOfCityId;
  }

  /**
   * @param provinceOfCityId the provinceOfCityId to set
   */
  public void setProvinceOfCityId(int provinceOfCityId) {
    this.provinceOfCityId = provinceOfCityId;
  }

}
