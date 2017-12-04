#######检查项结果（病史问询、生殖、体格、B超）#######
create table ods_exam_item_value(
building_date date comment '建档时间,时间维度的log_date',
zone_id string comment '服务机构对应区划id',
service_org_id string comment '服务机构id，区划机构维id',
assist_org_id string comment '协作机构id，区划机构维id',
followup_org_id string comment '随访机构id，区划机构维id',
archive_id string comment '档案id',
data_flag string comment '数据标识'，
id string comment 'id',
exam_date date comment '检查时间，时间维度的log_date',
gender string comment '性别',
exam_org_id string comment '检查机构id，区划机构维',
exam_item_id string comment '检查项目id,检查项目维度',
is_complete string comment '完成状态',
update_date date comment '最后更新时间，时间维度log_date',
create_date date comment '创建时间',
province string comment '省份'
)partitioned by (province,create_date)  STORED AS parquet;