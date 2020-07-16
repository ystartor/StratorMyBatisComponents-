package com.ystartor.type;

import com.ystartor.exception.type.TypeException;
import com.ystartor.io.Resources;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TypeAliasRegistry {

    //类型别名的map
    private final Map<String, Class<?>> typeAliases = new HashMap<>();

    public TypeAliasRegistry(){

    }

    public void registerAlias(String packageName, Class<?> superType){

    }

    //通过注解去获取别名
    public void registerAlias(Class<?> type){
        String alias = type.getSimpleName();
        Alias aliasAnnotation = type.getAnnotation(Alias.class);
        if (null != aliasAnnotation){
            alias = aliasAnnotation.value();
        }
        registerAlias(alias, type);
    }

    /**
     * 注册别名
     * @param alias
     * @param value
     */
    public void registerAlias(String alias, Class<?> value){
        if (null == alias){
            throw new TypeException("参数的别名不能为null");
        }
        //变成小写字母
        String key = alias.toLowerCase(Locale.ENGLISH);
        //是否已经存在改参数别名
        if (typeAliases.containsKey(key) &&
                typeAliases.get(key) != null &&
                !typeAliases.get(key).equals(value)){
            throw new TypeException("这个别名 " + alias + " 已经存在了" + typeAliases.get(key).getName() + ".");
        }
        //添加别名
        typeAliases.put(key, value);
    }

    public void registerAlias(String alias, String value){
        try {
            registerAlias(alias, Resources.classForName(value));
        } catch (Exception e) {
            throw new TypeException("Error registering type alias " + alias + " for " + value + ". Cause: " + e, e);
        }
    }


}
