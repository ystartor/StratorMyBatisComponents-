package com.ystartor.logging.slf4j;

import com.ystartor.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.spi.LocationAwareLogger;

/**
 *  TODO Slf4j封装日志
 */
public class Slf4jImpl implements Log {

    private Log log;

    public Slf4jImpl(String clazz){
        Logger logger = LoggerFactory.getLogger(clazz);
        //TODO LocationAwareLogger 这个类不知道干啥的
        if (logger instanceof LocationAwareLogger){
            // check for slf4j >= 1.6 method signature
            try {
                logger.getClass().getMethod("log", Marker.class, String.class, int.class, String.class, Object[].class, Throwable.class);
                log =  new Slf4jLocationAwareLoggerImpl((LocationAwareLogger) logger);
                return;
            } catch (NoSuchMethodException | SecurityException e) {
                //
            }
        }
        log = new Slf4jLoggerImpl(logger);
    }


    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    @Override
    public void error(String s, Throwable e) {
        log.error(s, e);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void debug(String s) {
        log.debug(s);
    }

    @Override
    public void trace(String s) {
        log.trace(s);
    }

    @Override
    public void warn(String s) {
        log.warn(s);
    }


}
