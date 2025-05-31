alter table rider_customer add column `site_identity` int DEFAULT '0' COMMENT '是否渠道商(1-是 0-否)';
alter table rider_customer add column `site_profit` int DEFAULT '0' COMMENT '站点利润';
alter table rider_customer add column `site_reference` int DEFAULT '0' COMMENT '推广金额';


-- 升级为渠道商
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025032305267340218', '2025032305267340210', '升级为渠道商', NULL, NULL, 0, NULL, NULL, 2, 'customer:rider_customer:upgradeSite', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-03-23 17:26:31', NULL, NULL, 0, 0, '1', 0);
