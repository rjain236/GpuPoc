/*
 * Copyright (c) 2014 Finmechanics Pte Ltd. All rights reserved. fmCom project series
 */
package com.finmechanics.fmcom.exceptions.validators;

import com.finmechanics.fmcom.exceptions.ErrorMessage;

/**
 * The Enum FinValidatorErrorMessages.
 */
public enum FinValidatorErrorMessages implements ErrorMessage {

  /** The finval arg has len. */
  FINVAL_ARG_HAS_LEN("V00-000-001",
      "[Assertion failed] - this String argument must have length; it must not be null or empty"),
  /** The finval arg has no substring. */
  FINVAL_ARG_HAS_NO_SUBSTRING("V00-000-002",
      "[Assertion failed] - this String argument must not contain the substring [${substring} ]"),
  /** The finval arg has text. */
  FINVAL_ARG_HAS_TEXT("V00-000-003",
      "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank"),
  /** The finval arg not null. */
  FINVAL_ARG_NOT_NULL("V00-000-004",
      "[Assertion failed] - this argument is required; it must not be null"),
  /** The finval arg null. */
  FINVAL_ARG_NULL("V00-000-005", "[Assertion failed] - the object argument must be null"),
  /** The finval array no null elements. */
  FINVAL_ARRAY_NO_NULL_ELEMENTS("V00-000-006",
      "[Assertion failed] - this array must not contain any null elements"),
  /** The finval array not empty. */
  FINVAL_ARRAY_NOT_EMPTY("V00-000-007",
      "[Assertion failed] - this array must not be empty: it must contain at least 1 element"),
  /** The finval col not empty. */
  FINVAL_COL_NOT_EMPTY("V00-000-008",
      "[Assertion failed] - this collection must not be empty: it must contain at least 1 element"),
  /** The finval exp true. */
  FINVAL_EXP_TRUE("V00-000-009", "[Assertion failed] - this expression must be true"),
  /** The finval exp true. */

  FINVAL_EXP_FALSE("V00-000-010", "[Assertion failed] - this expression must be false"),

  /** The finval map no null elements. */
  FINVAL_MAP_NO_NULL_ELEMENTS("V00-000-011",
      "[Assertion failed] - this map must not be empty; it must contain at least one entry"),
  /** The finval state inv true. */
  FINVAL_STATE_INV_TRUE("V00-000-012", "[Assertion failed] - this state invariant must be true"),
  /** The finval type not null. */
  FINVAL_TYPE_NOT_NULL("V00-000-013", "Type to check against must not be null"),

  ;

  /** The code. */
  private final String code;

  /** The error msg. */
  private final String errorMsg;

  /**
   * Instantiates a new fin validator error messages.
   *
   * @param code the code
   * @param errMessage the err message
   */
  private FinValidatorErrorMessages(String code, String errMessage) {
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
  @Override
  public String getName() {

    return super.name();
  }

}
