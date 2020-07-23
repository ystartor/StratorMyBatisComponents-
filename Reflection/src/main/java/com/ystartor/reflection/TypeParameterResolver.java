package com.ystartor.reflection;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * TODO 这个类的功能
 */
public class TypeParameterResolver {

   public static Type resolveFieldType(Field field, Type srcType) {
       Type fieldType = field.getGenericType(); // 返回一个 Type 对象，它表示此 Field 对象所表示字段的声明类型。
       Class<?> declaringClass = field.getDeclaringClass();//返回表示类或接口的 Class 对象，该类或接口声明由此 Field 对象表示的字段。
       return resolveType(fieldType, srcType, declaringClass);
   }

    /**
     *
     * @param type 字段类型
     * @param srcType TODO 不清楚
     * @param declaringClass field对象字段类型
     * @return
     */
   private static Type resolveType(Type type, Type srcType, Class<?> declaringClass){
       if (type instanceof TypeVariable){
           return resolveTypeVar((TypeVariable<?>)type, srcType, declaringClass);
       } else if (type instanceof ParameterizedType){
           return resolve
       }
   }


   private static ParameterizedType resolveParameterizedType(ParameterizedType parameterizedType, Type srcType, Class<?> declaringClass){
       Class<?> rawType = (Class<?>) parameterizedType.getRawType();
       Type[] typeArgs = parameterizedType.getActualTypeArguments();
       Type[] args = new Type[typeArgs.length];
       for (int i = 0; i < typeArgs.length; i++) {
           if (typeArgs[i] instanceof TypeVariable){
               args[i] = resolveTypeVar((TypeVariable<?>) typeArgs[i], srcType, declaringClass);
           } else if (typeArgs[i] instanceof ParameterizedType) {
               args[i] = resolveParameterizedType((ParameterizedType) typeArgs[i], srcType, declaringClass);
           } else if (typeArgs[i] instanceof ParameterizedType) {

           }
       }

   }


    /**
     *
     * @param wildcardType WildcardType 表示一个通配符类型表达式，如 ?、? extends Number 或 ? super Integer
     * @param srcType
     * @param declaringClass
     * @return
     */
   private  static Type resolveWildcardType(WildcardType wildcardType, Type srcType, Class<?> declaringClass){
       
   }

   private static Type[] resolveWildcardTypeBounds(Type[] bounds, Type srcType, Class<?> declaringClass){
       Type[] result = new Type[bounds.length];


   }

    /**
     *
     * @param typeVar
     * @param srcType
     * @param declaringClass
     * @return
     */
    private static Type resolveTypeVar(TypeVariable<?> typeVar, Type srcType, Class<?> declaringClass) {
        Type result;
        Class<?> clazz;
        if (srcType instanceof Class){
            clazz = (Class<?>) srcType;
        } else if (srcType instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) srcType;
            clazz = (Class<?>) parameterizedType.getRawType();
        }else {
            throw new IllegalArgumentException("The 2nd arg must be Class or ParameterizedType, but was: " + srcType.getClass());
        }

        if (clazz == declaringClass){
            Type[] bounds = typeVar.getBounds();
            if (bounds.length > 0){
                return bounds[0];
            }
            return Object.class;
        }

        Type superclass = clazz.getGenericSuperclass();
        result = scanSuperTypes(typeVar, srcType, declaringClass, clazz, superclass);
        if (result != null){
            return result;
        }

        Type[] superInterfaces = clazz.getGenericInterfaces();
        for (Type superInterface : superInterfaces) {
            result = scanSuperTypes(typeVar, srcType, declaringClass, clazz, superclass);
            if (null != result){
                return result;
            }
        }
        return Object.class;
    }

    /**
     *
     * @param typeVar
     * @param srcType
     * @param declaringClass
     * @param clazz
     * @param superclass
     * @return
     */
    private static Type scanSuperTypes(TypeVariable<?> typeVar, Type srcType, Class<?> declaringClass, Class<?> clazz, Type superclass){
        if (superclass instanceof ParameterizedType){
            ParameterizedType parentAsType = (ParameterizedType) superclass;
            Class<?> parentAsClass = (Class<?>) parentAsType.getRawType();
            TypeVariable<? extends Class<?>>[] parentTypeVars = parentAsClass.getTypeParameters();
            if (srcType instanceof ParameterizedType){
                parentAsType = translateParentTypeVars((ParameterizedType) srcType, clazz, parentAsType);
            }
            if (declaringClass == parentAsClass){
                for (int i = 0; i < parentTypeVars.length; i++) {
                    if (typeVar.equals(parentTypeVars[i])){
                        return parentAsType.getActualTypeArguments()[i];
                    }
                }
            }
            if (declaringClass.isAssignableFrom(parentAsClass)){
                return resolveTypeVar(typeVar, superclass, declaringClass);
            }
        } else if (superclass instanceof Class && declaringClass.isAssignableFrom((Class<?>) superclass)){
            return resolveTypeVar(typeVar, superclass, declaringClass);
        }
        return null;
    }

    private static ParameterizedType translateParentTypeVars(ParameterizedType srcType, Class<?> srcClass, ParameterizedType parentType){
        Type[] parentTypeArgs = parentType.getActualTypeArguments();
        Type[] srcTypeArgs = srcType.getActualTypeArguments();
        TypeVariable<?>[] srcTypeVars = srcClass.getTypeParameters();
        Type[] newParentArgs = new Type[parentTypeArgs.length];
        boolean noChange = true;
        for (int i = 0; i < parentTypeArgs.length; i++) {
            if (parentTypeArgs[i] instanceof TypeVariable){
                for (int j = 0; j < srcTypeVars.length; j++) {
                    if (srcTypeVars[j].equals(parentTypeArgs[i])){
                        noChange = false;
                        newParentArgs[i] = srcTypeArgs[j];
                    }
                }
            } else {
              newParentArgs[i] = parentTypeArgs[i];
            }
        }
        return noChange ? parentType : new ParameterizedTypeImpl((Class<?>) parentType.getRawType(), null, newParentArgs);
    }

    static class ParameterizedTypeImpl implements ParameterizedType{
        private Class<?> rawType;
        private Type ownerType;
        private Type[] actualTypeArguments;

        public ParameterizedTypeImpl(Class<?> rawType, Type ownerType, Type[] actualTypeArguments){
            super();

            this.rawType = rawType;
            this.ownerType = ownerType;
            this.actualTypeArguments = actualTypeArguments;
        }


        @Override
        public Type[] getActualTypeArguments() {
            return actualTypeArguments;
        }

        @Override
        public Type getRawType() {
            return rawType;
        }

        @Override
        public Type getOwnerType() {
            return ownerType;
        }

        @Override
        public String toString() {
            return "ParameterizedTypeImpl{" +
                    "rawType=" + rawType +
                    ", ownerType=" + ownerType +
                    ", actualTypeArguments=" + Arrays.toString(actualTypeArguments) +
                    '}';
        }
    }


}
