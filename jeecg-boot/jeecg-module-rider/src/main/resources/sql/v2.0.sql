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
