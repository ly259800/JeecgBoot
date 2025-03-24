CREATE TABLE `rider_customer` (
                                  `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                                  `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                                  `update_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                                  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
                                  `sys_org_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属部门',
                                  `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
                                  `phone` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
                                  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
                                  `identity` int DEFAULT '1' COMMENT '身份',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `rider_interview` (
                                   `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                                   `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                   `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                                   `update_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                                   `update_time` datetime DEFAULT NULL COMMENT '更新日期',
                                   `sys_org_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属部门',
                                   `name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
                                   `sex` int DEFAULT NULL COMMENT '性别',
                                   `age` int DEFAULT NULL COMMENT '年龄',
                                   `phone` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号码',
                                   `city` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '报道城市',
                                   `job_type` int DEFAULT '0' COMMENT '是否全职',
                                   `accommodation` int DEFAULT '0' COMMENT '是否需要住宿',
                                   `social_security` int DEFAULT '0' COMMENT '是否需要购买社保',
                                   `site_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '报道站点',
                                   `expect_region` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '期望区域地址',
                                   `reference` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推广人',
                                   `source` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '数据来源',
                                   `status` int DEFAULT '0' COMMENT '面试状态',
                                   `pass_status` int DEFAULT '0' COMMENT '通过状态',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `rider_site` (
                              `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                              `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                              `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                              `update_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                              `update_time` datetime DEFAULT NULL COMMENT '更新日期',
                              `sys_org_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属部门',
                              `city` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '城市',
                              `region` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '区域',
                              `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
                              `gap` int DEFAULT NULL COMMENT '缺口',
                              `memo` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
                              `commission` int DEFAULT NULL COMMENT '佣金',
                              `profit` int DEFAULT NULL COMMENT '利润',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;