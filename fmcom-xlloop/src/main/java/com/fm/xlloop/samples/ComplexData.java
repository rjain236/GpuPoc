package com.fm.xlloop.samples;

public class ComplexData {


  public ComplexData(int i, int j) {
    this.i = i;
    this.j = j;
  }

  public int i = 0;
  public int j = 0;

  @Override
  public String toString() {
    return "ComplexData" + i + ", " + "j";
  }
}
