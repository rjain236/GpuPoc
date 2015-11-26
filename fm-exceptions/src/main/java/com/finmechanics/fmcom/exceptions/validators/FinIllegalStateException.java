/*
 * Copyright (c) 2014 Finmechanics Pte Ltd. All rights reserved. fmCom project series
 */
package com.finmechanics.fmcom.exceptions.validators;


/**
 * The Class FinIllegalStateException.
 */
public class FinIllegalStateException extends IllegalStateException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -3533995734860871359L;

  /**
   * Instantiates a new fin illegal state exception.
   *
   * @param message the message
   */
  public FinIllegalStateException(String message) {
    super(message);

  }

  /**
   * Instantiates a new fin illegal state exception.
   *
   * @param cause the cause
   */
  public FinIllegalStateException(Throwable cause) {
    super(cause);

  }

  // public FinIllegalStateException(String errorCode, String message,
  // Throwable cause) {
  // super(errorCode, message, cause);
  // }
  //
  // public FinIllegalStateException(String message, Throwable cause) {
  // super(message, cause);
  //
  // }
  //
  // public FinIllegalStateException(String message, Throwable cause, boolean
  // enableSuppression, boolean writableStackTrace) {
  // super(message, cause, enableSuppression, writableStackTrace);
  //
  // }

}
