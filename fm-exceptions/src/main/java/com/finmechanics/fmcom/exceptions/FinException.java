/*
 * Copyright (c) 2014 Finmechanics Pte Ltd. All rights reserved. fmCom project series
 */
package com.finmechanics.fmcom.exceptions;

/**
 * The Class FinException.
 */
public class FinException extends RuntimeException {

  /** The Constant GENERIC_ERROR_CODE. */
  private static final String GENERIC_ERROR_CODE = SystemErrorMessage.GENERIC_ERROR.getCode();

  /** The Constant GENERIC_ERROR_MESSAGE. */
  private static final String GENERIC_ERROR_MESSAGE = SystemErrorMessage.GENERIC_ERROR
      .getErrorMsg();

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 3449005326164022306L;

  /** The error code. */
  protected final String errorCode;

  /**
   * Instantiates a new fin exception. defaults error code to GENERIC_ERROR_CODE and error message
   * to GENERIC_ERROR_MESSAGE
   * 
   * Should avoid using it as using this construtor essentially means that you're not giving a
   * proper error code or error message
   */
  @Deprecated
  private FinException() {
    super(GENERIC_ERROR_MESSAGE);
    errorCode = GENERIC_ERROR_CODE;

  }

  /**
   * Instantiates a new fin exception. defaults error code to GENERIC_ERROR_CODE and error message
   * to GENERIC_ERROR_MESSAGE
   * 
   * Should avoid using it as using this construtor essentially means that you're not giving a
   * proper error code
   * 
   * @param message the message
   */
  @Deprecated
  public FinException(String message) {
    super(message);
    errorCode = GENERIC_ERROR_CODE;

  }

  /**
   * Instantiates a new fin exception. Using the cause ensures original error cause of not lost
   *
   * @param errorCode the error code
   * @param message the message
   * @param cause the cause
   */
  public FinException(final String errorCode, final String message, final Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;

  }

  /**
   * Instantiates a new fin exception. Defaults error code to GENERIC_ERROR_CODE and hence might not
   * be so useful to the end user
   * 
   * 
   *
   * @param message the message
   * @param cause the cause
   */
  public FinException(final String message, final Throwable cause) {
    super(message, cause);
    errorCode = GENERIC_ERROR_CODE;

  }

  /**
   * Instantiates a new fin exception.
   *
   * @param message the message
   * @param cause the cause
   * @param enableSuppression the enable suppression
   * @param writableStackTrace the writable stack trace
   */
  public FinException(final String message, final Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    errorCode = GENERIC_ERROR_CODE;

  }

  /**
   * Instantiates a new fin exception.
   *
   * @param cause the cause
   */
  public FinException(final Throwable cause) {
    super(cause);
    errorCode = GENERIC_ERROR_CODE;

  }

  /**
   * Gets the error code.
   *
   * @return the error code
   */
  public String getErrorCode() {
    return errorCode;
  }

}
