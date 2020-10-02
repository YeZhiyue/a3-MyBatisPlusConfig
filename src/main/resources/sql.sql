create table dict_bed
(
    id          varchar(36)              not null comment 'id'
        primary key,
    cn_name     varchar(64)              null comment '床类型名称（中文）',
    en_name     varchar(64)              null comment '床类型名称（英文）',
    type        varchar(10)              null comment '种类分组',
    description varchar(255)             null comment '描述',
    create_time bigint        default 0  null comment '创建时间',
    update_time bigint        default 0  null comment '更新时间',
    create_by   varchar(36)              null comment '创建者',
    update_by   varchar(36)              null comment '更新者',
    version     int           default 0  null comment '版本',
    deleted     int           default 0  null comment '是否有效 0-未删除 1-已删除',
    extra       varchar(1000) default '' null comment '额外信息',
    weight      int           default 0  null comment '权重',
    tenant_id   varchar(255)  default '' null comment '租户id'
)
    comment '床类型字典表';

INSERT INTO park.dict_bed (id, cn_name, en_name, type, description, create_time, update_time, create_by, update_by, version, deleted, extra, weight, tenant_id) VALUES ('1', '测试床', 'single bed', 'yy', '测试摇号', 1601550204570, 0, null, null, 0, 0, '', 0, '');
INSERT INTO park.dict_bed (id, cn_name, en_name, type, description, create_time, update_time, create_by, update_by, version, deleted, extra, weight, tenant_id) VALUES ('1311532465234157569', '测试床4', 'single bed', 'yy', '测试摇号', null, 1601600242205, null, null, 0, 0, '', 0, '');


-- 楼房类型字典表
-- auto-generated definition
create table dict_building
(
    id          varchar(36)              not null comment 'id'
        primary key,
    cn_name     varchar(64)              null comment '楼房类型名称（中文）',
    en_name     varchar(64)              null comment '楼房类型名称（英文）',
    description varchar(255)             null comment '描述',
    weight      int           default 0  null comment '权重',
    create_time bigint        default 0  null comment '创建时间',
    update_time bigint        default 0  null comment '更新时间',
    create_by   varchar(36)              null comment '创建者',
    update_by   varchar(36)              null comment '更新者',
    version     int           default 0  null comment '版本',
    deleted     int           default 0  null comment '是否有效 0-未删除 1-已删除',
    extra       varchar(1000) default '' null comment '额外信息',
    tenant_id   varchar(255)  default '' null comment '租户id'
)
    comment '楼房类型字典表'