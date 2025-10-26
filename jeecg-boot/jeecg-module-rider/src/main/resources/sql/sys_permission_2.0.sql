

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





INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1946598429959491585', '0', '首页', 'A01', 'admin', '2025-07-19 23:50:06', 'admin', '2025-09-30 11:03:57', 'A01', '1', 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1946598461194473473', '0', '岗位', 'A02', 'admin', '2025-07-19 23:50:14', 'admin', '2025-07-19 23:51:04', 'A01', '1', 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1946598497340985345', '0', '学堂', 'A03', 'admin', '2025-07-19 23:50:22', 'admin', '2025-09-21 19:38:51', 'A01', null, 0, '');
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1946598669739462657', '1946598461194473473', '酒店', 'A02A01', 'admin', '2025-07-19 23:51:04', 'admin', '2025-08-02 17:00:49', 'A01', '1', 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1946598695572180993', '1946598461194473473', '家政', 'A02A02', 'admin', '2025-07-19 23:51:10', 'admin', '2025-08-02 17:01:02', 'A01', '1', 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1946598739155193858', '1946598461194473473', '高铁', 'A02A03', 'admin', '2025-07-19 23:51:20', 'admin', '2025-08-02 17:01:13', 'A01', '1', 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1946598810814877697', '1946598461194473473', '工厂', 'A02A04', 'admin', '2025-07-19 23:51:37', 'admin', '2025-08-02 17:01:19', 'A01', '1', 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1946599334146576385', '1946598669739462657', '前台', 'A02A01A01', 'admin', '2025-07-19 23:53:42', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1946599362634289154', '1946598669739462657', '客房保洁', 'A02A01A02', 'admin', '2025-07-19 23:53:49', 'admin', '2025-07-28 14:39:45', 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1946599387942719490', '1946598669739462657', '保安', 'A02A01A03', 'admin', '2025-07-19 23:53:55', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1948985809660121089', '1946598810814877697', '操作工', 'A02A04A01', 'admin', '2025-07-26 13:56:42', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949703836886208514', '1946598669739462657', '厨师', 'A02A01A05', 'admin', '2025-07-28 13:29:53', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949703903516921857', '1946598669739462657', '厨工、洗碗工', 'A02A01A06', 'admin', '2025-07-28 13:30:09', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949703973461135362', '1946598669739462657', '共区保洁', 'A02A01A07', 'admin', '2025-07-28 13:30:26', 'admin', '2025-07-29 11:33:25', 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949704070496358401', '1946598461194473473', '散工', 'A02A05', 'admin', '2025-07-28 13:30:49', 'admin', '2025-07-28 13:31:02', 'A01', '1', 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949704126343516161', '1949704070496358401', '服务员', 'A02A05A01', 'admin', '2025-07-28 13:31:02', 'admin', '2025-07-28 13:31:10', 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949704192768708609', '1949704070496358401', '收银员', 'A02A05A02', 'admin', '2025-07-28 13:31:18', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949704926314729474', '1949704070496358401', '迎宾员', 'A02A05A03', 'admin', '2025-07-28 13:34:13', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949704980899401730', '1946598669739462657', '洗杯工', 'A02A01A08', 'admin', '2025-07-28 13:34:26', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949705289960886274', '1946598810814877697', '普工', 'A02A04A02', 'admin', '2025-07-28 13:35:40', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949709770148646913', '1946598739155193858', '餐吧乘服员', 'A02A03A01', 'admin', '2025-07-28 13:53:28', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949709840969469954', '1946598739155193858', '跟车保洁', 'A02A03A02', 'admin', '2025-07-28 13:53:45', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949709931130228737', '1946598739155193858', '折返保洁', 'A02A03A03', 'admin', '2025-07-28 13:54:06', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949709995647012866', '1946598739155193858', '高铁配送员', 'A02A03A04', 'admin', '2025-07-28 13:54:21', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949710135774515202', '1946598739155193858', '站内保洁', 'A02A03A05', 'admin', '2025-07-28 13:54:55', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949710163834408961', '1946598739155193858', '安检', 'A02A03A06', 'admin', '2025-07-28 13:55:02', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949710558820405249', '1949704070496358401', '美容学徒', 'A02A05A04', 'admin', '2025-07-28 13:56:36', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949710663002722305', '1949704070496358401', '骑手', 'A02A05A05', 'admin', '2025-07-28 13:57:01', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949710762827157505', '1946598810814877697', '装卸工', 'A02A04A03', 'admin', '2025-07-28 13:57:24', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949710801330868225', '1946598810814877697', '快递分拣', 'A02A04A04', 'admin', '2025-07-28 13:57:34', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949711181544525826', '1949704070496358401', '司机', 'A02A05A06', 'admin', '2025-07-28 13:59:04', 'admin', '2025-07-28 13:59:14', 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949711261504737281', '1946598810814877697', '叉车工', 'A02A04A05', 'admin', '2025-07-28 13:59:23', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949711327015571458', '1946598695572180993', '养老护工', 'A02A02A01', 'admin', '2025-07-28 13:59:39', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949711374000164866', '1946598695572180993', '月嫂', 'A02A02A02', 'admin', '2025-07-28 13:59:50', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949711417591566338', '1946598695572180993', '育婴师', 'A02A02A03', 'admin', '2025-07-28 14:00:00', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949711466174189569', '1946598695572180993', '保姆', 'A02A02A04', 'admin', '2025-07-28 14:00:12', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949712658379935745', '1946598810814877697', '技工', 'A02A04A06', 'admin', '2025-07-28 14:04:56', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949712703061856257', '1946598810814877697', '仓管员', 'A02A04A07', 'admin', '2025-07-28 14:05:07', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949712746833612802', '1946598810814877697', '焊工', 'A02A04A08', 'admin', '2025-07-28 14:05:17', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949712773601660929', '1946598810814877697', '油漆工', 'A02A04A09', 'admin', '2025-07-28 14:05:24', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1949712822125563905', '1946598810814877697', '缝纫工', 'A02A04A10', 'admin', '2025-07-28 14:05:35', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1950060573543731201', '1946598739155193858', '动车检修', 'A02A03A07', 'admin', '2025-07-29 13:07:26', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1950065313182748674', '1946598739155193858', '国家电网', 'A02A03A08', 'admin', '2025-07-29 13:26:16', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1950066419866636290', '1946598739155193858', '乘务员', 'A02A03A09', 'admin', '2025-07-29 13:30:40', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1950068797080702978', '1946598739155193858', '库内保洁', 'A02A03A10', 'admin', '2025-07-29 13:40:06', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1950070480942764033', '1946598739155193858', '机场消防员', 'A02A03A11', 'admin', '2025-07-29 13:46:48', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1970494857077166082', '0', '标签', 'A04', 'admin', '2025-09-23 22:25:59', 'admin', '2025-09-26 10:02:53', 'A01', '1', 0, '');
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1970494998542651394', '1970494857077166082', '意向阶段', 'A04A01', 'admin', '2025-09-23 22:26:32', 'admin', '2025-09-28 09:08:14', 'A01', '1', 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1970495056281440257', '1970494857077166082', '意向标签', 'A04A02', 'admin', '2025-09-23 22:26:46', 'admin', '2025-09-28 09:08:07', 'A01', '1', 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1970495112363479042', '1970494857077166082', '岗位需求', 'A04A03', 'admin', '2025-09-23 22:26:59', 'admin', '2025-09-28 09:08:25', 'A01', '1', 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1970495167443079170', '1970494998542651394', '初步了解期', 'A04A01A01', 'admin', '2025-09-23 22:27:13', 'admin', '2025-09-28 09:08:54', 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1970495203438596098', '1970495056281440257', '初探观望', 'A04A02A01', 'admin', '2025-09-23 22:27:21', 'admin', '2025-09-28 09:09:35', 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1970495249559162882', '1970495112363479042', '家政类', 'A04A03A01', 'admin', '2025-09-23 22:27:32', 'admin', '2025-09-28 09:10:29', 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1970495276134273025', '1970495112363479042', '高铁类', 'A04A03A02', 'admin', '2025-09-23 22:27:39', 'admin', '2025-09-28 09:10:35', 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1971386241938198530', '1970494998542651394', '深入考虑期', 'A04A01A02', 'admin', '2025-09-26 09:28:01', 'admin', '2025-09-28 09:09:01', 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1971386327992733697', '1970494998542651394', '决策准备期', 'A04A01A03', 'admin', '2025-09-26 09:28:22', 'admin', '2025-09-28 09:09:09', 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1972106412319412226', '1970495056281440257', '定向咨询', 'A04A02A02', 'admin', '2025-09-28 09:09:43', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1972106448226848770', '1970495056281440257', '对比筛选', 'A04A02A03', 'admin', '2025-09-28 09:09:52', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1972106487519088642', '1970495056281440257', '强意向待确认', 'A04A02A04', 'admin', '2025-09-28 09:10:01', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1972106525758558209', '1970495056281440257', '暂缓决策', 'A04A02A05', 'admin', '2025-09-28 09:10:10', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1972106554225299457', '1970495056281440257', '已转化/成交', 'A04A02A06', 'admin', '2025-09-28 09:10:17', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1972106660114698242', '1970495112363479042', '工厂类', 'A04A03A03', 'admin', '2025-09-28 09:10:42', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1972106692666691585', '1970495112363479042', '酒店类', 'A04A03A04', 'admin', '2025-09-28 09:10:50', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1972106721120849922', '1970495112363479042', '散工类', 'A04A03A05', 'admin', '2025-09-28 09:10:57', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1972859933457223682', '1946598429959491585', '娘家人主理人', 'A01A01', 'admin', '2025-09-30 11:03:57', 'admin', '2025-10-21 12:30:13', 'A01', null, 0, 'https://family-shanghai-file.oss-cn-shanghai.aliyuncs.com/temp/娘家人主理人.jpg');
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1972870404096757762', '1946598429959491585', '走进娘家人', 'A01A02', 'admin', '2025-09-30 11:45:33', 'admin', '2025-10-21 12:43:14', 'A01', null, 0, 'https://family-shanghai-file.oss-cn-shanghai.aliyuncs.com/temp/娘家人平台.jpg');
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1972870478461767682', '1946598429959491585', '客房服务员', 'A01A03', 'admin', '2025-09-30 11:45:51', 'admin', '2025-10-21 13:04:09', 'A01', '0', 0, 'https://family-shanghai-file.oss-cn-shanghai.aliyuncs.com/temp/客房.jpg');
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1972870546443046913', '1946598429959491585', '小儿推拿师', 'A01A04', 'admin', '2025-09-30 11:46:07', 'admin', '2025-10-21 21:46:07', 'A01', null, 0, 'https://family-shanghai-file.oss-cn-shanghai.aliyuncs.com/temp/小儿推拿.jpg');
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1972870584346972162', '1946598429959491585', '养老护理员', 'A01A05', 'admin', '2025-09-30 11:46:16', 'admin', '2025-10-22 11:56:11', 'A01', null, 0, 'https://family-shanghai-file.oss-cn-shanghai.aliyuncs.com/temp/老年护理1.jpg');
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1972870631969099778', '1946598429959491585', '育婴早教师', 'A01A06', 'admin', '2025-09-30 11:46:27', 'admin', '2025-10-22 11:40:10', 'A01', null, 0, 'https://family-shanghai-file.oss-cn-shanghai.aliyuncs.com/temp/育婴1.jpg');
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1976503874475888641', '1946598429959491585', '高级养老护理', 'A01A07', 'admin', '2025-10-10 12:23:40', 'admin', '2025-10-22 11:56:22', 'A01', null, 0, 'https://family-shanghai-file.oss-cn-shanghai.aliyuncs.com/temp/养老2.jpg');
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1976503968482824194', '1946598429959491585', '全实操育婴师', 'A01A08', 'admin', '2025-10-10 12:24:02', 'admin', '2025-10-22 11:40:17', 'A01', null, 0, 'https://family-shanghai-file.oss-cn-shanghai.aliyuncs.com/temp/育婴2.jpg');
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1978025565647638530', '1946598669739462657', '客房主管', 'A02A01A09', 'admin', '2025-10-14 17:10:19', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1978025628151156738', '1946598669739462657', '客房经理', 'A02A01A10', 'admin', '2025-10-14 17:10:34', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1978025706764996610', '1946598695572180993', '家庭保洁师', 'A02A02A05', 'admin', '2025-10-14 17:10:53', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1978025774628835329', '1946598695572180993', '家庭收纳师', 'A02A02A06', 'admin', '2025-10-14 17:11:09', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1978025842220044290', '1946598695572180993', '家电清洁师', 'A02A02A07', 'admin', '2025-10-14 17:11:25', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1978025892744630274', '1946598695572180993', '产后康复师', 'A02A02A08', 'admin', '2025-10-14 17:11:37', 'admin', '2025-10-14 17:11:53', 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1978026032230404097', '1946598695572180993', '产后催乳师', 'A02A02A09', 'admin', '2025-10-14 17:12:11', null, null, 'A01', null, 0, null);
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1978690419534434305', '1946598429959491585', '剪映基础教程', 'A01A09', 'admin', '2025-10-16 13:12:13', 'admin', '2025-10-21 12:30:38', 'A01', null, 0, 'https://family-shanghai-file.oss-cn-shanghai.aliyuncs.com/temp/剪映基础教程.jpg');
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1980132288189534209', '1946598429959491585', '短视频基础课', 'A01A10', 'admin', '2025-10-20 12:41:41', null, null, 'A01', null, 0, 'https://family-shanghai-file.oss-cn-shanghai.aliyuncs.com/temp/短视频基础课.jpg');
INSERT INTO `jeecg-family`.sys_category (id, pid, name, code, create_by, create_time, update_by, update_time, sys_org_code, has_child, tenant_id, image) VALUES ('1980132344510648321', '1946598429959491585', '短视频精通', 'A01A11', 'admin', '2025-10-20 12:41:55', null, null, 'A01', null, 0, 'https://family-shanghai-file.oss-cn-shanghai.aliyuncs.com/temp/短视频精通.jpg');




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
