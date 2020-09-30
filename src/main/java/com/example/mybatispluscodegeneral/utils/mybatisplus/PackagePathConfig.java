package com.example.mybatispluscodegeneral.utils.mybatisplus;

/**
 * @author 叶之越
 * Description
 * Date 2020/9/23
 * Time 12:42
 * Mail 739153436@qq.com
 */
public interface PackagePathConfig extends BasicParamConfig {

    /**
     * 通用的路径输出命名
     */
    // 各层包名 实体类、Dao、XML、Service、Controller
    String ENTITY_PATH = "/entity/";
    String MAPPER_PATH = "/mapper/";
    String XML_PATH = "/resources/mapper/";
    String SERVICE_PATH = "/service/";
    String SERVICE_IMPL_PATH = "/service/impl/";
    String CONTROLLER_PATH = "/controller/";

    /**
     * 分布式通常和Entity不在同一个模块下面
     */
    // Controller.java输出模块路径
    String CONTROLLER_OUTPUT_PATH = SERVICE_PRE_MODULE + "/src/main/java" + PARENT_PACKAGE_PATH + AFTER_MODULE + CONTROLLER_PATH;
    // IService.java(/api/src/main/java/com/example/demo/after/service)
    String SERVICE_OUTPUT_PATH = SERVICE_PRE_MODULE + "/src/main/java" + PARENT_PACKAGE_PATH + AFTER_MODULE + SERVICE_PATH;
    // ServiceImpl.java输出模块路径
    String SERVICE_IMPL_OUTPUT_PATH = SERVICE_PRE_MODULE + "/src/main/java" + PARENT_PACKAGE_PATH + AFTER_MODULE + SERVICE_IMPL_PATH;
    // Mapper.java
    String MAPPER_OUTPUT_PATH = SERVICE_PRE_MODULE + "/src/main/java" + PARENT_PACKAGE_PATH + AFTER_MODULE + MAPPER_PATH;
    // Mapper.xml输出模块路径(需要注意放置的位置:默认从模块/src/main下开始)
    String XML_OUTPUT_PATH = SERVICE_PRE_MODULE + "/src/main" + XML_PATH;

    /**
     * 分布式通常和业务模块区分
     */
    // Entity.java
    String ENTITY_OUTPUT_PATH = API_PRE_MODULE + "/src/main/java" + PARENT_PACKAGE_PATH + AFTER_MODULE + ENTITY_PATH;
}
