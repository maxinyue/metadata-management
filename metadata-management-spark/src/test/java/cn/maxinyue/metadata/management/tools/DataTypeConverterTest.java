package cn.maxinyue.metadata.management.tools;

import org.apache.spark.sql.types.DataType;
import org.hamcrest.core.Is;
import org.junit.Test;

import static java.sql.JDBCType.*;
import static org.apache.spark.sql.types.DataTypes.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by obama on 2017/4/25.
 */
public class DataTypeConverterTest {


    @Test
    public void get() {
        System.out.println(DataTypeConverter.tms);
    }

    @Test
    public void should_getLong() {
        assertThat(DataTypeConverter.toSpark(BIGINT), Is.is(LongType));

    }

    @Test
    public void should_get_sql_Long() {
        assertThat(DataTypeConverter.toSparkSqlType(BIGINT), Is.is("Long"));
    }

    @Test
    public void should_get_jdbc_type() {
        assertThat(DataTypeConverter.toJDBCType("string"), Is.is(VARCHAR));
    }

    @Test
    public void should_to_spark() {

//        System.out.println(StringType.getClass());
        assertThat(DataTypeConverter.toSpark(VARCHAR), Is.is(StringType));
    }

    @Test
    public void should_to_spark_1() {

//        System.out.println(StringType.getClass());
        assertThat(DataTypeConverter.toSpark(INTEGER), Is.is(IntegerType));
    }

    @Test
    public void should_to_spark_2() {

        assertThat(DataTypeConverter.toSpark(SMALLINT), Is.is(ShortType));
        assertThat(DataTypeConverter.toSpark(TINYINT), Is.is(ByteType));
    }

    @Test
    public void should_to_spark_3() {

//        System.out.println(DataType.fromJson("\"Short\""));
//        System.out.println(DataType.fromJson("\"smallint\""));
//        System.out.println(DataType.fromJson("\"short\""));
        System.out.println(DataType.fromJson("\"byte\""));
    }

    @Test
    public void should_to_jdbc_1() {

//        System.out.println(StringType.getClass());
        assertThat(DataTypeConverter.toJDBCType(IntegerType), Is.is(INTEGER));
        assertThat(DataTypeConverter.toJDBCType(ShortType), Is.is(SMALLINT));
        assertThat(DataTypeConverter.toJDBCType(ByteType), Is.is(TINYINT));
    }
}
