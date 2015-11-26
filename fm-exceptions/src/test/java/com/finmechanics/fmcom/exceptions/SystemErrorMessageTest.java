package com.finmechanics.fmcom.exceptions;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.finmechanics.fmcom.exceptions.validators.FinValidatorErrorMessages;

@RunWith(Parameterized.class)
public class SystemErrorMessageTest {

  @Parameterized.Parameters
  public static Iterable<Object[]> data() {
    return Arrays.asList(new Object[][] { {SystemErrorMessage.class},
        {FinValidatorErrorMessages.class}});
  }

  @Parameter(value = 0)
  public Class<?> enumClassToTest;

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Test
  public void test() {
    ErrorMessage[] enumValues = (ErrorMessage[]) enumClassToTest.getEnumConstants();
    Assert.assertNotNull("enum should not have no values", enumValues);
    int len = enumValues.length;
    Assert.assertTrue(len > 0);

    Map<String, ErrorMessage> errorCodeToEnum = new HashMap<String, ErrorMessage>();
    Map<String, ErrorMessage> errorDescriptionToEnum = new HashMap<String, ErrorMessage>();

    for (int i = 0; i < len; i++) {
      ErrorMessage errorMessage = enumValues[i];
      String errorCode = errorMessage.getCode();
      String errorMsg = errorMessage.getErrorMsg();
      Assert.assertFalse("enum errorCode is invalid:" + errorMessage,
          StringUtils.isEmpty(errorCode));
      Assert.assertFalse("enum errorCode is invalid:" + errorMessage,
          StringUtils.isEmpty(errorCode.trim()));
      Assert.assertFalse("enum errorMsg is invalid:" + errorMessage, StringUtils.isEmpty(errorMsg));
      Assert.assertFalse("enum errorMsg is invalid:" + errorMessage,
          StringUtils.isEmpty(errorMsg.trim()));
      Assert.assertFalse("enum name is invalid:" + errorMessage,
          StringUtils.isEmpty(errorMessage.getName()));
      Assert.assertFalse("enum name is invalid:" + errorMessage,
          StringUtils.isEmpty(errorMessage.getName().trim()));

      Assert.assertTrue("enum errorCode is duplicated:" + errorCode,
          errorCodeToEnum.get(errorCode) == null);
      // Required ?
      Assert.assertTrue("enum errorMsg is duplicated:" + errorMsg,
          errorDescriptionToEnum.get(errorMsg) == null);
      errorCodeToEnum.put(errorCode, errorMessage);
      errorDescriptionToEnum.put(errorMsg, errorMessage);
      // Assert.assertThat("non empty string", isEmptyString(), "a");

    }

    Assert.assertEquals("errorCode map should have same number of entries", errorCodeToEnum.size(),
        len);
    Assert.assertEquals("errorMsg map should have same number of entries", errorCodeToEnum.size(),
        len);

  }

}
