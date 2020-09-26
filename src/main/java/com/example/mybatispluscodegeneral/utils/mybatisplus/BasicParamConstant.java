package com.example.mybatispluscodegeneral.utils.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;

/**
 * @author 叶之越
 * Description
 * Date 2020/9/23
 * Time 12:42
 * Mail 739153436@qq.com
 */
public interface BasicParamConstant {
    /**
     * 作者
     */
    String AUTHOR = "YeZhiyue";
    String EMAIL = "739153436@qq.com";
    /**
     * 生成的实体类忽略表前缀: 不需要则置空
     */
    String ENTITY_IGNORE_PREFIX = "info_";
    /**
     * 表名
     */
    String[] TABLES = {
//            "dict_bed",
            "dict_building"
    };

    /**
     * 实体类的父类Entity
     */
    String SUPER_ENTITY_CLASS = "com.example.mybatispluscodegeneral.entity.basic.BaseModel";

    /**
     * 基础父类继承字段
     */
    String SUPER_ENTITY_COLUMNS[] = {
            "id",
            "weight",
            "create_time",
            "update_time",
            "create_by",
            "update_by",
            "deleted",
            "version",
            "extra",
            "tenant_id"
    };

    /**
     * 输出基础路径
     */
    String PARENT_PACKAGE_PATH = "/com/example/mybatispluscodegeneral";
    String PRE_MODULE = "";
    // 配置你包路径下的子模块(例如：com.example.mybatispluscodegeneral.test)
    // 示例："test";
    String AFTER_MODULE = "";

    /**
     * 数据库
     */
    String username = "root";
    String password = "root";
    String url = "jdbc:mysql://59.110.213.92:3307/park";
    DbType DB_TYPE = DbType.MYSQL;
    String driverClassName = "com.mysql.cj.jdbc.Driver";
}
