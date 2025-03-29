-- 注意：该页面对应的前台目录为views/order文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external) 
VALUES ('2025032811542480330', NULL, '用户个人订单', '/order/riderUserOrderList', 'order/RiderUserOrderList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2025-03-28 23:54:33', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025032811542480331', '2025032811542480330', '添加用户个人订单', NULL, NULL, 0, NULL, NULL, 2, 'order:rider_user_order:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-03-28 23:54:33', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025032811542480332', '2025032811542480330', '编辑用户个人订单', NULL, NULL, 0, NULL, NULL, 2, 'order:rider_user_order:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-03-28 23:54:33', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025032811542480333', '2025032811542480330', '删除用户个人订单', NULL, NULL, 0, NULL, NULL, 2, 'order:rider_user_order:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-03-28 23:54:33', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025032811542480334', '2025032811542480330', '批量删除用户个人订单', NULL, NULL, 0, NULL, NULL, 2, 'order:rider_user_order:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-03-28 23:54:33', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025032811542480335', '2025032811542480330', '导出excel_用户个人订单', NULL, NULL, 0, NULL, NULL, 2, 'order:rider_user_order:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-03-28 23:54:33', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025032811542480336', '2025032811542480330', '导入excel_用户个人订单', NULL, NULL, 0, NULL, NULL, 2, 'order:rider_user_order:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-03-28 23:54:33', NULL, NULL, 0, 0, '1', 0);