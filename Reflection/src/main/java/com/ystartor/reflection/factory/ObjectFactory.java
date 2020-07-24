package com.ystartor.reflection.factory;

import java.util.List;
import java.util.Properties;

/**
 *  MyBatis使用ObjectFactory创建所有需要的新Object。
 */
public interface ObjectFactory {

    default void setProperties(Properties properties){
        //NO THING
    }

    <T> T create(Class<T> type);

    <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs);

    <T> boolean isCollection(Class<T> type);
}
