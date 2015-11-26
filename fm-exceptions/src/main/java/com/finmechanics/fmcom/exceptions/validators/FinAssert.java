/*
 * Copyright (c) 2014 Finmechanics Pte Ltd. All rights reserved. fmCom project series
 */
package com.finmechanics.fmcom.exceptions.validators;

import static com.finmechanics.fmcom.exceptions.validators.FinValidatorErrorMessages.FINVAL_ARG_HAS_LEN;
import static com.finmechanics.fmcom.exceptions.validators.FinValidatorErrorMessages.FINVAL_ARG_HAS_NO_SUBSTRING;
import static com.finmechanics.fmcom.exceptions.validators.FinValidatorErrorMessages.FINVAL_ARG_HAS_TEXT;
import static com.finmechanics.fmcom.exceptions.validators.FinValidatorErrorMessages.FINVAL_ARG_NOT_NULL;
import static com.finmechanics.fmcom.exceptions.validators.FinValidatorErrorMessages.FINVAL_ARG_NULL;
import static com.finmechanics.fmcom.exceptions.validators.FinValidatorErrorMessages.FINVAL_ARRAY_NOT_EMPTY;
import static com.finmechanics.fmcom.exceptions.validators.FinValidatorErrorMessages.FINVAL_ARRAY_NO_NULL_ELEMENTS;
import static com.finmechanics.fmcom.exceptions.validators.FinValidatorErrorMessages.FINVAL_COL_NOT_EMPTY;
import static com.finmechanics.fmcom.exceptions.validators.FinValidatorErrorMessages.FINVAL_EXP_FALSE;
import static com.finmechanics.fmcom.exceptions.validators.FinValidatorErrorMessages.FINVAL_EXP_TRUE;
import static com.finmechanics.fmcom.exceptions.validators.FinValidatorErrorMessages.FINVAL_MAP_NO_NULL_ELEMENTS;

import java.util.Collection;
import java.util.Map;

import com.finmechanics.fmcom.exceptions.ErrorMessage;
import com.finmechanics.fmcom.exceptions.FinExceptionBuilder;


/**
 * The Class FinAssert.
 */
public abstract class FinAssert {

  /**
   * The Class CollectionUtils.
   */
  private static class CollectionUtils {

    /**
     * Checks if is empty.
     *
     * @param collection the collection
     * @return true, if is empty
     */
    public static boolean isEmpty(Collection<?> collection) {
      return collection == null || collection.isEmpty();
    }

    /**
     * Checks if is empty.
     *
     * @param map the map
     * @return true, if is empty
     */
    public static boolean isEmpty(Map<?, ?> map) {
      return map == null || map.isEmpty();
    }
  }

  /**
   * The Class ObjectUtils.
   */
  private static class ObjectUtils {

    /**
     * Checks if is empty.
     *
     * @param array the array
     * @return true, if is empty
     */
    public static boolean isEmpty(Object[] array) {
      return array == null || array.length == 0;
    }
  }

  /**
   * The Class StringUtils.
   */
  private static class StringUtils {

    /**
     * Checks for length.
     *
     * @param str the str
     * @return true, if successful
     */
    public static boolean hasLength(CharSequence str) {
      return str != null && str.length() > 0;
    }

    /**
     * Checks for length.
     *
     * @param str the str
     * @return true, if successful
     */
    public static boolean hasLength(String str) {
      return hasLength((CharSequence) str);
    }

    /**
     * Checks for text.
     *
     * @param str the str
     * @return true, if successful
     */
    public static boolean hasText(CharSequence str) {
      if (!hasLength(str)) {
        return false;
      }
      int strLen = str.length();
      for (int i = 0; i < strLen; i++) {
        if (!Character.isWhitespace(str.charAt(i))) {
          return true;
        }
      }
      return false;
    }

    /**
     * Checks for text.
     *
     * @param str the str
     * @return true, if successful
     */
    public static boolean hasText(String str) {
      return hasText((CharSequence) str);
    }
  }

  /**
   * Does not contain.
   *
   * @param textToSearch the text to search
   * @param substring the substring
   */
  public static void doesNotContain(String textToSearch, String substring) {
    // TODO(jonnie) refractor to include code
    doesNotContain(textToSearch, substring, FINVAL_ARG_HAS_NO_SUBSTRING);
  }

  /**
   * Does not contain.
   *
   * @param textToSearch the text to search
   * @param substring the substring
   * @param message the message
   */
  public static void doesNotContain(String textToSearch, String substring, ErrorMessage message) {
    if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring)
        && textToSearch.contains(substring)) {
      throw new FinExceptionBuilder<FinValidatorException>(FinValidatorException.class).setMessage(
          message).build();
    }
  }

  /**
   * Checks for length.
   *
   * @param text the text
   */
  public static void hasLength(String text) {
    hasLength(text, FINVAL_ARG_HAS_LEN);
  }

  /**
   * Checks for length.
   *
   * @param text the text
   * @param message the message
   */
  public static void hasLength(String text, ErrorMessage message) {
    if (!StringUtils.hasLength(text)) {
      throw new FinExceptionBuilder<FinValidatorException>(FinValidatorException.class).setMessage(
          message).build();
    }
  }

  /**
   * Checks for text.
   *
   * @param text the text
   */
  public static void hasText(String text) {
    hasText(text, FINVAL_ARG_HAS_TEXT);
  }

  /**
   * Checks for text.
   *
   * @param text the text
   * @param message the message
   */
  public static void hasText(String text, ErrorMessage message) {
    if (!StringUtils.hasText(text)) {
      throw new FinExceptionBuilder<FinValidatorException>(FinValidatorException.class).setMessage(
          message).build();
    }
  }

  /**
   * Checks if is null.
   *
   * @param object the object
   */
  public static void isNull(Object object) {
    isNull(object, FINVAL_ARG_NULL);
  }

  /**
   * Checks if is null.
   *
   * @param object the object
   * @param message the message
   */
  public static void isNull(Object object, ErrorMessage message) {
    if (object != null) {
      throw new FinExceptionBuilder<FinValidatorException>(FinValidatorException.class).setMessage(
          message).build();
    }
  }

  /**
   * Checks if is null or empty text.
   *
   * @param text the text
   * @param message the message
   */
  public static void isNullOrEmptyText(String text, ErrorMessage message) {
    if (text == null || "".equals(text.trim())) {
      throw new FinExceptionBuilder<FinValidatorException>(FinValidatorException.class).setMessage(
          message).build();
    }
  }

  /**
   * Checks if is true.
   *
   * @param expression the expression
   */
  public static void isTrue(boolean expression) {
    isTrue(expression, FINVAL_EXP_TRUE);
  }

  /**
   * Checks if is false.
   *
   * @param expression the expression
   */
  public static void isFalse(boolean expression) {
    isFalse(expression, FINVAL_EXP_FALSE);
  }
  
  /**
   * Checks if is true.
   *
   * @param expression the expression
   * @param message the message
   */
  public static void isTrue(boolean expression, ErrorMessage message) {
    if (!expression) {
      throw new FinExceptionBuilder<FinValidatorException>(FinValidatorException.class).setMessage(
          message).build();
    }
  }

  /**
   * Checks if is false.
   *
   * @param expression the expression
   * @param message the message
   */
  public static void isFalse(boolean expression, ErrorMessage message) {
    if (expression) {
      throw new FinExceptionBuilder<FinValidatorException>(FinValidatorException.class).setMessage(
          message).build();
    }
  }

  
  /**
   * No null elements.
   *
   * @param array the array
   */
  public static void noNullElements(Object[] array) {
    noNullElements(array, FINVAL_ARRAY_NO_NULL_ELEMENTS);
  }

  /**
   * No null elements.
   *
   * @param array the array
   * @param message the message
   */
  public static void noNullElements(Object[] array, ErrorMessage message) {
    if (array != null) {
      for (Object element : array) {
        if (element == null) {
          throw new FinExceptionBuilder<FinValidatorException>(FinValidatorException.class)
              .setMessage(message).build();
        }
      }
    }
  }

  /**
   * Not empty.
   *
   * @param collection the collection
   */
  public static void notEmpty(Collection<?> collection) {
    notEmpty(collection, FINVAL_COL_NOT_EMPTY);
  }

  /**
   * Not empty.
   *
   * @param collection the collection
   * @param message the message
   */
  public static void notEmpty(Collection<?> collection, ErrorMessage message) {
    if (CollectionUtils.isEmpty(collection)) {
      throw new FinExceptionBuilder<FinValidatorException>(FinValidatorException.class).setMessage(
          message).build();
    }
  }

  /**
   * Not empty.
   *
   * @param map the map
   */
  public static void notEmpty(Map<?, ?> map) {
    notEmpty(map, FINVAL_MAP_NO_NULL_ELEMENTS);
  }

  /**
   * Not empty.
   *
   * @param map the map
   * @param message the message
   */
  public static void notEmpty(Map<?, ?> map, ErrorMessage message) {
    if (CollectionUtils.isEmpty(map)) {
      throw new FinExceptionBuilder<FinValidatorException>(FinValidatorException.class).setMessage(
          message).build();
    }
  }

  /**
   * Not empty.
   *
   * @param array the array
   */
  public static void notEmpty(Object[] array) {
    notEmpty(array, FINVAL_ARRAY_NOT_EMPTY);
  }

  // TODO(jonnie) refractor when have time to include these less used functions

  // public static void isInstanceOf(Class<?> clazz, Object obj) {
  // isInstanceOf(clazz, obj, "");
  // }
  //
  // public static void isInstanceOf(Class<?> type, Object obj, ErrorMessage
  // message) {
  // notNull(type, FINVAL_TYPE_NOT_NULL);
  // if (!type.isInstance(obj)) {
  // throw new FinValidatorException(
  // (StringUtils.hasLength(message) ? message + " " : "")
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
  // ErrorMessage message) {
  // notNull(superType, "Type to check against must not be null");
  // if (subType == null || !superType.isAssignableFrom(subType)) {
  // throw new FinValidatorException(message + subType
  // + " is not assignable to " + superType);
  // }
  // }
  //
  // public static void state(boolean expression, ErrorMessage message) {
  // if (!expression) {
  // throw new IllegalStateException(message);
  // }
  // }
  //
  // public static void state(boolean expression) {
  // state(expression,
  // FINVAL_STATE_INV_TRUE);
  // }

  /**
   * Not empty.
   *
   * @param array the array
   * @param message the message
   */
  public static void notEmpty(Object[] array, ErrorMessage message) {
    if (ObjectUtils.isEmpty(array)) {
      throw new FinExceptionBuilder<FinValidatorException>(FinValidatorException.class).setMessage(
          message).build();
    }
  }

  /**
   * Not null.
   *
   * @param object the object
   */
  public static void notNull(Object object) {
    notNull(object, FINVAL_ARG_NOT_NULL);
  }

  /**
   * Not null.
   *
   * @param object the object
   * @param message the message
   */
  public static void notNull(Object object, ErrorMessage message) {
    if (object == null) {
      throw new FinExceptionBuilder<FinValidatorException>(FinValidatorException.class).setMessage(
          message).build();
    }
  }

}
