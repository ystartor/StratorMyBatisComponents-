package com.ystartor.reflection;

import com.ystartor.reflection.invoker.AmbiguousMethodInvoker;
import com.ystartor.reflection.invoker.Invoker;
import com.ystartor.reflection.invoker.MethodInvoker;
import com.ystartor.reflection.property.PropertyNamer;
import org.omg.CORBA.ParameterMode;
import sun.plugin2.util.ParameterNames;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ReflectPermission;
import java.text.MessageFormat;
import java.util.*;

/**
 * 对一个class的反射对象
 */
public class Reflector {

    private final Class<?> type;
    private final String[] readableFactoryNames;
    private final String[] writablePropertyNames;
    private final Map<String, Invoker> setMethods = new HashMap<>();
    private final Map<String, Invoker> getMethods = new HashMap<>();
    private final Map<String, Class<?>> setTypes = new HashMap<>();
    private final Map<String, Class<?>> getTypes = new HashMap<>();
    private Constructor<?> defaultConstructor;

    private Map<String, String> caseInsensitivePropertyMap = new HashMap<>();

    public Reflector(Class<?> clazz){
        type = clazz;
        addDefaultConstructor(clazz);
    }





    private void addDefaultConstructor(Class<?> clazz){
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        //TODO @stydy 这里通过jdk8的形式获取构造方法非常值得学习
        Arrays.stream(constructors).filter(constructor -> constructor.getParameterTypes().length == 0)
                .findAny().ifPresent(constructor -> this.defaultConstructor = constructor);
    }

    private void addGetMethods(Class<?> clazz){
        Map<String, List<Method>> conflictingGetters = new HashMap<>();
        Method[] methods = getClassMethods(clazz);
        Arrays.stream(methods).filter(m -> m.getParameterTypes().length == 0 && PropertyNamer.isGetter(m.getName()))
                .forEach(m -> addMethodConflict(conflictingGetters, PropertyNamer.methodToProperty(m.getName()), m));

    }

    private void resolveGetterConflicts(Map<String, List<Method>> conflictingGetters){
        for (Map.Entry<String, List<Method>> entry : conflictingGetters.entrySet()) {
            Method winner = null;
            String propName = entry.getKey();
            boolean isAmbiguous = false;
            for (Method candidate : entry.getValue()) {
                if (null == winner){
                    winner = candidate;
                    continue;
                }
                Class<?> winnerType = winner.getReturnType();
                Class<?> candidateType = candidate.getReturnType();
                if (winnerType.equals(candidateType)){
                    if (!boolean.class.equals(candidateType)){
                        isAmbiguous = true;
                        break;
                    }else if (candidate.getName().startsWith("is")){
                        winner = candidate;
                    }
                }else if (candidateType.isAssignableFrom(winnerType)){
                    //
                }else if (winnerType.isAssignableFrom(candidateType)){
                    winner = candidate;
                }else {
                    isAmbiguous = true;
                    break;
                }

            }

        }
    }

    private void addGetMethod(String name, Method method, boolean isAmbiguous){
        MethodInvoker invoker = isAmbiguous
                ? new AmbiguousMethodInvoker(method, MessageFormat.format(
                "Illegal overloaded getter method with ambiguous type for property ''{0}'' in class ''{1}''. This breaks the JavaBeans specification and can cause unpredictable results.",
                name, method.getDeclaringClass().getName())):new MethodInvoker(method);
        getMethods.put(name, invoker);

    }

    private void addMethodConflict(Map<String, List<Method>> conflictingMethods, String name, Method method){
        if (isValidPropertyName(name)){
            List<Method> list = conflictingMethods.computeIfAbsent(name, k -> new ArrayList<>());
            list.add(method);
        }
    }

    private boolean isValidPropertyName(String name){
        return !(name.startsWith("$") || "serialVersionUID".equals(name)) || "class".equals(name);
    }


    private Method[] getClassMethods(Class<?> clazz){
        Map<String, Method> uniqueMethods = new HashMap<>();
        Class<?> currentClass = clazz;
        while (null != currentClass && currentClass != Object.class){
            addUniqueMethods(uniqueMethods, currentClass.getDeclaredMethods());
            //
            Class<?>[] interfaces = currentClass.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                addUniqueMethods(uniqueMethods, anInterface.getMethods());
            }
            currentClass = currentClass.getSuperclass();
        }
        Collection<Method> methods = uniqueMethods.values();
        return methods.toArray(new Method[0]);
    }

    private void addUniqueMethods(Map<String, Method> uniqueMethods, Method[] methods){
        for (Method currentMethod : methods) {
            if (!currentMethod.isBridge()){
                String signature = getSignature(currentMethod);
                if (!uniqueMethods.containsKey(signature)){
                    uniqueMethods.put(signature, currentMethod);
                }
            }

        }
    }

    private String getSignature(Method method){
        StringBuilder sb = new StringBuilder();
        Class<?> returnType = method.getReturnType();
        if (null != returnType){
            sb.append(returnType.getName()).append('#');
        }
        sb.append(method.getName());
        Class<?>[] parameters = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            sb.append(i == 0 ? ':':',').append(parameters[i].getName());
        }
        return sb.toString();
    }

    public static boolean canControlMemberAccessible(){
        try {
            SecurityManager securityManager = System.getSecurityManager();
            if (null != securityManager){
                securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
            }
        }catch (SecurityException e){
            return false;
        }
        return true;
    }

}
