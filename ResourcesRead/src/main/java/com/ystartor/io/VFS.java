package com.ystartor.io;

import com.ystartor.logging.Log;
import com.ystartor.logging.LogFactory;

import java.io.IOError;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 提供一个非常简单的API来访问应用服务器中的资源。
 */
public abstract class VFS {
    private static final Log log = LogFactory.getLog(VFS.class);


    public static final Class<?>[] IMPLEMENTIONS = {JBoss6VFS.class, DefaultVFS.class};
    /**
     * 由{@link #addImplClass(Class)} 进行加载
     */
    public static final List<Class<? extends VFS>> USER_IMPLEMENTIONS = new ArrayList<>();

    //TODO 静态内部类的功能
    public static class VFSHolder{
        static final VFS INSTANCE = createVFS();

        static VFS createVFS(){
            List<Class<? extends VFS>> impls = new ArrayList<>();
            impls.addAll(USER_IMPLEMENTIONS);
            impls.addAll(Arrays.asList((Class<? extends VFS>[]) IMPLEMENTIONS));

            VFS vfs = null;
            for (int i = 0; vfs == null || !vfs.isValid(); i++){
                Class<? extends VFS> impl = impls.get(i);
                try {
                    vfs = impl.getDeclaredConstructor().newInstance();
                    if (!vfs.isValid() && log.isDebugEnabled()){
                        log.debug("VFS implementation " + impl.getName()
                                + " is not valid in this environment.");
                    }
                }catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    log.error("Failed to instantiate " + impl, e);
                    return null;
                }
            }

            if (log.isDebugEnabled()){
                log.debug("Using VFS adapter " + vfs.getClass().getName());
            }
            return vfs;
        }


    }

    public static VFS getInstance(){
        return VFSHolder.INSTANCE;
    }

    public static void addImplClass(Class<? extends VFS> clazz){
        if (null != clazz){
            USER_IMPLEMENTIONS.add(clazz);
        }
    }

    /**
     *
     * @param className
     * @return
     */
    protected static Class<?> getClass(String className){
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            if (log.isDebugEnabled()){
                log.debug("Class没有找到 " + className);
            }
            return null;
        }
    }

    /**
     *
     * @param clazz
     * @param methodName
     * @param parameterType
     * @return
     */
    protected static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterType){
        if (null == clazz){
            return null;
        }
        try {
            return clazz.getMethod(methodName, parameterType);
        } catch (SecurityException e) {
            log.error("Security exception looking for method " + clazz.getName() + "." + methodName + ".  Cause: " + e);
            return null;
        } catch (NoSuchMethodException e) {
            log.error("Method not found " + clazz.getName() + "." + methodName + "." + methodName + ".  Cause: " + e);
            return null;
        }

    }

    protected static List<URL> getResources(String path) throws IOException {
        return Collections.list(Thread.currentThread().getContextClassLoader().getResources(path));
    }


    public abstract boolean isValid();

    public abstract List<String> list(URL url, String forPath) throws IOException;

    protected static <T> T invoke(Method method, Object object, Object ...parameters) throws IOException,RuntimeException {
        try {
            return (T)method.invoke(object, parameters);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof IOException){
                throw (IOException) e.getTargetException();
            }else {
                throw new RuntimeException(e);
            }
        }
    }


}
