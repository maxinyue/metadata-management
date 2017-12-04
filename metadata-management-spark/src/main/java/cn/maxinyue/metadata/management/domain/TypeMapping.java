package cn.maxinyue.metadata.management.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.sql.JDBCType;

/**
 * Created by obama on 2017/4/25.
 */
@XmlType
public class TypeMapping {

    private String sparkType;

    private JDBCType jdbcType;

    private String sqlType;

    @XmlElement
    public String getSparkType() {
        return sparkType;
    }

    public void setSparkType(String sparkType) {
        this.sparkType = sparkType;
    }

    @XmlElement
    public JDBCType getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(JDBCType jdbcType) {
        this.jdbcType = jdbcType;
    }

    @XmlElement
    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    @Override
    public String toString() {
        return "TypeMapping{" +
                "sparkType=" + sparkType +
                ", jdbcType=" + jdbcType +
                ", sqlType='" + sqlType + '\'' +
                '}';
    }
}
