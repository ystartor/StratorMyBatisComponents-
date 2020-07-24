package com.ystartor.reflection.property;

import java.util.Iterator;

/**
 * TODO 这个类干啥的？
 */
public class PropertyTokenizer implements Iterator<PropertyTokenizer> {

    private String name;
    private final String indexedName;
    private String index;
    private final String children;

    //TODO ------------------------ step3
    //初始化
    public PropertyTokenizer(String fullname){
        // 1. 判断fullname中是否有 "."，返回第一次出现"."的索引
        int delim = fullname.indexOf('.');
        // 2. 如果有
        if (delim > -1) {
            //返回"."之前的部分
            name = fullname.substring(0, delim);
            //返回"."之后的部分
            children = fullname.substring(delim + 1);
        } else {
            //整个property
            name = fullname;
            //为null
            children = null;
        }
        // indexName为"."之前或者整个字段
        indexedName = name;
        // 返回name中第一次出现“[”的索引
        delim = name.indexOf('[');
        //存在"["
        if (delim > -1) {
            //
            index = name.substring(delim + 1, name.length() - 1);
            name = name.substring(0, delim);
        }
    }


}
