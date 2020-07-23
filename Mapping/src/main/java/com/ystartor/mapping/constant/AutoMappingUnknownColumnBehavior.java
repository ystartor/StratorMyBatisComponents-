package com.ystartor.mapping.constant;

import com.ystartor.logging.Log;
import com.ystartor.logging.LogFactory;
import com.ystartor.mapping.MappedStatement;
import com.ystartor.session.exception.SqlSessionException;

public enum  AutoMappingUnknownColumnBehavior {

    NONE {
        @Override
        public void doAction(MappedStatement mappedStatement, String columnName, String propertyName, Class<?> propertyType) {
            //doNotion
        }
    },
    WARNING {
        @Override
        public void doAction(MappedStatement mappedStatement, String columnName, String propertyName, Class<?> propertyType) {
            LogHolder.log.warn(buildMessage(mappedStatement, columnName, propertyName, propertyType));
        }
    },
    FAILING {
        @Override
        public void doAction(MappedStatement mappedStatement, String columnName, String propertyName, Class<?> propertyType) {
            throw new SqlSessionException(buildMessage(mappedStatement, columnName, propertyName, propertyType));
        }
    }
    ;

    public abstract void doAction(MappedStatement mappedStatement, String columnName, String propertyName, Class<?> propertyType);

    private static String buildMessage(MappedStatement mappedStatement, String columnName, String property, Class<?> propertyType){
        return new StringBuilder("Unknown column is detected on '")
                .append(mappedStatement.getId())
                .append("' auto-mapping. Mapping parameters are ")
                .append("[")
                .append("columnName=").append(columnName)
                .append(",").append("propertyName=").append(property)
                .append(",").append("propertyType=").append(propertyType != null ? propertyType.getName() : null)
                .append("]")
                .toString();
    }

    private static class LogHolder {
        private static final Log log = LogFactory.getLog(AutoMappingUnknownColumnBehavior.class);
    }

}
