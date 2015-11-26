/*
 * Copyright (c) 2014 Finmechanics Pte Ltd. All rights reserved. fmCom project series
 */
package com.finmechanics.fmcom.exceptions.validators;

import com.finmechanics.fmcom.exceptions.FinException;


/**
 * The Class FinValidatorException.
 */
public class FinValidatorException extends FinException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -3533995734860871359L;

  /**
   * Instantiates a new fin validator exception.
   *
   * @param message the message
   */
  public FinValidatorException(final String message) {
    super(message);

  }

  /**
   * Instantiates a new fin validator exception.
   *
   * @param errorCode the error code
   * @param message the message
   * @param cause the cause
   */
  public FinValidatorException(final String errorCode, final String message, final Throwable cause) {
    super(errorCode, message, cause);
  }

  /**
   * Instantiates a new fin validator exception.
   *
   * @param message the message
   * @param cause the cause
   */
  public FinValidatorException(final String message, final Throwable cause) {
    super(message, cause);

  }

  /**
   * Instantiates a new fin validator exception.
   *
   * @param message the message
   * @param cause the cause
   * @param enableSuppression the enable suppression
   * @param writableStackTrace the writable stack trace
   */
  public FinValidatorException(final String message, final Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);

  }

  /**
   * Instantiates a new fin validator exception.
   *
   * @param cause the cause
   */
  public FinValidatorException(final Throwable cause) {
    super(cause);

  }

}
