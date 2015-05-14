package com.zhouwf.myweather.util;

public enum Districts {
  PROVINCE(0), //
  CITY(1), //
  COUNTRY(2);
  public final int value;

  private Districts(int value) {
    this.value = value;
  }
}
// package Reflect;
//
// /**
// * @author zhouwf
// *
// */
// public enum FruitKinds {
//
// APPLE(0),
//
// ORANGE(1);
//
// public final int value;
//
// private FruitKinds(int value) {
// this.value = value;
// }
//
// public int getValue() {
// return value;
// }
//
// public boolean is(int value) {
// return this.value == value;
// }
//
// @Override
// public String toString() {
// return String.valueOf(value);
// }
//
// public FruitKinds toFruitKinds(String kind) {
// return valueOf(kind);
//
// }
// }
