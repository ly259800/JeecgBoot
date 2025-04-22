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


CREATE TABLE `rider_params` (
                                `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                                `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                                `update_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                                `update_time` datetime DEFAULT NULL COMMENT '更新日期',
                                `sys_org_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属部门',
                                `param_code` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '参数编码',
                                `param_value` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '参数值',
                                `memo` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


alter table sys_user add column `wx_open_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信openid';
alter table sys_user add column `unionid` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信unionid';
alter table sys_user add column `user_level` int DEFAULT 1 COMMENT '会员等级';


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
                                `url` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '视频链接',
                                `sort` int DEFAULT NULL COMMENT '排序',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `video_matrix` (
                                `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                                `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                                `update_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                                `update_time` datetime DEFAULT NULL COMMENT '更新日期',
                                `sys_org_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属部门',
                                `video_url` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '我的视频',
                                `content` text COLLATE utf8mb4_unicode_ci COMMENT '视频描述',
                                `release_tiktok` int DEFAULT NULL COMMENT '发布抖音',
                                `release_kwai` int DEFAULT NULL COMMENT '发布快手',
                                `release_wechat` int DEFAULT NULL COMMENT '发布视频号',
                                `release_rednote` int DEFAULT NULL COMMENT '发布小红书',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;