create table store_ccat
(
    vaccine_2 varchar(32)  default '0' null comment '狂犬疫苗是否接种0：是 1：否',
    vaccine_1 varchar(255) default '0' null comment '多联疫苗接种情况：接种针数',
    health    varchar(32)  default '0' null comment '猫咪健康状况0:健康，1:生病',
    age       varchar(255)             null,
    status    varchar(255)             null comment '0:寄养1：流浪2：居家',
    sex       varchar(255)             null,
    name      varchar(255)             null,
    id        varchar(255)             not null
        primary key,
    flag      varchar(255)             null comment '0-本地喵，1-外地喵',
    picture   varchar(255)             null,
    master    varchar(255)             null,
    is_work   varchar(255)             null comment '0:休息1：上岗',
    hobby     varchar(255)             null,
    habit     varchar(255)             null
);

create table store_meal
(
    time   timestamp   null,
    userId varchar(32) null,
    id     varchar(32) null,
    meal   varchar(32) null,
    price  varchar(32) null
);

create table sys_files
(
    id        varchar(30)              not null,
    name      varchar(255)             null,
    type      varchar(30)              null,
    is_delete varchar(255) default '0' null comment '逻辑删除',
    enable    int          default 1   null,
    md5       varchar(255)             null,
    size      bigint                   null,
    url       varchar(255)             null
);

create table sys_icon
(
    name  varchar(255) null comment '名称',
    type  varchar(255) null comment '类型',
    value varchar(255) not null comment '内容'
        primary key
);

create table sys_menu
(
    sort        int         default 99   null,
    state       varchar(2)  default '1'  not null comment '可用状态：0-删除，1-可用',
    pid         varchar(30) default '-1' null comment '父菜单id',
    id          varchar(30)              not null
        primary key,
    path        varchar(255)             null comment '菜单路径',
    icon        varchar(255)             null comment '菜单图标',
    name        varchar(30)              null,
    description varchar(255)             null comment '菜单描述',
    page_path   varchar(255)             null
)
    comment '菜单';

create table sys_role
(
    flag        varchar(2)   default '0' null comment '1.系统管理员2.店员3.顾客',
    name        varchar(255)             null,
    description varchar(255) default '0' null,
    id          varchar(30)              not null
        primary key
);

create table sys_role_menu
(
    menu_id varchar(255) null comment '菜单id',
    role_id varchar(255) not null comment '角色id
'
);

create table sys_user
(
    status     varchar(2)   default '1'               null,
    modifyTime timestamp    default CURRENT_TIMESTAMP null,
    avatar_url varchar(100)                           null,
    id         varchar(30)                            not null comment 'ID'
        primary key,
    username   varchar(20)                            not null,
    password   varchar(30)                            null,
    address    varchar(30)                            null,
    email      varchar(30)                            null,
    nickname   varchar(30)                            null,
    role       varchar(255) default '3'               null comment '1.系统管理员2.店员3.顾客',
    sex        varchar(255)                           null,
    phone      varchar(30)                            null
);

