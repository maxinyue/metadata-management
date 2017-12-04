package cn.maxinyue.metadata.management.tools;

import cn.maxinyue.metadata.management.domain.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static cn.maxinyue.core.config.Configuration.getDefaultConfiguration;

/**
 * Created by obama on 2017/4/24.
 */
public class MySQLUtilsTest {

    @Test
    public void should_get_sql() throws IOException {
        MySQLUtils mySQLUtils = new MySQLUtils();
        Schema s = new ObjectMapper().readValue(MySQLUtilsTest.class.getClassLoader().getResource("schema1.json"), Schema.class);
        System.out.println(mySQLUtils.getSQLFromSchema(s));
    }

    @Test
    public void should_get_sql3() throws IOException {
        MySQLUtils mySQLUtils = new MySQLUtils();
        Schema s = new ObjectMapper().readValue(MySQLUtilsTest.class.getClassLoader().getResource("schema3.json"), Schema.class);
        System.out.println(mySQLUtils.getSQLFromSchema(s));
    }

    @Test
    public void should_getSchemas() throws IOException {
        MySQLUtils mySQLUtils = new MySQLUtils();
        List<Schema> schemas = mySQLUtils.getSchemaFromDBConnection(getDefaultConfiguration().getDatabases().get("jgxt_base"));
        System.out.println(new ObjectMapper().writeValueAsString(schemas));
    }
}