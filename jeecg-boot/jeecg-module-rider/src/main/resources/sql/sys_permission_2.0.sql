

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





-- 注意：该页面对应的前台目录为views/course文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('2025062906237630540', NULL, '课程管理', '/course/videoCourseList', 'course/VideoCourseList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2025-06-29 18:23:54', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025062906237630541', '2025062906237630540', '添加课程管理', NULL, NULL, 0, NULL, NULL, 2, 'course:video_course:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-06-29 18:23:54', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025062906237630542', '2025062906237630540', '编辑课程管理', NULL, NULL, 0, NULL, NULL, 2, 'course:video_course:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-06-29 18:23:54', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025062906237630543', '2025062906237630540', '删除课程管理', NULL, NULL, 0, NULL, NULL, 2, 'course:video_course:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-06-29 18:23:54', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025062906237640544', '2025062906237630540', '批量删除课程管理', NULL, NULL, 0, NULL, NULL, 2, 'course:video_course:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-06-29 18:23:54', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025062906237640545', '2025062906237630540', '导出excel_课程管理', NULL, NULL, 0, NULL, NULL, 2, 'course:video_course:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-06-29 18:23:54', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025062906237640546', '2025062906237630540', '导入excel_课程管理', NULL, NULL, 0, NULL, NULL, 2, 'course:video_course:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-06-29 18:23:54', NULL, NULL, 0, 0, '1', 0);



--设置支付金额
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025032305268280325', '2025032305268280310', '设置支付金额', NULL, NULL, 0, NULL, NULL, 2, 'interview:rider_interview:updatePriceBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-03-23 17:26:31', NULL, NULL, 0, 0, '1', 0);


--确认培训
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025032305268280326', '2025032305268280310', '确认培训', NULL, NULL, 0, NULL, NULL, 2, 'interview:rider_interview:confirmTraining', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-03-23 17:26:31', NULL, NULL, 0, 0, '1', 0);









INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1946598429959491585', '0', '首页', 'A01', 'admin', '2025-07-19 23:50:06', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1946598461194473473', '0', '岗位', 'A02', 'admin', '2025-07-19 23:50:14', 'admin', '2025-07-19 23:51:04', 'A01', '1', 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1946598497340985345', '0', '学堂', 'A03', 'admin', '2025-07-19 23:50:22', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1946598669739462657', '1946598461194473473', '酒店', 'A02A01', 'admin', '2025-07-19 23:51:04', 'admin', '2025-07-19 23:53:42', 'A01', '1', 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1946598695572180993', '1946598461194473473', '家政', 'A02A02', 'admin', '2025-07-19 23:51:10', 'admin', '2025-07-28 13:59:39', 'A01', '1', 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1946598739155193858', '1946598461194473473', '高铁', 'A02A03', 'admin', '2025-07-19 23:51:20', 'admin', '2025-07-28 13:53:28', 'A01', '1', 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1946598810814877697', '1946598461194473473', '工厂', 'A02A04', 'admin', '2025-07-19 23:51:37', 'admin', '2025-07-26 13:56:42', 'A01', '1', 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1946599334146576385', '1946598669739462657', '前台', 'A02A01A01', 'admin', '2025-07-19 23:53:42', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1946599362634289154', '1946598669739462657', '客房保洁', 'A02A01A02', 'admin', '2025-07-19 23:53:49', 'admin', '2025-07-28 14:39:45', 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1946599387942719490', '1946598669739462657', '保安', 'A02A01A03', 'admin', '2025-07-19 23:53:55', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1948985809660121089', '1946598810814877697', '操作工', 'A02A04A01', 'admin', '2025-07-26 13:56:42', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949703836886208514', '1946598669739462657', '厨师', 'A02A01A05', 'admin', '2025-07-28 13:29:53', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949703903516921857', '1946598669739462657', '厨工、洗碗工', 'A02A01A06', 'admin', '2025-07-28 13:30:09', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949703973461135362', '1946598669739462657', '共区保洁', 'A02A01A07', 'admin', '2025-07-28 13:30:26', 'admin', '2025-07-29 11:33:25', 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949704070496358401', '1946598461194473473', '散工', 'A02A05', 'admin', '2025-07-28 13:30:49', 'admin', '2025-07-28 13:31:02', 'A01', '1', 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949704126343516161', '1949704070496358401', '服务员', 'A02A05A01', 'admin', '2025-07-28 13:31:02', 'admin', '2025-07-28 13:31:10', 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949704192768708609', '1949704070496358401', '收银员', 'A02A05A02', 'admin', '2025-07-28 13:31:18', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949704926314729474', '1949704070496358401', '迎宾员', 'A02A05A03', 'admin', '2025-07-28 13:34:13', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949704980899401730', '1946598669739462657', '洗杯工', 'A02A01A08', 'admin', '2025-07-28 13:34:26', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949705289960886274', '1946598810814877697', '普工', 'A02A04A02', 'admin', '2025-07-28 13:35:40', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949709770148646913', '1946598739155193858', '餐吧乘服员', 'A02A03A01', 'admin', '2025-07-28 13:53:28', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949709840969469954', '1946598739155193858', '跟车保洁', 'A02A03A02', 'admin', '2025-07-28 13:53:45', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949709931130228737', '1946598739155193858', '折返保洁', 'A02A03A03', 'admin', '2025-07-28 13:54:06', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949709995647012866', '1946598739155193858', '高铁配送员', 'A02A03A04', 'admin', '2025-07-28 13:54:21', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949710135774515202', '1946598739155193858', '站内保洁', 'A02A03A05', 'admin', '2025-07-28 13:54:55', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949710163834408961', '1946598739155193858', '安检', 'A02A03A06', 'admin', '2025-07-28 13:55:02', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949710558820405249', '1949704070496358401', '美容学徒', 'A02A05A04', 'admin', '2025-07-28 13:56:36', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949710663002722305', '1949704070496358401', '骑手', 'A02A05A05', 'admin', '2025-07-28 13:57:01', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949710762827157505', '1946598810814877697', '装卸工', 'A02A04A03', 'admin', '2025-07-28 13:57:24', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949710801330868225', '1946598810814877697', '快递分拣', 'A02A04A04', 'admin', '2025-07-28 13:57:34', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949711181544525826', '1949704070496358401', '司机', 'A02A05A06', 'admin', '2025-07-28 13:59:04', 'admin', '2025-07-28 13:59:14', 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949711261504737281', '1946598810814877697', '叉车工', 'A02A04A05', 'admin', '2025-07-28 13:59:23', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949711327015571458', '1946598695572180993', '养老护工', 'A02A02A01', 'admin', '2025-07-28 13:59:39', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949711374000164866', '1946598695572180993', '月嫂', 'A02A02A02', 'admin', '2025-07-28 13:59:50', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949711417591566338', '1946598695572180993', '育婴师', 'A02A02A03', 'admin', '2025-07-28 14:00:00', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949711466174189569', '1946598695572180993', '保姆', 'A02A02A04', 'admin', '2025-07-28 14:00:12', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949712658379935745', '1946598810814877697', '技工', 'A02A04A06', 'admin', '2025-07-28 14:04:56', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949712703061856257', '1946598810814877697', '仓管员', 'A02A04A07', 'admin', '2025-07-28 14:05:07', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949712746833612802', '1946598810814877697', '焊工', 'A02A04A08', 'admin', '2025-07-28 14:05:17', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949712773601660929', '1946598810814877697', '油漆工', 'A02A04A09', 'admin', '2025-07-28 14:05:24', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1949712822125563905', '1946598810814877697', '缝纫工', 'A02A04A10', 'admin', '2025-07-28 14:05:35', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1950060573543731201', '1946598739155193858', '动车检修', 'A02A03A07', 'admin', '2025-07-29 13:07:26', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1950065313182748674', '1946598739155193858', '国家电网', 'A02A03A08', 'admin', '2025-07-29 13:26:16', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1950066419866636290', '1946598739155193858', '乘务员', 'A02A03A09', 'admin', '2025-07-29 13:30:40', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1950068797080702978', '1946598739155193858', '库内保洁', 'A02A03A10', 'admin', '2025-07-29 13:40:06', null, null, 'A01', null, 0);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id) VALUES ('1950070480942764033', '1946598739155193858', '机场消防员', 'A02A03A11', 'admin', '2025-07-29 13:46:48', null, null, 'A01', null, 0);





-- 注意：该页面对应的前台目录为views/talentpool文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('2025101908167450240', NULL, '人才库', '/talentpool/familyTalentPoolList', 'talentpool/FamilyTalentPoolList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2025-10-19 20:16:24', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025101908167450241', '2025101908167450240', '添加人才库', NULL, NULL, 0, NULL, NULL, 2, 'talentpool:family_talent_pool:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-10-19 20:16:24', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025101908167450242', '2025101908167450240', '编辑人才库', NULL, NULL, 0, NULL, NULL, 2, 'talentpool:family_talent_pool:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-10-19 20:16:24', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025101908167450243', '2025101908167450240', '删除人才库', NULL, NULL, 0, NULL, NULL, 2, 'talentpool:family_talent_pool:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-10-19 20:16:24', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025101908167450244', '2025101908167450240', '批量删除人才库', NULL, NULL, 0, NULL, NULL, 2, 'talentpool:family_talent_pool:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-10-19 20:16:24', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025101908167450245', '2025101908167450240', '导出excel_人才库', NULL, NULL, 0, NULL, NULL, 2, 'talentpool:family_talent_pool:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-10-19 20:16:24', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025101908167450246', '2025101908167450240', '导入excel_人才库', NULL, NULL, 0, NULL, NULL, 2, 'talentpool:family_talent_pool:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-10-19 20:16:24', NULL, NULL, 0, 0, '1', 0);



-- 注意：该页面对应的前台目录为views/starwall文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('2025102208485910520', NULL, '首页星光墙', '/starwall/familyStarWallList', 'starwall/FamilyStarWallList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2025-10-22 20:48:52', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025102208485920521', '2025102208485910520', '添加首页星光墙', NULL, NULL, 0, NULL, NULL, 2, 'starwall:family_star_wall:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-10-22 20:48:52', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025102208485920522', '2025102208485910520', '编辑首页星光墙', NULL, NULL, 0, NULL, NULL, 2, 'starwall:family_star_wall:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-10-22 20:48:52', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025102208485920523', '2025102208485910520', '删除首页星光墙', NULL, NULL, 0, NULL, NULL, 2, 'starwall:family_star_wall:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-10-22 20:48:52', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025102208485920524', '2025102208485910520', '批量删除首页星光墙', NULL, NULL, 0, NULL, NULL, 2, 'starwall:family_star_wall:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-10-22 20:48:52', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025102208485920525', '2025102208485910520', '导出excel_首页星光墙', NULL, NULL, 0, NULL, NULL, 2, 'starwall:family_star_wall:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-10-22 20:48:52', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025102208485930526', '2025102208485910520', '导入excel_首页星光墙', NULL, NULL, 0, NULL, NULL, 2, 'starwall:family_star_wall:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-10-22 20:48:52', NULL, NULL, 0, 0, '1', 0);



INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025032305267340219', '2025032305267340210', '确认风控', NULL, NULL, 0, NULL, NULL, 2, 'customer:rider_customer:comfirmRiskControl', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-03-23 17:26:31', NULL, NULL, 0, 0, '1', 0);
