package cn.maxinyue.metadata.management.tools;

import cn.maxinyue.metadata.management.domain.TypeMapping;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.sql.types.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.JDBCType;
import java.util.List;

/**
 * Created by obama on 2017/4/25.
 */
public class DataTypeConverter {

    static List<TypeMapping> tms;

    static ObjectMapper objectMapper;

    static Logger logger = LoggerFactory.getLogger(DataTypeConverter.class.getSimpleName());

    static {
        objectMapper = new ObjectMapper();
        try {
            tms = objectMapper.readValue(DataTypeConverter.class.getClassLoader().getResourceAsStream("spark.datatype.json"), new TypeReference<List<TypeMapping>>() {
            });
        } catch (IOException e) {
            logger.error("init error", e);
        }
    }

    public static DataType toSpark(JDBCType jdbcType) {
        String type = tms.stream().filter(t -> t.getJdbcType().equals(jdbcType)).findFirst().get().getSqlType();
        DataType dataType = null;
        try {
            dataType = DataType.fromJson("\"" + type.toLowerCase() + "\"");
        } catch (Throwable e) {
            logger.error("toSpark:" + jdbcType + " error!", e);
        }
        return dataType;
    }

    public static String toSparkSqlType(JDBCType jdbcType) {
        String s = "";
        try {
            s = tms.stream().filter(t -> t.getJdbcType().equals(jdbcType)).findFirst().get().getSqlType();
        } catch (Throwable e) {
            logger.debug("toSparkSqlType:" + jdbcType + " error!", e);
        }
        return s;
    }

    public static JDBCType toJDBCType(String type) {
        JDBCType jdbcType = null;
        try {
            jdbcType = tms.stream().filter(t -> t.getSqlType().equalsIgnoreCase(type)).findFirst().get().getJdbcType();
        } catch (Throwable e) {
            logger.debug("toJDBCType:" + type + " error!", e);
        }
        return jdbcType;
    }

    public static JDBCType toJDBCType(DataType type) {
        return toJDBCType(type.typeName());
    }

}
