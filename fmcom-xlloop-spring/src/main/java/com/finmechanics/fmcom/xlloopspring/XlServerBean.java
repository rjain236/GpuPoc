/*
 * @author Avinash Johnson
 */
package com.finmechanics.fmcom.xlloopspring;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.boris.xlloop.FunctionServer;
import org.boris.xlloop.handler.CompositeFunctionHandler;
import org.boris.xlloop.handler.DebugFunctionHandler;
import org.boris.xlloop.handler.FunctionInformationHandler;
import org.boris.xlloop.reflect.ReflectFunctionHandler;
import org.boris.xlloop.util.XLoperObjectConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.threeten.bp.ZonedDateTime;

import com.finmechanics.fmcom.annotations.xlserver.NonSpringXlService;
import com.finmechanics.fmcom.annotations.xlserver.XlService;
import com.finmechanics.fmcom.exceptions.ErrPrettyPrint;

/**
 * The Class XlServerBean.
 */
@Component
@Lazy(false)
public class XlServerBean implements ApplicationContextAware, SmartLifecycle {

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(XlServerBean.class);

  /** The application context. */
  private ApplicationContext applicationContext;

  private ExecutorService executor;

  /** The spring function handler. */
  private SpringFunctionHandler springFunctionHandler;
  
	@Autowired
	private ConverterSpringBean converter;
	    
	public ConverterSpringBean getConverter(){
		return converter;
	}
	
	public void setConverter(ConverterSpringBean converter){
		this.converter = converter;
	}

  /** The fs. */
  private FunctionServer fs;

  public XlServerBean() {
    System.out.println("CREATED XLSERVER BEAN ....................");
  }

  @Autowired
  public void setSpringFunctionHandler(SpringFunctionHandler springFunctionHandler) {
    this.springFunctionHandler = springFunctionHandler;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework
   * .context.ApplicationContext)
   */
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    System.out.println("setApplicationContext called");
    System.out.println("setApplicationContext:: Bean Definition Names="
        + Arrays.toString(applicationContext.getBeanDefinitionNames()));
    this.applicationContext = applicationContext;
  }



  /**
   * Initserver.
   */
  public void initserver() {
    LOG.info("Starting initialization of xl function server");
    fs = new FunctionServer();
    CompositeFunctionHandler cfh = new CompositeFunctionHandler();
    FunctionInformationHandler firh = new FunctionInformationHandler();
    ClassPathScanningCandidateComponentProvider nonSpringBeanScanner =
        new ClassPathScanningCandidateComponentProvider(false);
    nonSpringBeanScanner.addIncludeFilter(new AnnotationTypeFilter(NonSpringXlService.class));
    LOG.info("xl function server: created filter");
    NonSpringFunctionHandler rfh = new NonSpringFunctionHandler(converter.getInstance());
//    FunctionInformationHandler firh2 = new FunctionInformationHandler();
    try {
      for (BeanDefinition beanDefinition : nonSpringBeanScanner.findCandidateComponents("com.codefellas")) {
        System.out.println("**Scanning bean " + beanDefinition.getBeanClassName());
        String beanClassName = beanDefinition.getBeanClassName();
		rfh.addBean("", beanClassName);
      }
      firh.add(rfh.getFunctions());
      cfh.add(rfh);
    } catch (Exception exception) {
      exception.printStackTrace();
      LOG.error("xl function server: error with bean definitions");
    }
    
    ClassPathScanningCandidateComponentProvider springBeanScanner =
            new ClassPathScanningCandidateComponentProvider(false);
        springBeanScanner.addIncludeFilter(new AnnotationTypeFilter(XlService.class));
        LOG.info("xl function server: created filter");
        try {
          for (BeanDefinition beanDefinition : springBeanScanner.findCandidateComponents("com.codefellas")) {
            System.out.println("**Scanning bean " + beanDefinition.getBeanClassName());
            String beanClassName = beanDefinition.getBeanClassName();
            // beanDefinition.qualifiers

            springFunctionHandler.addBean("", beanClassName);
          }
        } catch (Exception exception) {
          exception.printStackTrace();
          LOG.error("xl function server: error with bean definitions");
        }
        LOG.info("xl function server: creating function server instance");
        firh.add(springFunctionHandler.getFunctions());
        LOG.info("xl function server: added FunctionInformationHandler");
        cfh.add(springFunctionHandler);
        cfh.add(firh);

    NonSpringFunctionHandler staticNonFmClassHandlers = new NonSpringFunctionHandler(converter.getInstance());
    staticNonFmClassHandlers.addBean("", ZonedDateTime.class.getName());
    staticNonFmClassHandlers.addBean("", Date.class.getName());
//    staticNonFmClassHandlers.addBean("", BigDecimal.class.getName());
    firh.add(staticNonFmClassHandlers.getFunctions());
    cfh.add(staticNonFmClassHandlers);
    cfh.add(firh);
    
        // Set the handlers
    fs.setFunctionHandler(new DebugFunctionHandler(cfh));

    // Run the engine
    LOG.info("Listening on port " + fs.getPort() + "...");
    try {
      if (executor == null) {
        executor = Executors.newFixedThreadPool(1);
        executor.execute(new Runnable() {

          @Override
          public void run() {
            try {
              fs.run();
            } catch (IOException e) {
              e.printStackTrace();
            }

          }
        });
      }

      LOG.info("Started xl function server");
    } catch (Exception exception) {
      LOG.error(ErrPrettyPrint.prettyPrintStr(exception, true));
    }
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.springframework.context.Lifecycle#start()
   */
  @Override
  public void start() {
    initserver();
  }

  /**
   * Stop server.
   */
  public void stopServer() {
    try {
      if (fs != null) {
        fs.stop();
        LOG.info("Stopped xl function server");
        fs = null;
      }
    } catch (IOException exception) {
      LOG.error(ErrPrettyPrint.prettyPrintStr(exception, true));
    }

    if (executor != null) {
      executor.shutdownNow();
      executor = null;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.springframework.context.Lifecycle#stop()
   */
  @Override
  public void stop() {
    LOG.info("Request to stop xl function server");
    stopServer();

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.springframework.context.Lifecycle#isRunning()
   */
  @Override
  public boolean isRunning() {
    if (fs == null) {
      return false;
    } else {
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.springframework.context.Phased#getPhase()
   */
  @Override
  public int getPhase() {
    return Integer.MAX_VALUE;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.springframework.context.SmartLifecycle#isAutoStartup()
   */
  @Override
  public boolean isAutoStartup() {
    return true;
  }


  /*
   * (non-Javadoc)
   *
   * @see org.springframework.context.SmartLifecycle#stop(java.lang.Runnable)
   */
  @Override
  public void stop(Runnable callback) {
    LOG.info("Request to stop xl function server");
    stopServer();
  }
}
