package com.finmechanics.fmcom.xlloopspring;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;


import org.apache.lucene.util.Parameter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = {"classpath*:/moduleconfig/*applicationContext*.xml"})
public class FmStandAloneServerExample {
  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {{"test01"}

    });
  }

  @Parameter(value = 0)
  public String testCaseId;

  @BeforeClass
  public static void setEnvVariable() {
    System.setProperty("fmConfigLocationExtn", "testenv");
  }

  private TestContextManager testContextManager;

  @Before
  public void setUpContext() throws Exception {
    // this is where the magic happens, we actually do "by hand" what the
    // spring runner would do for
    // us,
    // read the JavaDoc for the class bellow to know exactly what it does,
    // the method names are
    // quite accurate though
    this.testContextManager = new TestContextManager(getClass());
    this.testContextManager.prepareTestInstance(this);
  }

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void test() {
    System.out.println("Test started " + testCaseId);
    System.out.println("End test");
    final CountDownLatch latch = new CountDownLatch(1);
    try {
      latch.await();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  System.out.println("really end test");
  }

}
