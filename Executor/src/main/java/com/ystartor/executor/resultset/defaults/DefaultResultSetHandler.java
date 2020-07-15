package com.ystartor.executor.resultset.defaults;

import com.ystartor.cursor.Cursor;
import com.ystartor.executor.Executor;
import com.ystartor.executor.resultset.ResultSetHandler;

import javax.security.auth.login.Configuration;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 */
public class DefaultResultSetHandler implements ResultSetHandler {

    private static final Object DEFERRED = new Object();

//    private final Executor executor;
//    private final Configuration configuration;








    @Override
    public <E> List<E> handleResultSets(Statement stmt) throws SQLException {
        return null;
    }

    @Override
    public <E> Cursor<E> handleCursorResultSets(Statement stmt) throws SQLException {
        return null;
    }

    @Override
    public void handleOutputParameters(CallableStatement cs) throws SQLException {

    }
}
