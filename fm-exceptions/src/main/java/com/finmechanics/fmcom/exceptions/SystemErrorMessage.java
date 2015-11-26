/*
 * Copyright (c) 2014 Finmechanics Pte Ltd. All rights reserved. fmCom project series
 */
package com.finmechanics.fmcom.exceptions;

/**
 * The Enum SystemErrorMessage.
 */
public enum SystemErrorMessage implements ErrorMessage {

  /** The generic error. */
  GENERIC_ERROR("G00-000-002", "An error occured in the system."),
  /** The generic fatal error. */
  GENERIC_FATAL_ERROR("G00-000-000",
      "A fatal error occured in the system. Please contact system admin.");

  /** The code. */
  private final String code;

  /** The error msg. */
  private final String errorMsg;

  /**
   * Instantiates a new system error message.
   *
   * @param code the code
   * @param errMessage the err message
   */
  private SystemErrorMessage(String code, String errMessage) {
    this.code = code;
    this.errorMsg = errMessage;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.finmechanics.fmcom.exceptions.ErrorMessage#getCode()
   */
  public String getCode() {
    return code;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.finmechanics.fmcom.exceptions.ErrorMessage#getErrorMsg()
   */
  public String getErrorMsg() {
    return errorMsg;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.finmechanics.fmcom.exceptions.ErrorMessage#getName()
   */
  public String getName() {
    return super.name();
  }

}
