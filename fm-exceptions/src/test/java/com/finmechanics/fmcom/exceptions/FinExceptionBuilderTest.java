package com.finmechanics.fmcom.exceptions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.finmechanics.fmcom.exceptions.validators.FinValidatorException;

@RunWith(Parameterized.class)
public class FinExceptionBuilderTest {

  @Parameterized.Parameters
  public static Iterable<Object[]> data() {
    return Arrays.asList(new Object[][] { {FinException.class}, {FinValidatorException.class}});
  }

  @Parameter(value = 0)
  public Class<? extends FinException> clazz;

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Test
  public void test() throws InstantiationException, IllegalAccessException {

    FinException test001 = (new FinExceptionBuilder(clazz)).setCause(null).build();
    checkValues(test001, "case test001 cause null, returns default message and code",
        SystemErrorMessage.GENERIC_ERROR.getCode(), SystemErrorMessage.GENERIC_ERROR.getErrorMsg());

    FinException test002 =
        (new FinExceptionBuilder(clazz)).setErrorCode(
            SystemErrorMessage.GENERIC_FATAL_ERROR.getCode()).build();
    checkValues(test002,
        "case test002 error code set but not error message, returns default message",
        SystemErrorMessage.GENERIC_FATAL_ERROR.getCode(),
        SystemErrorMessage.GENERIC_ERROR.getErrorMsg());

    FinException test003 =
        (new FinExceptionBuilder(clazz)).setMessage(
            SystemErrorMessage.GENERIC_FATAL_ERROR.getErrorMsg()).build();
    checkValues(test003, "case test003 message set but not error code, returns default code",
        SystemErrorMessage.GENERIC_ERROR.getCode(),
        SystemErrorMessage.GENERIC_FATAL_ERROR.getErrorMsg());

    FinException test004 =
        (new FinExceptionBuilder(clazz)).setMessage(ErrorMessageEnumTestData.SIMPLE_ENUM).build();
    checkValues(test004, "case test004 error message and code set via enum",
        ErrorMessageEnumTestData.SIMPLE_ENUM.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test005 =
        (new FinExceptionBuilder(clazz)).setMessage(ErrorMessageEnumTestData.NUMBER_ENUM_001)
            .build();
    checkValues(test005,
        "case test005 error message and code set via enum, ignorable numbered param",
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg());

    FinException test006 =
        (new FinExceptionBuilder(clazz)).setMessage(ErrorMessageEnumTestData.NAMED_ENUM_001)
            .build();
    checkValues(test006, "case test006 error message and code set via enum, ignorable named param",
        ErrorMessageEnumTestData.NAMED_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());

    Map<String, Object> nullMap = null;
    Map<String, Object> emptyMap = new HashMap<String, Object>();
    Map<String, Object> oneItemMap = new HashMap<String, Object>();
    oneItemMap.put("myname", "REPLACEDVALUE");
    Map<String, Object> oneItemNegativeMap = new HashMap<String, Object>();
    oneItemNegativeMap.put("notmyname", "NOTREPLACEDVALUE");
    Map<String, Object> twoItemMap = new HashMap<String, Object>();
    twoItemMap.put("myname", "REPLACEDVALUE");
    twoItemMap.put("notmyname", "NOTREPLACEDVALUE");

    // 5 COMBINATIONS * 3 TYPES OF MESSAGES TEST
    // set 1
    FinException test007_11 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg(), nullMap).build();
    checkValues(test007_11, "case test007_11", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test007_12 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg(), emptyMap).build();
    checkValues(test007_12, "case test007_12", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test007_13 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg(), oneItemMap).build();
    checkValues(test007_13, "case test007_13", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test007_14 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg(), oneItemNegativeMap).build();
    checkValues(test007_14, "case test007_14", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test007_15 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg(), twoItemMap).build();
    checkValues(test007_15, "case test007_15", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    // set 2
    FinException test007_21 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg(), nullMap).build();
    checkValues(test007_21, "case test007_21", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg());

    FinException test007_22 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg(), emptyMap).build();
    checkValues(test007_22, "case test007_22", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg());

    FinException test007_23 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg(), oneItemMap).build();
    checkValues(test007_23, "case test007_23", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg());

    FinException test007_24 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg(), oneItemNegativeMap).build();
    checkValues(test007_24, "case test007_24", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg());

    FinException test007_25 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg(), twoItemMap).build();
    checkValues(test007_25, "case test007_25", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg());

    // set 3
    FinException test007_31 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg(), nullMap).build();
    checkValues(test007_31, "case test007_31", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());

    FinException test007_32 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg(), emptyMap).build();
    checkValues(test007_32, "case test007_32", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());

    FinException test007_33 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg(), oneItemMap).build();
    checkValues(test007_33, "case test007_33", SystemErrorMessage.GENERIC_ERROR.getCode(),
        "Simple named enum REPLACEDVALUE");

    FinException test007_34 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg(), oneItemNegativeMap).build();
    checkValues(test007_34, "case test007_34", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());

    FinException test007_35 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg(), twoItemMap).build();
    checkValues(test007_35, "case test007_35", SystemErrorMessage.GENERIC_ERROR.getCode(),
        "Simple named enum REPLACEDVALUE");


    // 5 COMBINATIONS * 3 TYPES OF MESSAGES TEST
    // set 1
    FinException test008_11 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM, nullMap).build();
    checkValues(test008_11, "case test008_11", ErrorMessageEnumTestData.SIMPLE_ENUM.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test008_12 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM, emptyMap).build();
    checkValues(test008_12, "case test008_12", ErrorMessageEnumTestData.SIMPLE_ENUM.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test008_13 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM, oneItemMap).build();
    checkValues(test008_13, "case test008_13", ErrorMessageEnumTestData.SIMPLE_ENUM.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test008_14 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM, oneItemNegativeMap).build();
    checkValues(test008_14, "case test008_14", ErrorMessageEnumTestData.SIMPLE_ENUM.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test008_15 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM, twoItemMap).build();
    checkValues(test008_15, "case test008_15", ErrorMessageEnumTestData.SIMPLE_ENUM.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    // set 2
    FinException test008_21 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001, nullMap).build();
    checkValues(test008_21, "case test008_21", ErrorMessageEnumTestData.NUMBER_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg());

    FinException test008_22 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001, emptyMap).build();
    checkValues(test008_22, "case test008_22", ErrorMessageEnumTestData.NUMBER_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg());

    FinException test008_23 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001, oneItemMap).build();
    checkValues(test008_23, "case test008_23", ErrorMessageEnumTestData.NUMBER_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg());

    FinException test008_24 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001, oneItemNegativeMap).build();
    checkValues(test008_24, "case test008_24", ErrorMessageEnumTestData.NUMBER_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg());

    FinException test008_25 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001, twoItemMap).build();
    checkValues(test008_25, "case test008_25", ErrorMessageEnumTestData.NUMBER_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg());

    // set 3
    FinException test008_31 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001, nullMap).build();
    checkValues(test008_31, "case test008_31", ErrorMessageEnumTestData.NAMED_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());

    FinException test008_32 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001, emptyMap).build();
    checkValues(test008_32, "case test008_32", ErrorMessageEnumTestData.NAMED_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());

    FinException test008_33 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001, oneItemMap).build();
    checkValues(test008_33, "case test008_33", ErrorMessageEnumTestData.NAMED_ENUM_001.getCode(),
        "Simple named enum REPLACEDVALUE");

    FinException test008_34 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001, oneItemNegativeMap).build();
    checkValues(test008_34, "case test008_34", ErrorMessageEnumTestData.NAMED_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());

    FinException test008_35 =
        (new FinExceptionBuilder(clazz)).setNamedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001, twoItemMap).build();
    checkValues(test008_35, "case test008_35", ErrorMessageEnumTestData.NAMED_ENUM_001.getCode(),
        "Simple named enum REPLACEDVALUE");

    Object[] nullList = null;
    Object[] emptyList = new Object[0];
    Object[] oneItemList = new Object[] {"REPLACEDVALUE"};
    Object[] oneItemNegativeList = new Object[] {"NOTREPLACEDVALUE"};
    Object[] twoItemList = new Object[] {"REPLACEDVALUE", "NOTREPLACEDVALUE"};

    // 5 COMBINATIONS * 3 TYPES OF MESSAGES TEST
    // set 1
    FinException test009_11 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg(), nullList).build();
    checkValues(test009_11, "case test009_11", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test009_12 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg(), emptyList).build();
    checkValues(test009_12, "case test009_12", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test009_13 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg(), oneItemList).build();
    checkValues(test009_13, "case test009_13", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test009_14 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg(), oneItemNegativeList).build();
    checkValues(test009_14, "case test009_14", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test009_15 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg(), twoItemList).build();
    checkValues(test009_15, "case test009_15", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    // set 2
    FinException test009_21 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg(), nullList).build();
    checkValues(test009_21, "case test009_21", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg());

    FinException test009_22 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg(), emptyList).build();
    checkValues(test009_22, "case test009_22", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg());

    FinException test009_23 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg(), oneItemList).build();
    checkValues(test009_23, "case test009_23", SystemErrorMessage.GENERIC_ERROR.getCode(),
        "Simple numbered enum REPLACEDVALUE");

    FinException test009_24 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg(), oneItemNegativeList).build();
    checkValues(test009_24, "case test009_24", SystemErrorMessage.GENERIC_ERROR.getCode(),
        "Simple numbered enum NOTREPLACEDVALUE");

    FinException test009_25 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg(), twoItemList).build();
    checkValues(test009_25, "case test009_25", SystemErrorMessage.GENERIC_ERROR.getCode(),
        "Simple numbered enum REPLACEDVALUE");

    // set 3
    FinException test009_31 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg(), nullList).build();
    checkValues(test009_31, "case test009_31", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());

    FinException test009_32 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg(), emptyList).build();
    checkValues(test009_32, "case test009_32", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());

    FinException test009_33 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg(), oneItemList).build();
    checkValues(test009_33, "case test009_33", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());

    FinException test009_34 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg(), oneItemNegativeList).build();
    checkValues(test009_34, "case test009_34", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());

    FinException test009_35 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg(), twoItemList).build();
    checkValues(test009_35, "case test009_35", SystemErrorMessage.GENERIC_ERROR.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());


    // 5 COMBINATIONS * 3 TYPES OF MESSAGES TEST
    // set 1
    FinException test010_11 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM, nullList).build();
    checkValues(test010_11, "case test010_11", ErrorMessageEnumTestData.SIMPLE_ENUM.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test010_12 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM, emptyList).build();
    checkValues(test010_12, "case test010_12", ErrorMessageEnumTestData.SIMPLE_ENUM.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test010_13 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM, oneItemList).build();
    checkValues(test010_13, "case test010_13", ErrorMessageEnumTestData.SIMPLE_ENUM.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test010_14 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM, oneItemNegativeList).build();
    checkValues(test010_14, "case test010_14", ErrorMessageEnumTestData.SIMPLE_ENUM.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    FinException test010_15 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.SIMPLE_ENUM, twoItemList).build();
    checkValues(test010_15, "case test010_15", ErrorMessageEnumTestData.SIMPLE_ENUM.getCode(),
        ErrorMessageEnumTestData.SIMPLE_ENUM.getErrorMsg());

    // set 2
    FinException test010_21 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001, nullList).build();
    checkValues(test010_21, "case test010_21", ErrorMessageEnumTestData.NUMBER_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg());

    FinException test010_22 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001, emptyList).build();
    checkValues(test010_22, "case test010_22", ErrorMessageEnumTestData.NUMBER_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg());

    FinException test010_23 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001, oneItemList).build();
    checkValues(test010_23, "case test010_23", ErrorMessageEnumTestData.NUMBER_ENUM_001.getCode(),
        "Simple numbered enum REPLACEDVALUE");

    FinException test010_24 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001, oneItemNegativeList).build();
    checkValues(test010_24, "case test010_24", ErrorMessageEnumTestData.NUMBER_ENUM_001.getCode(),
        "Simple numbered enum NOTREPLACEDVALUE");

    FinException test010_25 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NUMBER_ENUM_001, twoItemList).build();
    checkValues(test010_25, "case test010_25", ErrorMessageEnumTestData.NUMBER_ENUM_001.getCode(),
        "Simple numbered enum REPLACEDVALUE");

    // set 3
    FinException test010_31 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001, nullList).build();
    checkValues(test010_31, "case test010_31", ErrorMessageEnumTestData.NAMED_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());

    FinException test010_32 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001, emptyList).build();
    checkValues(test010_32, "case test010_32", ErrorMessageEnumTestData.NAMED_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());

    FinException test010_33 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001, oneItemList).build();
    checkValues(test010_33, "case test010_33", ErrorMessageEnumTestData.NAMED_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());

    FinException test010_34 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001, oneItemNegativeList).build();
    checkValues(test010_34, "case test010_34", ErrorMessageEnumTestData.NAMED_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());

    FinException test010_35 =
        (new FinExceptionBuilder(clazz)).setNumberedParameterMessage(
            ErrorMessageEnumTestData.NAMED_ENUM_001, twoItemList).build();
    checkValues(test010_35, "case test010_35", ErrorMessageEnumTestData.NAMED_ENUM_001.getCode(),
        ErrorMessageEnumTestData.NAMED_ENUM_001.getErrorMsg());



  }

  public void checkValues(FinException testSubject, String testDescription,
      String expectedErrorCode, String expectedErrorMessage) throws InstantiationException,
      IllegalAccessException {

    // errorCode and errorMessage should never be null. So never put any null
    // checks in the test case.
    Assert.assertNotNull(testSubject);
    Assert.assertEquals(testDescription, expectedErrorCode, testSubject.getErrorCode());
    Assert.assertEquals(testDescription, expectedErrorMessage, testSubject.getMessage());

  }

  public static enum ErrorMessageEnumTestData implements ErrorMessage {
    SIMPLE_ENUM("SIMPLE_ENUM", "Simple error message"), NUMBER_ENUM_001("NUMBER_ENUM_001",
        "Simple numbered enum {0}"), NAMED_ENUM_001("NAMED_ENUM_001", "Simple named enum ${myname}");

    private final String code;
    private final String errMessage;

    private ErrorMessageEnumTestData(String code, String errMessage) {
      this.code = code;
      this.errMessage = errMessage;
    }

    @Override
    public String getCode() {
      return code;
    }

    @Override
    public String getErrorMsg() {
      return errMessage;
    }

    @Override
    public String getName() {

      return name();
    }

  }

}
