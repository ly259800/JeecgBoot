CREATE TABLE `family_post` (
                               `id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
                               `create_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                               `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                               `update_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                               `update_time` datetime DEFAULT NULL COMMENT '更新日期',
                               `sys_org_code` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属部门',
                               `category_code` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '岗位类型编码',
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
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- 注意：该页面对应的前台目录为views/post文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('2025062912013070170', NULL, '岗位管理', '/post/postList', 'post/PostList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2025-06-29 00:01:17', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025062912013070171', '2025062912013070170', '添加岗位管理', NULL, NULL, 0, NULL, NULL, 2, 'post:family_post:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-06-29 00:01:17', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025062912013070172', '2025062912013070170', '编辑岗位管理', NULL, NULL, 0, NULL, NULL, 2, 'post:family_post:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-06-29 00:01:17', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025062912013070173', '2025062912013070170', '删除岗位管理', NULL, NULL, 0, NULL, NULL, 2, 'post:family_post:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-06-29 00:01:17', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025062912013070174', '2025062912013070170', '批量删除岗位管理', NULL, NULL, 0, NULL, NULL, 2, 'post:family_post:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-06-29 00:01:17', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025062912013070175', '2025062912013070170', '导出excel_岗位管理', NULL, NULL, 0, NULL, NULL, 2, 'post:family_post:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-06-29 00:01:17', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025062912013080176', '2025062912013070170', '导入excel_岗位管理', NULL, NULL, 0, NULL, NULL, 2, 'post:family_post:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-06-29 00:01:17', NULL, NULL, 0, 0, '1', 0);

INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025062912013080177', '2025062912013070170', '设置岗位详情', NULL, NULL, 0, NULL, NULL, 2, 'post:family_post_detail:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-06-29 00:01:17', NULL, NULL, 0, 0, '1', 0);

INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025062912013080178', '2025062912013070170', '确认发布', NULL, NULL, 0, NULL, NULL, 2, 'post:family_post:publishBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-06-29 00:01:17', NULL, NULL, 0, 0, '1', 0);

INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025062912013080179', '2025062912013070170', '确认下架', NULL, NULL, 0, NULL, NULL, 2, 'post:family_post:cancelBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-06-29 00:01:17', NULL, NULL, 0, 0, '1', 0);


