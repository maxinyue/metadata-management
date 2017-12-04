package cn.maxinyue.metadata.management.tools;

import com.bjtuling.core.config.DatabaseConfiguration;
import cn.maxinyue.metadata.management.domain.Schema;

import java.util.List;

/**
 * Created by obama on 2017/4/24.
 */
public interface SQLUtils {

    String getSQLFromSchema(Schema schema);

    Schema getSchemaFromSql(String sql);

    List<Schema> getSchemaFromDBConnection(DatabaseConfiguration db);
}
