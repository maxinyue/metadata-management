package cn.maxinyue.metadata.management.tools;

import cn.maxinyue.metadata.management.domain.Element;
import cn.maxinyue.metadata.management.domain.Schema;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by obama on 2017/4/25.
 */
public class StringUtilsTest {

    public static String sql = "create table ods_archive(\n" +
            "create_date date comment '建档时间时间维度的log_date',\n" +
            "zone_id string comment '服务机构对应区划id',\n" +
            "service_org_id string comment '服务机构id区划机构维id',\n" +
            "assit_org_id string comment '协作机构id区划机构维id',\n" +
            "followup_org_id string comment '随访机构id区划机构维id',\n" +
            "followup_zone_id string comment '随访区划维id',\n" +
            "data_flag string comment '数据标识',\n" +
            "id string comment '档案id',\n" +
            "archive_code string comment '档案编号',\n" +
            "dim_archive_info_id string comment '档案信息维度id',\n" +
            "alter_date date comment '最后修改时间时间维度log_date',\n" +
            "update_date date comment '最后更新时间时间维度log_date',\n" +
            "del_flag string comment '是否删除'\n" +
            ")partitioned by (province String)  STORED AS parquet;";

    @Test
    public void should_get() {
//        System.out.println(StringUtils.getColumns(sql));
        String[] strings = StringUtils.getColumns(sql).split("\n");
        Schema schema = new Schema();
        String tableName = sql.substring(sql.indexOf("create table") + 13, sql.indexOf("("));
        System.out.println("tableName:" + tableName);
        schema.setName(tableName);
        Arrays.asList(strings).forEach(s -> {
            String str = s.trim();
            if (str.contains(" ")) {
                Element e = new Element();
                String name = str.substring(0, str.indexOf(" "));
                System.out.println("name:" + name);
                e.setName(name);
                String type = str.substring(str.indexOf(" ") + 1, str.indexOf(" ", str.indexOf(" ") + 1));
                System.out.println("type:" + type);
                String comment = str.substring(str.indexOf("'") + 1, str.lastIndexOf("'"));
                System.out.println("comment:" + comment);
                schema.getElements().add(e);
            }
        });
    }

    @Test
    public void should() {
        Arrays.asList(sql.split(";")).forEach(atable -> {

        });
    }
}
