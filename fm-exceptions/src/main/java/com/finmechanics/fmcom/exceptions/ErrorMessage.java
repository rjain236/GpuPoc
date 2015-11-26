/*
 * Copyright (c) 2014 Finmechanics Pte Ltd. All rights reserved. fmCom project series
 */
package com.finmechanics.fmcom.exceptions;

/**
 * The Interface ErrorMessage. This is the interface that all enums must implement in order for the
 * builder to accept error codes properly.
 */
public interface ErrorMessage {

  /**
   * Gets the error code.
   *
   * @return the error code
   */
  String getCode();

  /**
   * Gets the error message.
   *
   * @return the error message
   */
  String getErrorMsg();

  /**
   * Gets the name of the enum. should return super.name(); in case of enums
   * 
   * @return the name of the enum
   */
  String getName();

}
