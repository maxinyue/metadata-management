package cn.maxinyue.metadata.management.tools;

import cn.maxinyue.core.config.DatabaseConfiguration;
import cn.maxinyue.metadata.management.domain.Element;
import cn.maxinyue.metadata.management.domain.Schema;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by obama on 2017/4/24.
 */
public class MySQLUtils implements SQLUtils {

    static Logger logger = LoggerFactory.getLogger(MySQLUtils.class.getSimpleName());

    @Override
    public String getSQLFromSchema(Schema schema) {
        StringBuilder sql = new StringBuilder("create table " + schema.getDomain() + "." + schema.getName() + " (");
        schema.getElements().forEach(element -> {
            StringBuilder sb = new StringBuilder();
            element.getLimitations().forEach((key, value) -> {
                sb.append(key + " " + (value == null ? "" : value.toString()));
            });
            sql.append(element.getName() + " " + element.getType() + (element.getLength() > 0 ? "(" + element.getLength() + ")" : "") + " " + sb.toString() + ((Strings.isNullOrEmpty(element.getComment()) ? "" : (" COMMENT '" + element.getComment()) + "'")) + ",");
        });
        return sql.deleteCharAt(sql.lastIndexOf(",")).append(")").toString();
    }

    @Override
    public Schema getSchemaFromSql(String sql) {
        return null;
    }

    @Override
    public List<Schema> getSchemaFromDBConnection(DatabaseConfiguration db) {
        List<Schema> schemas = new ArrayList<>();
        Connection c = null;
        try {
            c = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPassword());
            DatabaseMetaData metaData = c.getMetaData();
            ResultSet rs = metaData.getTables(c.getCatalog(), null, null, null);
            while (rs.next()) {
                String t = rs.getString("TABLE_NAME");
                Schema s = new Schema();
                s.setName(t);
                s.setDomain(c.getCatalog());
                List<Element> elements = new ArrayList<>();
                s.setElements(elements);
                ResultSet crs = metaData.getColumns(c.getCatalog(), null, t, null);
                while (crs.next()) {
                    Element e = new Element();
                    e.setName(crs.getString("COLUMN_NAME"));
                    e.setType(JDBCType.valueOf(crs.getInt("DATA_TYPE")));
                    e.setLength(crs.getInt("COLUMN_SIZE"));
                    elements.add(e);
                }
                schemas.add(s);
            }

        } catch (Throwable e) {
            logger.error("getSchemaFromDBConnection error!", e);
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    logger.error("close connect error!", e);
                }
            }
        }
        return schemas;
    }
}
