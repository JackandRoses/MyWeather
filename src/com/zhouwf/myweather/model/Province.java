package com.zhouwf.myweather.model;

/**
 * Province entity
 * 
 * @author zhouwf
 *
 */
public class Province {

  /** provinceId */
  private int provinceId;

  /** provinceName */
  private String provinceName;

  /** provinceCode */
  private String provinceCode;

  /**
   * @return the provinceId
   */
  public int getProvinceId() {
    return provinceId;
  }

  /**
   * @param provinceId the provinceId to set
   */
  public void setProvinceId(int provinceId) {
    this.provinceId = provinceId;
  }

  /**
   * @return the provinceName
   */
  public String getProvinceName() {
    return provinceName;
  }

  /**
   * @param provinceName the provinceName to set
   */
  public void setProvinceName(String provinceName) {
    this.provinceName = provinceName;
  }

  /**
   * @return the provinceCode
   */
  public String getProvinceCode() {
    return provinceCode;
  }

  /**
   * @param provinceCode the provinceCode to set
   */
  public void setProvinceCode(String provinceCode) {
    this.provinceCode = provinceCode;
  }

}
