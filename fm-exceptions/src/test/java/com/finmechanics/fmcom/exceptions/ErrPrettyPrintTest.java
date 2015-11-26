package com.finmechanics.fmcom.exceptions;

import static com.finmechanics.fmcom.exceptions.ErrPrettyPrint.prettyPrint;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ErrPrettyPrintTest {

  private static final int ERROR_STACK_SIZE = 100;

  @Test
  public void testSimpleCase() {
    Exception baseException = new Exception("baseException");
    String errMessageRec = prettyPrint(baseException, true).toString();
    String errMessageNonRec = prettyPrint(baseException, false).toString();
    assertTrue("should contain " + errMessageRec, errMessageRec.contains("baseException"));
    assertTrue("should contain " + errMessageNonRec, errMessageNonRec.contains("baseException"));

  }

  @Test
  public void testSimpleCaseRoot01() {
    Exception baseException = new Exception("baseException");
    Exception rootException = new Exception("rootException");
    baseException.initCause(rootException);

    String errMessageRec = prettyPrint(baseException, true).toString();
    String errMessageNonRec = prettyPrint(baseException, false).toString();
    assertTrue("should contain " + errMessageRec, errMessageRec.contains("baseException"));
    assertTrue("should contain " + errMessageNonRec, errMessageNonRec.contains("baseException"));
    assertTrue("should contain " + errMessageRec, errMessageRec.contains("rootException"));
    assertTrue("should contain " + errMessageNonRec, errMessageNonRec.contains("rootException"));

  }

  @Test
  public void testSimpleCaseRoot02() {
    Exception rootException = new Exception("rootException");
    Exception baseException = new Exception("baseException", rootException);

    String errMessageRec = prettyPrint(baseException, true).toString();
    String errMessageNonRec = prettyPrint(baseException, false).toString();
    assertTrue("should contain " + errMessageRec, errMessageRec.contains("baseException"));
    assertTrue("should contain " + errMessageNonRec, errMessageNonRec.contains("baseException"));
    assertTrue("should contain " + errMessageRec, errMessageRec.contains("rootException"));
    assertTrue("should contain " + errMessageNonRec, errMessageNonRec.contains("rootException"));

  }

  @Test
  public void testSimpleCaseNested03() {
    Exception rootException = new Exception("rootException");
    Exception nestedException = new Exception("nestedException", rootException);
    Exception baseException = new Exception("baseException", nestedException);

    String errMessageRec = prettyPrint(baseException, true).toString();
    String errMessageNonRec = prettyPrint(baseException, false).toString();
    assertTrue("should contain " + errMessageRec, errMessageRec.contains("baseException"));
    assertTrue("should contain " + errMessageNonRec, errMessageNonRec.contains("baseException"));
    assertTrue("should contain " + errMessageRec, errMessageRec.contains("rootException"));
    assertTrue("should contain " + errMessageNonRec, errMessageNonRec.contains("rootException"));
    assertTrue("should contain " + errMessageRec, errMessageRec.contains("nestedException"));
    assertTrue("should NOT contain " + errMessageNonRec,
        errMessageNonRec.contains("nestedException"));


  }


  @Test
  public void testHugeStack() {
    Exception rootException = new Exception("rootException");
    Exception baseException = new Exception("baseException");
    Exception nestedException = baseException;

    for (int i = 0; i < ERROR_STACK_SIZE; i++) {
      if (i != 0) {
        nestedException = new Exception("nestedException" + i, nestedException);
      } else {
        nestedException = new Exception("nestedException" + i, rootException);
      }
    }

    baseException.initCause(nestedException);
    String errMessageRec = prettyPrint(baseException, true).toString();
    String errMessageNonRec = prettyPrint(baseException, false).toString();

    for (int i = ERROR_STACK_SIZE - 1; i >= 0; --i) {
      assertTrue("should contain " + errMessageRec, errMessageRec.contains("baseException"));
      assertTrue("should contain " + errMessageNonRec, errMessageNonRec.contains("baseException"));
      assertTrue("should contain " + errMessageRec, errMessageRec.contains("rootException"));
      assertTrue("should contain " + errMessageNonRec, errMessageNonRec.contains("rootException"));
      assertTrue("should contain " + errMessageRec, errMessageRec.contains("nestedException" + i));
      errMessageRec = errMessageRec.replaceAll("nestedException" + i, "");
    }

  }

  @Test
  public void testHugeSuppressedStack() {
    Throwable rootException = new Exception("rootException");
    Throwable baseException = new Exception("baseException");
    Throwable nestedException = baseException;

    for (int i = 0; i < ERROR_STACK_SIZE; i++) {
      if (i != 0) {
        Throwable tempThrowable = new Exception("nestedException" + i);
        tempThrowable.addSuppressed(nestedException);
        nestedException = tempThrowable;

      } else {
        Throwable tempThrowable = new Exception("nestedException" + i);
        tempThrowable.addSuppressed(rootException);
        nestedException = tempThrowable;

      }
    }

    baseException.addSuppressed(nestedException);
    String errMessageRec = prettyPrint(baseException, true).toString();
    String errMessageNonRec = prettyPrint(baseException, false).toString();

    for (int i = ERROR_STACK_SIZE - 1; i >= 0; --i) {
      assertTrue("should contain " + errMessageRec, errMessageRec.contains("baseException"));
      assertTrue("should contain " + errMessageNonRec, errMessageNonRec.contains("baseException"));
      assertTrue("should contain " + errMessageRec, errMessageRec.contains("rootException"));
      assertTrue("should contain " + errMessageNonRec, errMessageNonRec.contains("rootException"));
      assertTrue("should contain " + errMessageRec, errMessageRec.contains("nestedException" + i));
      errMessageRec = errMessageRec.replaceAll("nestedException" + i, "");
    }

  }

  @Test
  public void testHugeSuppressedStackLateral() {
    Throwable rootException = new Exception("rootException");
    Throwable nestedException = new Exception("nestedException", rootException);
    Throwable baseException = new Exception("baseException", nestedException);

    for (int i = 0; i < ERROR_STACK_SIZE; i++) {
      Throwable tempThrowable = new Exception("suppressedException" + i);
      nestedException.addSuppressed(tempThrowable);

    }
    String errMessageRec = prettyPrint(baseException, true).toString();
    String errMessageNonRec = prettyPrint(baseException, false).toString();

    // System.err.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% CASE NON RECURSIVE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    // System.err.println(errMessageNonRec);
    // System.err.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% CASE RECURSIVE %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    // System.err.println(errMessageRec);
    // System.err.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% CASE print stacktrace %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    // baseException.printStackTrace();

    for (int i = ERROR_STACK_SIZE - 1; i >= 0; --i) {
      assertTrue("should contain " + errMessageRec, errMessageRec.contains("baseException"));
      assertTrue("should contain " + errMessageNonRec, errMessageNonRec.contains("baseException"));
      assertTrue("should contain " + errMessageRec, errMessageRec.contains("rootException"));
      assertTrue("should contain " + errMessageNonRec, errMessageNonRec.contains("rootException"));
      assertTrue("should contain " + errMessageRec,
          errMessageRec.contains("suppressedException" + i));
      errMessageRec = errMessageRec.replaceAll("suppressedException" + i, "");
    }

  }



}
