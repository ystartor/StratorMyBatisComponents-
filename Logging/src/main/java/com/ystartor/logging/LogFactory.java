package com.ystartor.logging;

import com.ystartor.logging.commons.JakartaCommonsLoggingImpl;
import com.ystartor.logging.exception.LogException;
import com.ystartor.logging.log4j.Log4jImpl;
import com.ystartor.logging.log4j2.Log4j2Impl;
import com.ystartor.logging.slf4j.Slf4jImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 */
public final class LogFactory {

    public static final String MARKER = "STRATORMYBATIS";

    private static Constructor<? extends Log> logConstructor;

    static {

    }

    private LogFactory(){
        //禁止构造
    }


    /**
     * 获取logger实例
     * @param clazz
     * @return
     */
    public static Log getLog(Class<?> clazz){
        return getLog(clazz.getName());
    }

    /**
     * 工厂类的获取实例方法
     * @param logger
     * @return
     */
    public static Log getLog(String logger){
        try {
            return logConstructor.newInstance(logger);
        } catch (Throwable t) {
            throw new LogException("创建logger失败 " + logger + " 原因：" + t, t);
        }
    }


    //TODO
    public static synchronized void useCustomLogging(Class<? extends Log> clazz){
        setImplemention(clazz);
    }


    public static synchronized void useSlf4jLogging(){
        setImplemention(Slf4jImpl.class);
    }

    public static synchronized void useCommonsLogging(){
        setImplemention(JakartaCommonsLoggingImpl.class);
    }

    public static synchronized void useLog4JLogging(){
        setImplemention(Log4jImpl.class);
    }

    public static synchronized void useLog4J2Logging(){
        setImplemention(Log4j2Impl.class);
    }





    private static void setImplemention(Class<? extends Log> implClass){
        try {
            Constructor<? extends Log> candidate = implClass.getConstructor(String.class);
            Log log = candidate.newInstance(LogFactory.class.getName());
            if (log.isDebugEnabled()){
                log.debug("logging 初始化使用 " + implClass + "适配器");
            }
            logConstructor = candidate;
        }catch (Throwable t){
            throw new LogException("设置错误log实现：Cause:" + t, t);
        }
    }

    private static void tryImplemention(Runnable runnable){
        if (null == logConstructor){
            try {
                runnable.run();
            }catch (Throwable t){
                //ignore
            }
        }
    }


}
