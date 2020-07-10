package com.ystartor.io;

import java.io.InputStream;

/**
 *
 */
public class ClassLoaderWrapper {

    ClassLoader defaultClassLoader;
    ClassLoader systemClassLoader;

    ClassLoaderWrapper(){
        try {
            //这里可能会产生没有权限的问题，需要抛出异常
            systemClassLoader = ClassLoader.getSystemClassLoader();
        }catch (SecurityException ignored){
            //允许不管理的异常
        }
    }

    InputStream getResourceAsStream(String resource, ClassLoader[] classLoader){
        for (ClassLoader cl : classLoader) {
            if (null != cl){
                InputStream returnValue = cl.getResourceAsStream(resource);
            }
        }
        return null;
    }


    /**
     *
     * @param classLoader 传入的classLoader
     * @return  返回的是一个数组，带顺序
     *     1. 参数
     *     2. 属性参数，defaultClassLoader
     *     3. 通过当前线程来获取classLoader
     *     4. 当前class对象的类装载器
     *     5. 属性参数，systemClassLoader
     *
     */
    ClassLoader[] getClassLoaders(ClassLoader classLoader){
        return new ClassLoader[]{
                classLoader,
                defaultClassLoader,
                Thread.currentThread().getContextClassLoader(),
                getClass().getClassLoader(),
                systemClassLoader
        };
    }




}
