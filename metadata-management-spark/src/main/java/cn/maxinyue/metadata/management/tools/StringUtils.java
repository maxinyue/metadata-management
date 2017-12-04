package cn.maxinyue.metadata.management.tools;

/**
 * Created by obama on 2017/4/25.
 */
public class StringUtils {

    public static String getColumns(String sql) {
        return sql.substring(sql.indexOf("(") + 1, sql.indexOf(")"));
    }
}
