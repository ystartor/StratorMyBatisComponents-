package com.ystartor.io;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.ystartor.logging.Log;
import com.ystartor.logging.LogFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class JBoss6VFS extends VFS {
    private static final Log log = LogFactory.getLog(JBoss6VFS.class);

    //TODO function?
    static class VirtualFile{
        static Class<?> VirtualFile;
        static Method getPathNameRelativeTo;
        static Method getChildrenRecursively;

        Object virtualFile;

        VirtualFile(Object virtualFile){ this.virtualFile = virtualFile; }

        /**
         *
         * @param parent
         * @return
         */
        String getPathNameRelativeTo(VirtualFile parent){
            try {
                return invoke(getPathNameRelativeTo, virtualFile, parent.virtualFile);
            } catch (IOException e) {
                log.error("读取VirtualFile.getPathNameRelativeTo()抛出异常");
                return null;
            }
        }

        /**
         *
         * @return
         * @throws IOException
         */
        List<VirtualFile> getChildren() throws IOException {
            List<?> objects = invoke(getChildrenRecursively, virtualFile);
            List<VirtualFile> children = new ArrayList<>(objects.size());
            for (Object object : objects) {
                children.add(new VirtualFile(object));
            }
            return children;
        }

    }

    //TODO function
    static class VFS{
        static Class<?> VFS;
        static Method getChild;

        private VFS(){
            //
        }

        static VirtualFile getChild(URL url) throws IOException {
            Object o = invoke(getChild, VFS, url);
            return null == o? null : new VirtualFile(o);
        }

    }

    //指示此VFS对当前环境是否有效的标志。
    private static Boolean valid;

    protected static synchronized void initialize(){
        if (null == valid){
            //避免直接使用魔法值
            valid = Boolean.TRUE;

            VFS.VFS = checkNotNull(getClass("org.jboss.vfs.VFS"));
            VirtualFile.VirtualFile = checkNotNull(getClass("org.jboss.vfs.VirtualFile"));

            VFS.getChild = checkNotNull(getMethod(VFS.VFS, "getChild", URL.class));
            VirtualFile.getChildrenRecursively = checkNotNull(getMethod(VirtualFile.VirtualFile, "getChildrenRecursively"));

            checkReturnType(VFS.getChild, VirtualFile.VirtualFile);
            checkReturnType(VirtualFile.getChildrenRecursively, List.class);
            checkReturnType(VirtualFile.getPathNameRelativeTo, String.class);


        }
    }

    protected static void setInvalid(){
        if (JBoss6VFS.valid.booleanValue()){
            log.debug("JBoos6 VFS API无法在当前环境使用");
            JBoss6VFS.valid = Boolean.FALSE;
        }
    }

    //TODO 这下面几个检测方法干嘛用的
    protected static void checkReturnType(Method method, Class<?> expected){
        if (null != method && !expected.isAssignableFrom(method.getReturnType())){
            log.error("方法 " + method.getClass().getName() + "." + method.getName() + " (...) 将返回 " + expected.getName() + "但是返回了" + method.getReturnType().getName()+"代替");
            setInvalid();
        }
    }

    protected static <T> T checkNotNull(T object){
        if (null == object){
            setInvalid();
        }
        return object;
    }

    static {
        initialize();
    }

    @Override
    public boolean isValid(){
        return valid;
    }

    @Override
    public List<String> list(URL url, String forPath) throws IOException {
        VirtualFile directory;
        directory = VFS.getChild(url);
        if (null == directory){
            return Collections.emptyList();
        }

        if (!forPath.endsWith("/")){
            forPath += "/";
        }

        List<VirtualFile> children = directory.getChildren();
        List<String> names = new ArrayList<>(children.size());
        for (VirtualFile vf : children) {
            names.add(forPath + vf.getPathNameRelativeTo(directory));
        }
        return names;
    }
}
