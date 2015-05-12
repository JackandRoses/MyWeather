package com.zhouwf.myweather.model;

public class Country {
  /** countryId */
  private int countryId;
  /** countryName */
  private String countryName;
  /** contryCode */
  private String countryCode;
  /** cityOfCountryId */
  private int cityOfCountryId;

  /**
   * @return the countryId
   */
  public int getCountryId() {
    return countryId;
  }

  /**
   * @param countryId the countryId to set
   */
  public void setCountryId(int countryId) {
    this.countryId = countryId;
  }

  /**
   * @return the countryName
   */
  public String getCountryName() {
    return countryName;
  }

  /**
   * @param countryName the countryName to set
   */
  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  /**
   * @return the countryCode
   */
  public String getCountryCode() {
    return countryCode;
  }

  /**
   * @param countryCode the countryCode to set
   */
  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  /**
   * @return the cityOfCountryId
   */
  public int getCityOfCountryId() {
    return cityOfCountryId;
  }

  /**
   * @param cityOfCountryId the cityOfCountryId to set
   */
  public void setCityOfCountryId(int cityOfCountryId) {
    this.cityOfCountryId = cityOfCountryId;
  }

}
