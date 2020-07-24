package com.ystartor.reflection.wrapper;

public interface ObjectWrapperFactory {

    boolean hasWrapperFor(Object object);
    //TODO ------------------------ start
    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);

}
