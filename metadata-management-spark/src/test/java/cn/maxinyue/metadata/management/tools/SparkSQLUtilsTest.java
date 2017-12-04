package cn.maxinyue.metadata.management.tools;

import cn.maxinyue.metadata.management.domain.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by obama on 2017/4/24.
 */
public class SparkSQLUtilsTest {

    @Test
    public void should_get() throws IOException {
        SparkSQLUtils s = new SparkSQLUtils();
        System.out.println(s.getSQLFromSchema(new ObjectMapper().readValue(SparkSQLUtilsTest.class.getClassLoader().getResource("1.json"), Schema.class)));
    }

    @Test
    public void should_get_schema_from_ods_sql() throws IOException {
        SparkSQLUtils s = new SparkSQLUtils();
        String sqls = new String(Files.readAllBytes(new File("/Users/obama/workspace/tuling/sourcecode/spark-dw-etl/src/main/doc/健管系统-ods表.sql").toPath()));
        List<Schema> list = new ArrayList<>();
        Arrays.asList(sqls.split(";")).forEach(sql -> {
            Schema schema = s.getSchemaFromSql(sql, "jgxt_dw");
            list.add(schema);
        });
        System.out.println(new ObjectMapper().writeValueAsString(list));
    }

    @Test
    public void should_get_schema_from_dimension_sql() throws IOException {
        SparkSQLUtils s = new SparkSQLUtils();
        String sqls = new String(Files.readAllBytes(new File("/Users/obama/workspace/tuling/sourcecode/spark-dw-etl/src/main/doc/健管系统-维度表.sql").toPath()));
        List<Schema> list = new ArrayList<>();
        Arrays.asList(sqls.split(";")).forEach(sql -> {
            Schema schema = s.getSchemaFromSql(sql, "jgxt_dw");
            list.add(schema);
        });
        System.out.println(new ObjectMapper().writeValueAsString(list));
    }

    @Test
    public void should_get_schema_from_result_sql() throws IOException {
        SparkSQLUtils s = new SparkSQLUtils();
        String sqls = new String(Files.readAllBytes(new File("/Users/obama/workspace/tuling/sourcecode/spark-dw-etl/src/main/doc/健管系统-事实表.sql").toPath()));
        List<Schema> list = new ArrayList<>();
        Arrays.asList(sqls.split(";")).forEach(sql -> {
            Schema schema = s.getSchemaFromSql(sql, "jgxt_dw");
            list.add(schema);
        });
        System.out.println(new ObjectMapper().writeValueAsString(list));
    }

    @Test
    public void should_get_sql() throws IOException {
        SparkSQLUtils s = new SparkSQLUtils();
        String sqls = new String(Files.readAllBytes(new File("/Users/obama/workspace/tuling/sourcecode/metadata-management/metadata-management-spark/src/test/resources/3.sql").toPath()));
        List<Schema> list = new ArrayList<>();
        Arrays.asList(sqls.split(";")).forEach(sql -> {
            Schema schema = s.getSchemaFromSql(sql, "jgxt_dw");
            list.add(schema);
        });
        System.out.println(new ObjectMapper().writeValueAsString(list));
    }
}
