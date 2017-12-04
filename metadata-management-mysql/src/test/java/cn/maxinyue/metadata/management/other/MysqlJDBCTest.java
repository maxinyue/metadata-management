package cn.maxinyue.metadata.management.other;

import cn.maxinyue.core.config.Configuration;
import cn.maxinyue.core.config.DatabaseConfiguration;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * Created by obama on 2017/4/24.
 */
public class MysqlJDBCTest {

    @Test
    public void shoule_get() throws Exception {
        DatabaseConfiguration db = Configuration.getDefaultConfiguration().getDatabases().get("jgxt_base");
        Class.forName(db.getDriver());
        Connection c = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPassword());
        DatabaseMetaData metaData = c.getMetaData();
        ResultSet rs = metaData.getTables(c.getCatalog(), null, null, null);
        while (rs.next()) {
            int size = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= size; i++) {
//                System.out.println("name:" + rs.getMetaData().getColumnName(i) + ",value:" + rs.getObject(i));
            }
            ResultSet crs = metaData.getColumns(c.getCatalog(), null, rs.getString("TABLE_NAME"), null);
            while (crs.next()) {
                for (int j = 1; j <= size; j++) {
                    System.out.println("name:" + crs.getMetaData().getColumnName(j) + ",value:" + crs.getObject(j));
                }
            }
        }

    }

    @Test
    public void shoule_get_schema() throws Exception {

        DatabaseConfiguration db = Configuration.getDefaultConfiguration().getDatabases().get("jgxt_base");
        Class.forName(db.getDriver());
        Connection c = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPassword());
        DatabaseMetaData metaData = c.getMetaData();
        ResultSet rs = metaData.getSchemas(c.getCatalog(), null);
        while (rs.next()) {
            int size = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= size; i++) {
//                System.out.println("name:" + rs.getMetaData().getColumnName(i) + ",value:" + rs.getObject(i));
            }
            ResultSet crs = metaData.getColumns(c.getCatalog(), null, rs.getString("TABLE_NAME"), null);
            while (crs.next()) {
                for (int j = 1; j <= size; j++) {
                    System.out.println("name:" + crs.getMetaData().getColumnName(j) + ",value:" + crs.getObject(j));
                }
            }
        }

    }
}
