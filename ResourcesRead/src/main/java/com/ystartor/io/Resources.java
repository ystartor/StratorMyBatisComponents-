package com.ystartor.io;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * 简化通过类加载器访问资源的类。
 */
public class Resources {

    private static ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();

    private static Charset charset;

    Resources(){
    }

    /**
     * 获取包装类里面的类加载器
     * @return
     */
    public static ClassLoader getDefaultClassLoader(){
        return classLoaderWrapper.defaultClassLoader;
    }

    /**
     * 设置默认的类加载器
     * @param defaultClassLoader
     */
    public static void setDefaultClassLoader(ClassLoader defaultClassLoader){
        classLoaderWrapper.defaultClassLoader = defaultClassLoader;
    }

    /**
     * 获取URL
     * @param resource
     * @return
     * @throws IOException
     */
    public static URL getResourceURL(String resource) throws IOException {
        //调用重载方法
        return getResourceURL(null, resource);
    }

    /**
     * 获取URL
     * @param classLoader
     * @param resource
     * @return
     * @throws IOException
     */
    public static URL getResourceURL(ClassLoader classLoader, String resource) throws IOException {
        //获取url
        URL url = classLoaderWrapper.getResourceAsURL(resource, classLoader);
        if (null == url){
            // 抛出异常
            throw new IOException("找不到这个resource文件" + resource);
        }
        return null;
    }

    /**
     * 获取inputstream
     * @param resource
     * @return
     * @throws IOException
     */
    public static InputStream getResourceAsStream(String resource) throws IOException {
        return getResourceAsStream(null, resource);
    }

    /**
     * 获取InputStream
     * @param classLoader
     * @param resource
     * @return
     * @throws IOException
     */
    public static InputStream getResourceAsStream(ClassLoader classLoader, String resource) throws IOException {
        InputStream in = classLoaderWrapper.getResourceAsStream(resource, classLoader);
        if (null == in){
            throw new IOException("找不到这个resource文件" + resource);
        }
        return in;
    }

    /**
     * 获取到resouce对应文件的流并将其转换成properties对象
     * @param resource
     * @return
     * @throws IOException
     */
    public static Properties getResourceAsProperties(String resource) throws IOException {
        Properties props = new Properties();
        // try-with-resource
        try(InputStream in = getResourceAsStream(resource)){
            props.load(in);
        }
        return props;
    }

    /**
     * 获取到resouce对应文件的流并将其转换成properties对象
     * @param resource
     * @return
     * @throws IOException
     */
    public static Properties getResourceAsProperties(ClassLoader classLoader, String resource) throws IOException {
        Properties props = new Properties();
        // try-with-resource
        try(InputStream in = getResourceAsStream(classLoader, resource)){
            props.load(in);
        }
        return props;
    }

    /**
     * 根据编码将获取到的字节流转换成字符流
     * @param resource
     * @return
     * @throws IOException
     */
    public static Reader getResourceAsReader(String resource) throws IOException {
        Reader reader;
        if (null == charset){
            reader = new InputStreamReader(getResourceAsStream(resource));
        }else {
            reader = new InputStreamReader(getResourceAsStream(resource), charset);
        }
        return reader;
    }

    /**
     * 根据编码将获取到的字节流转换成字符流
     * @param resource
     * @return
     * @throws IOException
     */
    public static Reader getResourceAsReader(ClassLoader classLoader, String resource) throws IOException {
        Reader reader;
        if (null == charset){
            reader = new InputStreamReader(getResourceAsStream(classLoader, resource));
        }else {
            reader = new InputStreamReader(getResourceAsStream(classLoader, resource), charset);
        }
        return reader;
    }

    /**
     * 将获取到的URL转换成文件
     * @param resource
     * @return
     * @throws IOException
     */
    public static File getResourceAsFile(String resource) throws IOException {
        return new File(getResourceURL(resource).getFile());
    }

    /**
     * 将获取到的URL转换成文件
     * @param resource
     * @return
     * @throws IOException
     */
    public static File getResourceAsFile(ClassLoader classLoader, String resource) throws IOException {
        return new File(getResourceURL(classLoader, resource).getFile());
    }

    /**
     * 根据url字符串获取文件并转换成流
     * @param urlString
     * @return
     * @throws IOException
     */
    public static InputStream getUrlAsStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return conn.getInputStream();
    }

    /**
     * 根据url字符串获取文件并装换成字符流
     * @param urlString
     * @return
     * @throws IOException
     */
    public static Reader getUrlAsReader(String urlString) throws IOException {
        Reader reader;
        if (null == charset){
            reader = new InputStreamReader(getUrlAsStream(urlString));
        }else{
            reader = new InputStreamReader(getUrlAsStream(urlString), charset);
        }
        return reader;
    }

    /**
     * 将远程文件转换成properties
     * @param urlString
     * @return
     * @throws IOException
     */
    public static Properties getUrlAsProperties(String urlString) throws IOException {
        Properties props = new Properties();
        try(InputStream in = getUrlAsStream(urlString)){
            props.load(in);
        }
        return props;
    }

    /**
     * 加载一个文件的class
     * @param className
     * @return
     */
    public static Class<?> classForName(String className){
        return classLoaderWrapper.classForName(className);
    }

    public static Charset getCharset() {
        return charset;
    }

    public static void setCharset(Charset charset) {
        Resources.charset = charset;
    }
}
