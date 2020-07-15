package com.ystartor.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @param <T>
 */
public interface TypeHandler<T> {

    void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;




}
