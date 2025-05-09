alter table rider_customer add column `settle_commission` decimal(10,2) DEFAULT 0 COMMENT '已提现佣金';
alter table rider_customer add column  `commission` decimal(10,2) DEFAULT 0 COMMENT '我的佣金';
alter table rider_interview add column  `settle_status` int DEFAULT 0 COMMENT '结算状态';

--确认结算
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025032305268280321', '2025032305268280310', '确认结算', NULL, NULL, 0, NULL, NULL, 2, 'interview:rider_interview:settleBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-03-23 17:26:31', NULL, NULL, 0, 0, '1', 0);



CREATE TABLE `rider_commission` (
        `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
        `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
        `create_time` datetime DEFAULT NULL COMMENT '创建日期',
        `update_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
        `update_time` datetime DEFAULT NULL COMMENT '更新日期',
        `sys_org_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属部门',
        `customer_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推广人ID',
        `customer_phone` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推广人手机',
        `interview_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '面试人ID',
        `interview_phone` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '面试人手机号',
        `interview_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '面试人名称',
        `commission` int DEFAULT 0 COMMENT '佣金',
        `audit_status` int DEFAULT 0 COMMENT '审核状态',
        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

alter table rider_site add column `platform` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '平台';


alter table rider_customer add column `unionid` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'unionid';


alter table rider_customer add column `reference` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推广人';
alter table rider_customer add column `reference_phone` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推广人手机号';