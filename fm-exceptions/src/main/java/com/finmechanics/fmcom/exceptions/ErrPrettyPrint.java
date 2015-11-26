/*
 * Copyright (c) 2014 Finmechanics Pte Ltd. All rights reserved. fmCom project series
 */
package com.finmechanics.fmcom.exceptions;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * The Class ErrPrettyPrint.
 */
public abstract class ErrPrettyPrint {

  /** The Constant LOG_DIVIDER_END. */
  private static final String LOG_DIVIDER_END = "*********************** "
      + "ERROR PRINT ENDS ****************************";

  /** The Constant LOG_DIVIDER_START. */
  private static final String LOG_DIVIDER_START = "*********************** "
      + "ERROR PRINT STARTS ****************************\n";

  /** The Constant LOG_SPACER. */
  private static final String LOG_SPACER = "     ***************     ";

  /** The Constant NL. */
  private static final String NL = "\n";

  /**
   * Pretty print.
   *
   * @param exception the exception
   * @param recursive the recursive
   * @return the string builder
   */
  public static final StringBuilder prettyPrint(final Throwable exception, boolean recursive) {
    StringBuilder stringBuilder = new StringBuilder(LOG_DIVIDER_START);

    if (exception != null) {
      if (exception.getMessage() != null) {
        stringBuilder.append(exception.getMessage());
        stringBuilder.append(NL);

      }
      stringBuilder.append(ExceptionUtils.getStackTrace(exception));
      @SuppressWarnings("rawtypes")
      List list = ExceptionUtils.getThrowableList(exception);
      Throwable rootCause = list.size() < 2 ? null : (Throwable) list.get(list.size() - 1);

      if (rootCause != null) {
        stringBuilder.append(LOG_SPACER + "ROOT CAUSE" + LOG_SPACER + NL);
        stringBuilder.append(ExceptionUtils.getStackTrace(rootCause));
        stringBuilder.append(NL);
      }

      if (recursive == true && list != null && list.size() > 2) {

        for (int i = 1; i < list.size(); i++) {
          Throwable current = (Throwable) list.get(i);
          stringBuilder.append(LOG_SPACER + "STACK: " + i + " " + LOG_SPACER + NL);
          stringBuilder.append(ExceptionUtils.getStackTrace(current));
          stringBuilder.append(NL);

        }
      }
    }
    stringBuilder.append(LOG_DIVIDER_END);
    return stringBuilder;
  }

  /**
   * Pretty print simple.
   *
   * @param exception the exception
   * @return the string
   */
  public static final String prettyPrintSimple(final Exception exception) {
    String msg = exception.getMessage();
    return StringUtils.isEmpty(msg) ? "an error occured" : msg;
  }

  /**
   * Pretty print str.
   *
   * @param exception the exception
   * @param recursive the recursive
   * @return the string
   */
  public static final String prettyPrintStr(final Exception exception, boolean recursive) {

    return prettyPrint(exception, recursive).toString();
  }

  /**
   * Instantiates a new err pretty print.
   */
  private ErrPrettyPrint() {

  }

}
