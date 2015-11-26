package com.finmechanics.fmcom.exceptions;



import org.junit.Assert;
import org.junit.Test;

public class FinExceptionTest {

  @Test(expected = IllegalAccessException.class)
  public void testCannotCreateException() throws InstantiationException, IllegalAccessException {
    FinException finException = FinException.class.newInstance();
    Assert.assertNull("Should not be able to create an exception without error message",
        finException);
  }


  public void testCreateException() throws InstantiationException, IllegalAccessException {
    FinException finExceptionRoot = new FinException("root cause");
    Exception finExceptionGeneric01 = new Exception();
    RuntimeException finExceptionGeneric02 = new RuntimeException();

    Assert.assertNotNull("should create with another finexception", new FinException(
        finExceptionRoot));
    Assert.assertNotNull("should create with checked finexception", new FinException(
        finExceptionGeneric01));
    Assert.assertNotNull("should create with runtime finexception", new FinException(
        finExceptionGeneric02));

    Assert.assertNotNull("should create with another finexception", new FinException("own error",
        finExceptionRoot));
    Assert.assertNotNull("should create with checked finexception", new FinException("own error",
        finExceptionGeneric01));
    Assert.assertNotNull("should create with runtime finexception", new FinException("own error",
        finExceptionGeneric02));


  }

}
