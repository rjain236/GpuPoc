/*
 * Copyright (c) 2014 Finmechanics Pte Ltd. All rights reserved. fmCom project series
 */
package com.finmechanics.fmcom.exceptions;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.Map;

/**
 * The Class FinExceptionBuilder.
 *
 * @param <FinExceptionT> the generic type
 */
public class FinExceptionBuilder<FinExceptionT extends FinException> {

  /** The Constant GENERIC_ERROR_CODE. */
  private static final String GENERIC_ERROR_CODE = SystemErrorMessage.GENERIC_ERROR.getCode();

  /** The Constant GENERIC_ERROR_MESSAGE. */
  private static final String GENERIC_ERROR_MESSAGE = SystemErrorMessage.GENERIC_ERROR
      .getErrorMsg();

  /** The cause. */
  private final Throwable cause;

  /** The clazz. */
  private final Class<FinExceptionT> clazz;

  /** The error code. */
  private final String errorCode;

  /** The message. */
  private final String message;

  /** The named parameter message. */
  private final String namedParameterMessage;

  /** The named parameter value map. */
  private final Map<String, Object> namedParameterValueMap;

  /** The numbered parameter message. */
  private final String numberedParameterMessage;

  /** The numbered parameter values. */
  private final Object[] numberedParameterValues;

  /**
   * Instantiates a new fin exception builder.
   *
   * @param clazz the clazz
   */
  public FinExceptionBuilder(Class<FinExceptionT> clazz) {
    this.clazz = clazz;
    this.cause = null;
    this.errorCode = GENERIC_ERROR_CODE;
    this.message = null;
    this.numberedParameterMessage = null;
    this.numberedParameterValues = null;
    this.namedParameterMessage = null;
    this.namedParameterValueMap = null;

  }

  /**
   * Instantiates a new fin exception builder.
   *
   * @param clazz the clazz
   * @param cause the cause
   * @param errorCode the error code
   * @param message the message
   * @param numberedParameterMessage the numbered parameter message
   * @param numberedParameterValues the numbered parameter values
   * @param namedParameterMessage the named parameter message
   * @param namedParameterValueMap the named parameter value map
   */
  public FinExceptionBuilder(Class<FinExceptionT> clazz, final Throwable cause, String errorCode,
      String message, String numberedParameterMessage, final Object[] numberedParameterValues,
      String namedParameterMessage, final Map<String, Object> namedParameterValueMap) {
    super();
    this.clazz = clazz;
    this.cause = cause;
    this.errorCode = StringUtils.isEmpty(errorCode) ? GENERIC_ERROR_CODE : errorCode;
    this.message = message;
    this.numberedParameterMessage = numberedParameterMessage;
    this.numberedParameterValues = numberedParameterValues;
    this.namedParameterMessage = namedParameterMessage;
    this.namedParameterValueMap = namedParameterValueMap;
  }

  /**
   * Builds the.
   *
   * @return the fin exception t
   */
  public FinExceptionT build() {
    try {

      StringBuilder errMessage = new StringBuilder();
      if (!StringUtils.isEmpty(message)) {
        errMessage = errMessage.append(message);
      }
      if (!StringUtils.isEmpty(numberedParameterMessage)) {
        if (numberedParameterValues != null && numberedParameterValues.length > 0) {
          if (errMessage.length() > 0) {
            errMessage.append("\n");
          }
          try {
            errMessage.append(MessageFormat.format(numberedParameterMessage,
                numberedParameterValues));
          } catch (Exception cannotParseException) {
            errMessage.append(numberedParameterMessage);
          }
        } else {
          errMessage.append(numberedParameterMessage);
        }
      }

      if (!StringUtils.isEmpty(namedParameterMessage)) {
        if (namedParameterValueMap != null && namedParameterValueMap.size() > 0) {
          if (errMessage.length() > 0) {
            errMessage.append("\n");
          }
          try {
            StrSubstitutor sub = new StrSubstitutor(namedParameterValueMap);
            errMessage.append(sub.replace(namedParameterMessage));

          } catch (Exception expectException) {/* ignore this exception */
            errMessage.append(namedParameterMessage);

          }
        } else {
          errMessage.append(namedParameterMessage);
        }
      }
      if (errMessage.length() == 0) {
        errMessage.append(GENERIC_ERROR_MESSAGE);
      }

      Constructor<FinExceptionT> ctor =
          clazz.getDeclaredConstructor(String.class, String.class, Throwable.class);
      return (FinExceptionT) ctor.newInstance(errorCode, errMessage.toString(), cause);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Gets the type parameter class.
   *
   * @return the type parameter class
   */
  @SuppressWarnings("unchecked")
  public Class<FinExceptionT> getTypeParameterClass() {
    Type type = this.getClass().getGenericSuperclass();

    ParameterizedType paramType = (ParameterizedType) type;
    return (Class<FinExceptionT>) paramType.getActualTypeArguments()[0];

    // ParameterizedType genericSuperclass = (ParameterizedType)
    // getClass().getGenericSuperclass();
    // Type type = genericSuperclass.getActualTypeArguments()[0];
    // if (type instanceof Class) {
    // return (Class<FinExceptionT>) type;
    // } else if (type instanceof ParameterizedType) {
    // return (Class<FinExceptionT>) ((ParameterizedType)type).getRawType();
    // }
    // return null;
  }

  /**
   * Sets the cause.
   *
   * @param cause the cause
   * @return the fin exception builder
   */
  public FinExceptionBuilder<FinExceptionT> setCause(Throwable cause) {

    return new FinExceptionBuilder<FinExceptionT>(clazz, cause, errorCode, message,
        numberedParameterMessage, numberedParameterValues, namedParameterMessage,
        namedParameterValueMap);
  }

  /**
   * Sets the error code.
   *
   * @param errorCode the error code
   * @return the fin exception builder
   */
  public FinExceptionBuilder<FinExceptionT> setErrorCode(String errorCode) {

    return new FinExceptionBuilder<FinExceptionT>(clazz, cause, errorCode, message,
        numberedParameterMessage, numberedParameterValues, namedParameterMessage,
        namedParameterValueMap);
  }

  /**
   * Sets the message.
   *
   * @param errorMessage the error message
   * @return the fin exception builder
   */
  public FinExceptionBuilder<FinExceptionT> setMessage(ErrorMessage errorMessage) {

    return new FinExceptionBuilder<FinExceptionT>(clazz, cause, errorMessage.getCode(), message,
        errorMessage.getErrorMsg(), numberedParameterValues, namedParameterMessage,
        namedParameterValueMap);
  }

  /**
   * Sets the message.
   *
   * @param message the message
   * @return the fin exception builder
   */
  public FinExceptionBuilder<FinExceptionT> setMessage(String message) {

    return new FinExceptionBuilder<FinExceptionT>(clazz, cause, errorCode, message,
        numberedParameterMessage, numberedParameterValues, namedParameterMessage,
        namedParameterValueMap);
  }

  /**
   * Sets the named parameter message.
   *
   * @param errorMessage the error message
   * @param namedParameterValueMap the named parameter value map
   * @return the fin exception builder
   */
  public FinExceptionBuilder<FinExceptionT> setNamedParameterMessage(ErrorMessage errorMessage,
      Map<String, Object> namedParameterValueMap) {

    return new FinExceptionBuilder<FinExceptionT>(clazz, cause, errorMessage.getCode(), message,
        numberedParameterMessage, numberedParameterValues, errorMessage.getErrorMsg(),
        namedParameterValueMap);
  }

  /**
   * Sets the named parameter message.
   *
   * @param namedParameterMessage the named parameter message
   * @param namedParameterValueMap the named parameter value map
   * @return the fin exception builder
   */
  public FinExceptionBuilder<FinExceptionT> setNamedParameterMessage(String namedParameterMessage,
      Map<String, Object> namedParameterValueMap) {

    return new FinExceptionBuilder<FinExceptionT>(clazz, cause, errorCode, message,
        numberedParameterMessage, numberedParameterValues, namedParameterMessage,
        namedParameterValueMap);
  }

  /**
   * Sets the numbered parameter message.
   *
   * @param errorMessage the error message
   * @param numberedParameterValues the numbered parameter values
   * @return the fin exception builder
   */
  public FinExceptionBuilder<FinExceptionT> setNumberedParameterMessage(ErrorMessage errorMessage,
      Object[] numberedParameterValues) {

    return new FinExceptionBuilder<FinExceptionT>(clazz, cause, errorMessage.getCode(), message,
        errorMessage.getErrorMsg(), numberedParameterValues, namedParameterMessage,
        namedParameterValueMap);
  }

  /**
   * Sets the numbered parameter message.
   *
   * @param numberedParameterMessage the numbered parameter message
   * @param numberedParameterValues the numbered parameter values
   * @return the fin exception builder
   */
  public FinExceptionBuilder<FinExceptionT> setNumberedParameterMessage(
      String numberedParameterMessage, Object[] numberedParameterValues) {

    return new FinExceptionBuilder<FinExceptionT>(clazz, cause, errorCode, message,
        numberedParameterMessage, numberedParameterValues, namedParameterMessage,
        namedParameterValueMap);
  }

  public static final <AnyFinExceptionT extends FinException> AnyFinExceptionT buildAndThrow(
      Class<AnyFinExceptionT> clazz, ErrorMessage message, Throwable cause) {
    throw new FinExceptionBuilder<AnyFinExceptionT>(clazz).setCause(cause).setMessage(message).build();
  }

  public static final <AnyFinExceptionT extends FinException> AnyFinExceptionT buildAndThrow(
      Class<AnyFinExceptionT> clazz, ErrorMessage message, Object[] numberedParameterValues, Throwable cause) {
    throw new FinExceptionBuilder<AnyFinExceptionT>(clazz).setCause(cause).setNumberedParameterMessage(message,
        numberedParameterValues).build();
  }

  public static final <AnyFinExceptionT extends FinException> AnyFinExceptionT buildAndThrow(
      Class<AnyFinExceptionT> clazz, ErrorMessage message,
      Map<String, Object> namedParameterValueMap, Throwable cause) {
    throw new FinExceptionBuilder<AnyFinExceptionT>(clazz).setCause(cause).setNamedParameterMessage(message,
        namedParameterValueMap).build();
  }

  public static final <AnyFinExceptionT extends FinException> AnyFinExceptionT buildAndThrow(
      Class<AnyFinExceptionT> clazz, ErrorMessage message, Throwable cause, final Logger LOG) {
    AnyFinExceptionT exInstance =
        new FinExceptionBuilder<AnyFinExceptionT>(clazz).setCause(cause).setMessage(message).build();
    LOG.error(ErrPrettyPrint.prettyPrintStr(exInstance, true));
    throw exInstance;
  }

  public static final <AnyFinExceptionT extends FinException> AnyFinExceptionT buildAndThrow(
      Class<AnyFinExceptionT> clazz, ErrorMessage message, final Object[] numberedParameterValues,
      Throwable cause, final Logger LOG) {
    AnyFinExceptionT exInstance =
        new FinExceptionBuilder<AnyFinExceptionT>(clazz).setCause(cause).setNumberedParameterMessage(message,
            numberedParameterValues).build();
    LOG.error(ErrPrettyPrint.prettyPrintStr(exInstance, true));
    throw exInstance;
  }

  public static final <AnyFinExceptionT extends FinException> AnyFinExceptionT buildAndThrow(
      Class<AnyFinExceptionT> clazz, ErrorMessage message,
      final Map<String, Object> namedParameterValueMap, Throwable cause, final Logger LOG) {
    AnyFinExceptionT exInstance =
        new FinExceptionBuilder<AnyFinExceptionT>(clazz).setCause(cause).setNamedParameterMessage(message,
            namedParameterValueMap).build();
    LOG.error(ErrPrettyPrint.prettyPrintStr(exInstance, true));
    throw exInstance;
  }


public static void main(String[] args) {
  System.out.println(MessageFormat.format("Can''t resolve path {0}.",
      
                new Object[]{"c:/monitor"}));
}
}
