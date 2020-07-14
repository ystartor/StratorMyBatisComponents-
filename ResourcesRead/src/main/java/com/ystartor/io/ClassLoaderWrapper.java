package com.ystartor.io;

import java.io.InputStream;
import java.net.URL;

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

    /**
     * 不指定类加载器获取class
     * @param name
     * @return
     */
    Class<?> classForName(String name){
        return classForName(name, getClassLoaders(null));
    }

    /**
     * 根据类加载器获取class
     * @param name
     * @param classLoader
     * @return
     */
    Class<?> classForName(String name, ClassLoader classLoader){
        return classForName(name, getClassLoaders(classLoader));
    }

    /**
     * 使用一组类加载器获取类
     * @param name
     * @param classLoader
     * @return
     */
    Class<?> classForName(String name, ClassLoader[] classLoader){
        //遍历
        for (ClassLoader cl : classLoader) {
            if (null != cl){
                try {
                    //获取class信息
                    Class<?> c = Class.forName(name, true, cl);
                    if (null != c){
                        return c;
                    }
                } catch (ClassNotFoundException e) {
                    //允许不被处理
                }
            }
        }
        return null;
    }

    /**
     * 调用重载方法
     * @param resource
     * @return
     */
    URL getResourceAsURL(String resource){
        //
        return getResourceAsURL(resource, getClassLoaders(null));
    }

    /**
     *
     * 获取对应的URL
     * @param resource
     * @param classLoader
     * @return
     */
    URL getResourceAsURL(String resource, ClassLoader classLoader){
        //获取URL
        return getResourceAsURL(resource, getClassLoaders(classLoader));
    }

    /**
     * 根据resource获取流
     * @param resource 文件地址
     * @param classLoader 类加载器
     * @return
     */
    InputStream getResourceAsStream(String resource, ClassLoader classLoader){
        // 调用重载方法获取流
        return getResourceAsStream(resource, getClassLoaders(classLoader));
    }

    /**
     * 根据resource获取流
     * @param resource 文件地址
     * @return
     */
    InputStream getResourceAsStream(String resource){
        //调用重载方法
        return getResourceAsStream(resource, getClassLoaders(null));
    }

    /**
     *
     * 根据resource获取流
     * @param resource 文件地址
     * @param classLoader 类加载器数组，后面实际根据类加载器来加载文件
     * @return
     */
    InputStream getResourceAsStream(String resource, ClassLoader[] classLoader){
        //遍历类加载器
        for (ClassLoader cl : classLoader) {
            //为空则不进行处理
            if (null != cl){
                //直接根据文件路径获取流
                InputStream returnValue = cl.getResourceAsStream(resource);
                //
                if (null == returnValue){
                    // 有的classloader需要加上一个"/"来进行处理
                    returnValue = cl.getResourceAsStream("/" + resource);
                }
                if (null != returnValue){
                    return returnValue;
                }
            }
        }
        return null;
    }


    /**
     * 根据resource获取URL，这块代码和上面的inputStream是一样的
     * @param resource
     * @param classLoader
     * @return
     */
    URL getResourceAsURL(String resource, ClassLoader[] classLoader){
        URL url;
        for (ClassLoader cl : classLoader) {
            if (null != cl){
                // 直接获取url
                url = cl.getResource(resource);
                //
                if (null != url){
                    //有的classloader需要“/”
                    url = cl.getResource("/" + resource);
                }
                if (null != url){
                    return url;
                }
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
