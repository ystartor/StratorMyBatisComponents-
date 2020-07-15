package com.ystartor.cursor;

import javax.swing.text.html.HTMLDocument;
import java.io.Closeable;
import java.util.Iterator;

/**
 *  游标继承使用迭代器惰性地处理抓取项。
 *  游标非常适合处理数百万项查询，而这些查询通常无法装入内存。
 *  如果在resultMaps中使用集合，则必须对游标SQL查询进行排序(resultOrdered="true")
 *  使用resultMap的id列。
 * @param <T>
 */
public interface Cursor<T> extends Closeable, Iterator<T> {

    boolean isOpen();

    boolean isConsumed();

    int getCurrentIndex();

}