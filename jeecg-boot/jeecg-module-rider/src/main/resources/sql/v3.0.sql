
alter table family_star_wall add column `customer_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主理人ID';

-- auto-generated definition
create table family_tourism
(
    id              varchar(36)    not null
        primary key,
    create_by       varchar(50)    null comment '创建人',
    create_time     datetime       null comment '创建日期',
    update_by       varchar(50)    null comment '更新人',
    update_time     datetime       null comment '更新日期',
    sys_org_code    varchar(64)    null comment '所属部门',
    title           varchar(64)    null comment '标题',
    cover           varchar(128)   null comment '封面',
    address         varchar(32)    null comment '时间地点',
    tag             varchar(128)   null comment '标签',
    un_regist_price decimal(10, 2) null comment '普通价格',
    price           decimal(10, 2) null comment '会员价格',
    commission      decimal(10, 2) null comment '佣金'
);



-- auto-generated definition
create table family_tourism_detail
(
    id           varchar(36)  not null
        primary key,
    create_by    varchar(50)  null comment '创建人',
    create_time  datetime     null comment '创建日期',
    update_by    varchar(50)  null comment '更新人',
    update_time  datetime     null comment '更新日期',
    sys_org_code varchar(64)  null comment '所属部门',
    image        varchar(512) null comment '头部图片',
    video        varchar(512) null comment '视频',
    route_pic    varchar(512) null comment '路线图片',
    memo         varchar(255) null comment '备注',
    tourism_id   varchar(32)  null comment '主表ID'
);


