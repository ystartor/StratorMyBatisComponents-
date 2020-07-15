package com.ystartor.cursor.defaults;

import com.ystartor.cursor.Cursor;

import java.io.IOException;

/**
 *
 * @param <T>
 */
public class DefaultCursor<T> implements Cursor<T> {

//    private final DefaultResultSetHandler resultSetHandler;


    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public boolean isConsumed() {
        return false;
    }

    @Override
    public int getCurrentIndex() {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        return null;
    }
}
