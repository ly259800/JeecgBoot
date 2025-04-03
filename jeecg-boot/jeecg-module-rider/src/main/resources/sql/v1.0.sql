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
                                  `wx_open_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信openid',
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
                                   `electric_vehicle` int DEFAULT NULL COMMENT '是否需要电动车',
                                   `site_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '站点id',
                                   `entrance` int DEFAULT NULL COMMENT '小程序入口',
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
                              `commission` int DEFAULT NULL COMMENT '美团佣金',
                              `profit` int DEFAULT NULL COMMENT '利润',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `rider_city` (
                              `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                              `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                              `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                              `update_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                              `update_time` datetime DEFAULT NULL COMMENT '更新日期',
                              `sys_org_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属部门',
                              `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
                              `url` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '链接',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `rider_pay_order` (
                                   `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                                   `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                   `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                                   `update_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                                   `update_time` datetime DEFAULT NULL COMMENT '更新日期',
                                   `sys_org_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属部门',
                                   `appid` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '应用ID',
                                   `mchid` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户号',
                                   `out_trade_no` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户订单号',
                                   `description` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品描述',
                                   `pre_pay_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预支付交易会话标识',
                                   `transaction_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信支付订单号',
                                   `trade_type` int DEFAULT NULL COMMENT '交易类型',
                                   `trade_state` int DEFAULT NULL COMMENT '交易状态',
                                   `openid` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户唯一标识',
                                   `total_amount` decimal(10,2) DEFAULT NULL COMMENT '订单总金额',
                                   `currency` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '货币类型',
                                   `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '用户支付金额',
                                   `pay_currency` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户支付币种类型',
                                   `bank_type` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '银行类型',
                                   `success_time` datetime DEFAULT NULL COMMENT '支付完成时间',
                                   `close_state` int DEFAULT NULL COMMENT '关闭状态',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `rider_user_order` (
                                    `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                                    `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                    `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                                    `update_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                                    `update_time` datetime DEFAULT NULL COMMENT '更新日期',
                                    `sys_org_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属部门',
                                    `customer_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客户ID',
                                    `out_trade_no` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商户订单号',
                                    `order_state` int DEFAULT NULL COMMENT '订单状态',
                                    `order_type` int DEFAULT NULL COMMENT '订单类型',
                                    `total_amount` decimal(10,2) DEFAULT NULL COMMENT '订单总金额',
                                    `actual_amount` decimal(10,2) DEFAULT NULL COMMENT '实际支付金额',
                                    `description` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品描述',
                                    `pay_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付人ID',
                                    `payer` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付人名称',
                                    `success_time` datetime DEFAULT NULL COMMENT '支付完成时间',
                                    `cancel_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '取消人ID',
                                    `canceler` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '取消人',
                                    `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
                                    `payment_method` int DEFAULT NULL COMMENT '支付方式',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;