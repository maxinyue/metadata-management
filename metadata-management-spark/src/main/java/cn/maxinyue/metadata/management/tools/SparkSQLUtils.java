package cn.maxinyue.metadata.management.tools;

import cn.maxinyue.core.config.DatabaseConfiguration;
import cn.maxinyue.metadata.management.domain.Element;
import cn.maxinyue.metadata.management.domain.Schema;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.scala.DefaultScalaModule;
import com.google.common.base.Strings;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.collection.Seq;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by obama on 2017/4/24.
 */
public class SparkSQLUtils implements SQLUtils {

    public static final String FILE_FORMAT = "FILE_FORMAT";
    public static final String PARTITION_BY = "PARTITION_BY";
    public static final String COMMENT = "COMMENT";
    static ObjectMapper objectMapper;
    static Logger logger = LoggerFactory.getLogger(SparkSQLUtils.class.getSimpleName());

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new DefaultScalaModule());
    }

    @Override
    public String getSQLFromSchema(Schema schema) {
        StringBuilder sql = new StringBuilder("create table " + schema.getDomain() + "." + schema.getName() + " (").append("\n");
        schema.getElements().forEach(element -> sql.append(element.getName() + " " + DataTypeConverter.toSparkSqlType(element.getType()) + " " + ((Strings.isNullOrEmpty(element.getComment()) ? "" : (" COMMENT '" + element.getComment()) + "'")) + ",").append("\n"));
        sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
        if (schema.getProperties().getProperty(FILE_FORMAT) != null) {
            sql.append(" USING ").append(schema.getProperties().get(FILE_FORMAT)).append("\n");
        }
        if (schema.getProperties().getProperty(PARTITION_BY) != null) {
            sql.append(" PARTITIONED BY ").append("(").append(schema.getProperties().get(PARTITION_BY)).append(")").append("\n");
        }
        return sql.toString();
    }

    public Schema getSchemaFromSql(String sql, String domain) {
        String[] strings = StringUtils.getColumns(sql).split("\n");
        Schema schema = new Schema();
        schema.setDomain(domain);
        String tableName = sql.substring(sql.indexOf("create table") + 13, sql.indexOf("(", sql.indexOf("create table")));
        schema.setName(tableName.trim());
        Arrays.asList(strings).forEach(s -> {
            String str = s.trim();
            if (str.contains(" ")) {
                Element e = new Element();
                String name = str.substring(0, str.indexOf(" "));
                e.setName(name);
                String type = str.substring(str.indexOf(" ") + 1, str.indexOf(" ", str.indexOf(" ") + 1));
                e.setType(DataTypeConverter.toJDBCType(type));
                String comment = str.substring(str.indexOf("'") + 1, str.lastIndexOf("'"));
                e.setComment(comment);
                schema.getElements().add(e);
            }
        });
        schema.getProperties().setProperty(FILE_FORMAT, "parquet");
        if (sql.contains("partitioned by")) {
            String partition_by = sql.substring(sql.indexOf("partitioned by") + 16, sql.indexOf(")", sql.indexOf("partitioned by") + 15)).trim();
            schema.getProperties().setProperty(PARTITION_BY, partition_by);
        }
        schema.getProperties().setProperty(COMMENT, sql.substring(sql.indexOf("#"), sql.indexOf("\n", sql.indexOf("#"))).trim());
        return schema;
    }

    @Override
    public Schema getSchemaFromSql(String sql) {
        return getSchemaFromSql(sql, "");
    }

    @Override
    public List<Schema> getSchemaFromDBConnection(DatabaseConfiguration db) {

        //TODO
        return null;
    }

    public Schema getSchemaFromJsonStream(InputStream stream) {
        Schema schema = null;
        try {
            schema = objectMapper.readValue(stream, Schema.class);
        } catch (IOException e) {
            logger.error("getSchemaFromStream error!", e);
        }
        return schema;
    }

    public List<Schema> getSchemaListFromJsonStream(InputStream stream) {
        List<Schema> schemas = new ArrayList<>();
        try {
            schemas = objectMapper.readValue(stream, new TypeReference<List<Schema>>() {
            });
        } catch (IOException e) {
            logger.error("getSchemaFromStream error!", e);
        }
        return schemas;
    }

    public Schema getSchemaFromStream(InputStream stream, String domain, String name) {
        return getSchemaListFromJsonStream(stream).stream().filter(schema -> schema.getName().equalsIgnoreCase(name) && schema.getDomain().equalsIgnoreCase(domain)).findFirst().get();
    }

    public StructType getStructTypeFromSchema(Schema schema) {
        List<StructField> structFields = new ArrayList<>();
        schema.getElements().forEach(e -> {
            structFields.add(DataTypes.createStructField(e.getName(), DataTypeConverter.toSpark(e.getType()), e.getLimitations().get("nullable") == null || Boolean.parseBoolean(String.valueOf(e.getLimitations().get("nullable")))));
        });
        return DataTypes.createStructType(structFields);
    }

    public List<Element> getFromStructType(StructType structType) {
        List<Element> elements = new ArrayList<>();
        Seq<StructField> seq = structType.toSeq();
        for (int i = 0; i < seq.size(); i++) {
            Element e = new Element();
            if (seq.apply(i).getComment().isDefined()) {
                e.setComment(seq.apply(i).getComment().get());
            }
            e.setName(seq.apply(i).name());
            e.setType(DataTypeConverter.toJDBCType(seq.apply(i).dataType()));
            elements.add(i, e);

        }

        return null;
    }

    public static boolean isEquals(StructType source, StructType target) {
        if (source == null || target == null) {
            return false;
        }
        try {
            Seq<StructField> seq = source.toSeq();
            Seq<StructField> tar = target.toSeq();
            if (seq.size() != tar.size()) {
                logger.warn("source and target has deferent numbers StructFields");
                return false;
            }
            for (int i = 0; i < seq.size(); i++) {
                if (!(seq.apply(i).name().equals(tar.apply(i).name()) && seq.apply(i).dataType().equals(tar.apply(i).dataType()))) {
                    logger.warn(seq.apply(i) + " and " + tar.apply(i) + " deferent!");
                    return false;
                }
            }
        } catch (Throwable e) {
            logger.error("compare error!", e);
            return false;
        }
        return true;
    }

    public static boolean isEqualsExceptType(StructType source, StructType target) {
        if (source == null || target == null) {
            return false;
        }
        try {
            Seq<StructField> seq = source.toSeq();
            Seq<StructField> tar = target.toSeq();
            if (seq.size() != tar.size()) {
                logger.warn("source and target has deferent numbers StructFields");
                return false;
            }
            for (int i = 0; i < seq.size(); i++) {
                if (!(seq.apply(i).name().equals(tar.apply(i).name()))) {
                    logger.warn(seq.apply(i) + " and " + tar.apply(i) + " deferent!");
                    return false;
                } else if (!(seq.apply(i).dataType().equals(tar.apply(i).dataType()))) {
                    logger.warn(seq.apply(i) + " and " + tar.apply(i) + " deferent!");
                }
            }
        } catch (Throwable e) {
            logger.error("compare error!", e);
            return false;
        }
        return true;
    }

}
