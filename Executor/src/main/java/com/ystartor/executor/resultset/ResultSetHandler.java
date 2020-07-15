package com.ystartor.executor.resultset;

import com.ystartor.cursor.Cursor;

import javax.swing.plaf.nimbus.State;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 结果集处理
 */
public interface ResultSetHandler {

    <E> List<E> handleResultSets(Statement stmt) throws SQLException;

    <E> Cursor<E> handleCursorResultSets(Statement stmt) throws SQLException;

    void handleOutputParameters(CallableStatement cs) throws SQLException;

}
