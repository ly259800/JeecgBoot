CREATE TABLE `family_post` (
                               `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                               `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                               `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                               `update_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                               `update_time` datetime DEFAULT NULL COMMENT '更新日期',
                               `sys_org_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属部门',
                               `category_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '岗位类型ID',
                               `category_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '岗位类型名称',
                               `tag` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签',
                               `salary_range` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '薪资范围',
                               `post_name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '岗位名称',
                               `hourly_wage` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '时薪',
                               `address` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '详细地址',
                               `longitude` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '经度',
                               `latitude` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '纬度',
                               `commission` decimal(12,2) DEFAULT NULL COMMENT '佣金',
                               `city` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '城市',
                               `benefit` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '福利',
                               `publish_status` int DEFAULT '0' COMMENT '发布状态',
                               `contact_phone` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
                               `contacts` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系人',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `family_post_detail` (
                                      `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                                      `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                      `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                                      `update_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                                      `update_time` datetime DEFAULT NULL COMMENT '更新日期',
                                      `sys_org_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属部门',
                                      `post_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '岗位ID',
                                      `post_detail` longtext COLLATE utf8mb4_unicode_ci COMMENT '岗位详情',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `video_course` (
                                `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                                `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                                `update_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                                `update_time` datetime DEFAULT NULL COMMENT '更新日期',
                                `sys_org_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属部门',
                                `classification` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分类',
                                `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
                                `pay_type` int DEFAULT NULL COMMENT '付费类型',
                                `price` decimal(10,2) DEFAULT NULL COMMENT '付费价格',
                                `url` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '视频链接',
                                `sort` int DEFAULT NULL COMMENT '排序',
                                `classification_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分类名称',
                                `cover` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '封面',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `video_unlock_record` (
                                       `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                                       `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                       `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                                       `update_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                                       `update_time` datetime DEFAULT NULL COMMENT '更新日期',
                                       `sys_org_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属部门',
                                       `video_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '视频ID',
                                       `customer_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户ID',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


alter table rider_customer add column `open_user_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '法大大openid';
alter table rider_customer add column `id_card` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证号';

alter table family_post add column  `pay_type` int DEFAULT NULL COMMENT '付费类型';
alter table family_post add column `price` decimal(10,2) DEFAULT NULL COMMENT '付费价格';
alter table family_post add column `training_status` int DEFAULT '0' COMMENT '是否培训';


alter table rider_interview
    change job_type pay_status int default 0 null comment '是否支付';

alter table rider_interview
    change accommodation sign_status int default 0 null comment '是否签署';

alter table rider_interview
    change social_security training_status int default 0 null comment '是否培训';

alter table rider_interview
    change electric_vehicle confirm_status int default 0 null comment '是否岗位确认';

alter table rider_user_order add column `interview_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '报名记录ID';


alter table family_post_detail add column `environment` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工作环境';


alter table rider_params
    modify param_value longtext null comment '参数值';


alter table rider_interview add column `id_card` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证号';
alter table rider_interview add column `sign_task_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '签署任务';
alter table rider_interview add column `price` decimal(10,2) DEFAULT NULL COMMENT '付费价格';

alter table rider_customer add column `sign_task_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '签署任务';


alter table rider_interview
    modify expect_region varchar(100) null comment '区域地址';

alter table rider_interview
    modify job_position varchar(100) null comment '工作地点';

alter table rider_interview
    change interview_date operator_name varchar(32) null comment '操作人名称';

alter table family_post add column `sort` int DEFAULT '0' comment '排序';



alter table rider_customer add column `sign_task_url` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '签署文件链接';

alter table rider_interview add column `sign_task_url` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '签署文件链接';

alter table rider_interview add column `training_teacher` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '招聘老师';

alter table rider_interview add column `video_url` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '视频链接';


alter table video_course add column `video_type` int DEFAULT null comment '视频类型';
