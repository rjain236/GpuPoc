package com.finmechanics.fmcom.exceptions.validators;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestFinAssert {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testExpectedException() {
    // exception.expect(IllegalArgumentException.class);
    // exception.expectMessage(containsString("fin error message"));

  }

  @Test
  public void isTrueTestP01() {
    int i = 8;
    FinAssert.isTrue(i == 8);

  }

  @Test(expected = FinValidatorException.class)
  public void isTrueTestN01() {
    int i = 8;
    FinAssert.isTrue(i != 8);

  }


  // public static void isTrue(boolean expression) {
  // isTrue(expression, "[Assertion failed] - this expression must be true");
  // }
  //
  // public static void isNull(Object object, String message) {
  // if (object != null) {
  // throw new FinException(message);
  // }
  // }
  //
  // public static void isNull(Object object) {
  // isNull(object, "[Assertion failed] - the object argument must be null");
  // }
  //
  // public static void notNull(Object object, String message) {
  // if (object == null) {
  // throw new FinException(message);
  // }
  // }
  //
  // public static void notNull(Object object) {
  // notNull(object,
  // "[Assertion failed] - this argument is required; it must not be null");
  // }
  //
  // public static void hasLength(String text, String message) {
  // if (!StringUtils.hasLength(text)) {
  // throw new FinException(message);
  // }
  // }
  //
  // public static void hasLength(String text) {
  // hasLength(
  // text,
  // "[Assertion failed] - this String argument must have length; it must not be null or empty");
  // }
  //
  // public static void hasText(String text, String message) {
  // if (!StringUtils.hasText(text)) {
  // throw new FinException(message);
  // }
  // }
  //
  // public static void hasText(String text) {
  // hasText(text,
  // "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
  // }
  //
  // public static void doesNotContain(String textToSearch, String substring,
  // String message) {
  // if (StringUtils.hasLength(textToSearch)
  // && StringUtils.hasLength(substring)
  // && textToSearch.contains(substring)) {
  // throw new FinException(message);
  // }
  // }
  //
  // public static void doesNotContain(String textToSearch, String substring) {
  // doesNotContain(textToSearch, substring,
  // "[Assertion failed] - this String argument must not contain the substring ["
  // + substring + "]");
  // }
  //
  // public static void notEmpty(Object[] array, String message) {
  // if (ObjectUtils.isEmpty(array)) {
  // throw new FinException(message);
  // }
  // }
  //
  // public static void notEmpty(Object[] array) {
  // notEmpty(
  // array,
  // "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
  // }
  //
  // public static void noNullElements(Object[] array, String message) {
  // if (array != null) {
  // for (Object element : array) {
  // if (element == null) {
  // throw new FinException(message);
  // }
  // }
  // }
  // }
  //
  // public static void noNullElements(Object[] array) {
  // noNullElements(array,
  // "[Assertion failed] - this array must not contain any null elements");
  // }
  //
  // public static void notEmpty(Collection<?> collection, String message) {
  // if (CollectionUtils.isEmpty(collection)) {
  // throw new FinException(message);
  // }
  // }
  //
  // public static void notEmpty(Collection<?> collection) {
  // notEmpty(
  // collection,
  // "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
  // }
  //
  // public static void notEmpty(Map<?, ?> map, String message) {
  // if (CollectionUtils.isEmpty(map)) {
  // throw new FinException(message);
  // }
  // }
  //
  // public static void notEmpty(Map<?, ?> map) {
  // notEmpty(
  // map,
  // "[Assertion failed] - this map must not be empty; it must contain at least one entry");
  // }
  //
  // public static void isInstanceOf(Class<?> clazz, Object obj) {
  // isInstanceOf(clazz, obj, "");
  // }
  //
  // public static void isInstanceOf(Class<?> type, Object obj, String message) {
  // notNull(type, "Type to check against must not be null");
  // if (!type.isInstance(obj)) {
  // throw new FinException((StringUtils.hasLength(message) ? message
  // + " " : "")
  // + "Object of class ["
  // + (obj != null ? obj.getClass().getName() : "null")
  // + "] must be an instance of " + type);
  // }
  // }
  //
  // public static void isAssignable(Class<?> superType, Class<?> subType) {
  // isAssignable(superType, subType, "");
  // }
  //
  // public static void isAssignable(Class<?> superType, Class<?> subType,
  // String message) {
  // notNull(superType, "Type to check against must not be null");
  // if (subType == null || !superType.isAssignableFrom(subType)) {
  // throw new FinException(message + subType + " is not assignable to "
  // + superType);
  // }
  // }
  //
  // public static void state(boolean expression, String message) {
  // if (!expression) {
  // throw new IllegalStateException(message);
  // }
  // }
  //
  // public static void state(boolean expression) {
  // state(expression,
  // "[Assertion failed] - this state invariant must be true");
  // }
  //
  // private static class StringUtils {
  //
  // public static boolean hasLength(CharSequence str) {
  // return (str != null && str.length() > 0);
  // }
  //
  // public static boolean hasLength(String str) {
  // return hasLength((CharSequence) str);
  // }
  //
  // public static boolean hasText(CharSequence str) {
  // if (!hasLength(str)) {
  // return false;
  // }
  // int strLen = str.length();
  // for (int i = 0; i < strLen; i++) {
  // if (!Character.isWhitespace(str.charAt(i))) {
  // return true;
  // }
  // }
  // return false;
  // }
  //
  // public static boolean hasText(String str) {
  // return hasText((CharSequence) str);
  // }

}
