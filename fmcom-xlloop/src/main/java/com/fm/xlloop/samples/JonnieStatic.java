package com.fm.xlloop.samples;

import java.util.HashMap;
import java.util.Map;

public class JonnieStatic {

  public static int addInt1 (int i ,int j){
    System.out.println("Jonnie: addInt " + i + "," + j);
    return i+j;
  }
  
  public static int addInteger (Integer i ,Integer j){
    System.out.println("Jonnie: addInteger " + i + "," + j);
    return i+j;
  }
  
  public static long addLong (long i ,long j){
    System.out.println("Jonnie: addLong " + i + "," + j);
    return i+j;
  }
  
  public static Long addLongObj (Long i ,Long j){
    System.out.println("Jonnie: addLongObj " + i + "," + j);
    return i+j;
  }
  
  
  public static ComplexData addComplexData(int i, int j){
    return new ComplexData(i, j);
  }
  
  public static ComplexData takeComplexData(ComplexData complexData){
    return new ComplexData(complexData.i + 1, complexData.j + 1);
  }
  
  public static Map<String, String>  addMap(int i, int j){
    Map<String, String> testStrMap = new HashMap<String, String>();
    testStrMap.put("one", "one value" + i);
    return testStrMap;
  }
  
}
