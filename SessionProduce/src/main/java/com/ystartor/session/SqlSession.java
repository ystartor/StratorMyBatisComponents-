package com.ystartor.session;

import com.ystartor.session.parame.RowBounds;

import java.awt.*;
import java.io.Closeable;
import java.util.List;
import java.util.Map;

public interface SqlSession extends Closeable {

    //
    <T> T selectOne(String statement);

    <T> T selectOne(String statement, Object parameter);

    <E> List<E> selectList(String statement);

    <E> List<E> selectList(String statement, Object parameter);

    <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds);

    <K, V> Map<K, V> selectMap(String statement, String mapKey);

    <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey);

    <K, V> Map<K, V> selectMap(String statement, Object paramenter, String mapKey, RowBounds rowBounds);

    



}
