
create table admin
(
    id           int auto_increment comment 'id'
        primary key,
    username     varchar(20)  not null comment '用户名',
    password     varchar(20)  null comment '密码',
    role         varchar(10)  null comment '管理角色',
    create_time  datetime     not null comment '创建时间',
    user_address varchar(100) null comment '用户钱包地址',
    remark       varchar(500) null comment '管理员备注'
)
    collate = utf8mb4_unicode_ci;

create table config_experience_fee
(
    id                          int auto_increment
        primary key,
    current_address             varchar(200)             null comment '当前用户地址',
    current_id                  int                      null comment '当前用户id',
    parent_address              varchar(200) default '0' null comment '上级地址',
    parent_id                   int          default 0   null comment '上级id',
    create_time                 datetime                 null comment '创建时间',
    is_configure_experience_fee tinyint(1)   default 0   null comment '是否配置体验金',
    subordinate_id              int                      null comment '下级id',
    subordinate_address         varchar(200)             null comment '下级地址'
);

create table configuration
(
    id                 int auto_increment
        primary key,
    minimum_quantity   double     default 0 null comment '最低质押数量',
    external_link_name varchar(100)         null comment 'tab名称',
    external_link      varchar(200)         null comment 'tab外链',
    system_message     varchar(500)         null comment '系统消息',
    is_notice          tinyint(1) default 1 null comment '系统消息是否开启',
    flow_mining_list   varchar(500)         null comment '参与流动性挖矿的余额范围及相应的收益率',
    period_list        varchar(500)         null comment '质押周期对应的利率列表',
    is_airdrop         tinyint(1) default 1 null comment '空投开关'
)
    collate = utf8mb4_unicode_ci;

create table experience_gold_record
(
    id              int auto_increment
        primary key,
    user_id         int                     null comment '用户id',
    user_address    varchar(200)            null comment '用户地址',
    experience_time int         default 0   null comment '剩余体验时长',
    amount          double      default 0   null comment '体验金',
    profit          varchar(10) default '0' null comment '利率',
    income          double      default 0   null comment '收益',
    currency_type   varchar(10)             null comment '币种',
    create_time     datetime                null comment '创建时间',
    config_time     datetime                null comment '配置体验金时间',
    profit_switch   tinyint(1)              null comment '体验金收益开关'
);

create table flow_record
(
    id            int auto_increment comment 'id'
        primary key,
    user_id       int           null comment '用户id',
    user_address  varchar(100)  null comment '用户地址',
    amount        double        null comment '账号余额',
    time          int default 0 null comment '对比次数',
    create_time   datetime      null comment '创建时间',
    update_time   datetime      null comment '修改时间',
    period varchar (10) null comment '利率',
    currency_type varchar(10)   null comment '币种类型'
);

create table pledge_record
(
    id            int auto_increment comment 'id'
        primary key,
    user_id       int                            not null comment '用户id',
    user_address  varchar(100)                   not null comment '用户地址',
    create_time   datetime                       not null comment '创建时间',
    pledge_hash   varchar(100)                   null comment '质押哈希',
    amount        double                         not null comment '质押金额',
    currency_type varchar(20)                    null comment '币种类型',
    is_reward     tinyint(1)  default 1          null comment '质押收益开关',
    profit        varchar(10)                    not null comment '利率',
    period varchar (10) null comment '质押周期时长',
    stop_time     datetime                       not null comment '质押结束时间',
    status        varchar(20) default 'PLEDGING' null comment '质押状态',
    income        double      default 0          null comment '收益',
    is_virtual    tinyint(1)  default 0          null comment '是否为虚拟记录'
);

create table statistics
(
    id                                   int auto_increment
        primary key,
    user_id                              int              not null comment '用户id',
    user_address                         varchar(100)     not null comment '用户钱包地址',
    total_pledge                         double default 0 null comment '总质押',
    unwithdraw_pledge                    double default 0 not null comment '未提取的本金',
    total_pledge_reward                  double default 0 not null comment '总的质押收益',
    unreceived_pledge_reward             double default 0 not null comment '未领取的质押收益',
    total_flow_reward                    double default 0 not null comment '流动性挖矿总收益',
    unreceived_flow_reward               double default 0 not null comment '未领取的流动性收益',
    total_experience_reward              double default 0 not null comment '总的体验金收益',
    unreceived_experience_reward         double default 0 not null comment '未领取的体验金收益',
    virtual_total_pledge                 double default 0 null comment '虚拟总质押',
    virtual_unwithdraw_pledge            double default 0 null comment '虚拟未提取本金',
    virtual_total_pledge_reward          double default 0 null comment '虚拟总质押收益',
    virtual_unreceived_pledge_reward     double default 0 null comment '虚拟未领取的质押收益',
    virtual_flow_amount                  double default 0 null comment '虚拟流动金额',
    virtual_total_flow_reward            double default 0 null comment '虚拟总流动收益',
    virtual_unreceived_flow_reward       double default 0 null comment '虚拟未领取流动性收益',
    virtual_experience_amount            double default 0 null comment '虚拟在生效的体验金',
    virtual_total_experience_reward      double default 0 null comment '虚拟同体验金收益',
    virtual_unreceived_experience_reward double default 0 null comment '虚拟未领取体验金收益',
    constraint statistics_user_address_uindex
        unique (user_address)
)
    collate = utf8mb4_unicode_ci;

create table user
(
    id                    int auto_increment
        primary key,
    user_address          varchar(200)             not null comment '用户钱包地址',
    create_time           datetime                 not null comment '创建时间',
    superior_user_address varchar(200) default '0' null comment '上级地址',
    is_flow_reward        tinyint(1)   default 1   null comment '流动性收益开关',
    is_withdrawal_auth    tinyint(1)   default 0   null comment '提现需授权开关',
    superior_id           int          default 0   null comment '上级id',
    remark                varchar(500)             null comment '备注',
    root_id               int          default 0   null comment '根代理id',
    root_address          varchar(200) default '0' null comment '根代理地址',
    has_flow              tinyint(1)   default 0   null comment '是否参与流动性挖矿',
    system_message        varchar(500)             null comment '系统消息',
    is_notice             tinyint(1)   default 0   null comment '是否通知',
    email                 varchar(100)             null comment '空投email',
    has_email             tinyint(1)   default 0   null comment '是否已经有领取空投'
)
    collate = utf8mb4_unicode_ci;

create table withdraw_record
(
    id               int auto_increment comment 'id'
        primary key,
    amount           double           null comment '提现金额',
    apply_time       datetime         null comment '申请时间',
    status           varchar(52)      null comment '提现状态',
    play_hash        varchar(200)     null comment '打款hash',
    play_time        datetime         null comment '打款时间',
    withdrew_type    varchar(50)      null comment '提现类型',
    user_id          int              null comment '用户id',
    user_address     varchar(200)     null comment '用户地址',
    pledge_record_id int              null comment '质押记录id',
    virtual_amount   double default 0 null comment '虚拟金额'
);

