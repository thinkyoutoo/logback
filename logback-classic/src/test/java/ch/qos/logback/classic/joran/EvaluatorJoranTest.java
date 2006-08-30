/**
 * LOGBack: the generic, reliable, fast and flexible logging framework.
 *
 * Copyright (C) 1999-2006, QOS.ch
 *
 * This library is free software, you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation.
 */
package ch.qos.logback.classic.joran;

import java.util.Map;

import junit.framework.TestCase;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.boolex.JaninoEventEvaluator;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.util.Constants;
import ch.qos.logback.core.CoreGlobal;
import ch.qos.logback.core.boolex.EvaluationException;
import ch.qos.logback.core.util.StatusPrinter;


public class EvaluatorJoranTest extends TestCase {

  public void xtest() throws NullPointerException, EvaluationException {
    JoranConfigurator jc = new JoranConfigurator();
    LoggerContext loggerContext = new LoggerContext();
    jc.setContext(loggerContext);
    jc.doConfigure(Constants.TEST_DIR_PREFIX + "input/joran/simpleEvaluator.xml");
    
    
    Map evalMap = (Map) loggerContext.getObject(CoreGlobal.EVALUATOR_MAP);
    assertNotNull(evalMap);
    //StatusPrinter.print(loggerContext.getStatusManager());
    JaninoEventEvaluator evaluator = (JaninoEventEvaluator) evalMap.get("msgEval");
    assertNotNull(evaluator);
    
    Logger logger = loggerContext.getLogger("xx");
    LoggingEvent event = new LoggingEvent("foo", logger, Level.DEBUG, "Hello world", null, null);
    StatusPrinter.print(loggerContext.getStatusManager());
    assertTrue(evaluator.evaluate(event));
    
    //StatusPrinter.print(loggerContext.getStatusManager());
  }
  
  public void testIgnoreMarker() throws NullPointerException, EvaluationException {
    JoranConfigurator jc = new JoranConfigurator();
    LoggerContext loggerContext = new LoggerContext();
    jc.setContext(loggerContext);
    jc.doConfigure(Constants.TEST_DIR_PREFIX + "input/joran/ignore.xml");
    
    
    
    Map evalMap = (Map) loggerContext.getObject(CoreGlobal.EVALUATOR_MAP);
    assertNotNull(evalMap);
    StatusPrinter.print(loggerContext.getStatusManager());
    
    Logger logger = loggerContext.getLogger("xx");
    
    //LoggingEvent event = new LoggingEvent("foo", logger, Level.DEBUG, "Hello world",null);
    //StatusPrinter.print(loggerContext.getStatusManager());
    //assertTrue(evaluator.evaluate(event));
    
    Marker ignoreMarker = MarkerFactory.getMarker("IGNORE");
    logger.debug("hello", new Exception("test"));
    logger.debug(ignoreMarker, "hello ignore", new Exception("test"));
    
    //logger.debug("hello", new Exception("test"));
    
    StatusPrinter.print(loggerContext.getStatusManager());
  }
}
