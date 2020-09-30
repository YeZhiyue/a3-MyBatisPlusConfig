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
     * 父包名路径(文件输出路径,也是导包的路径)
     */
    // 各层包名 实体类、Dao、XML、Service、Controller
    String ENTITY_PATH = "/entity/";
    String MAPPER_PATH = "/mapper/";
    String XML_PATH = "/resources/mapper/";
    String SERVICE_PATH = "/service/";
    String SERVICE_IMPL_PATH = "/service/impl/";
    String CONTROLLER_PATH = "/controller/";

    /**
     * mapper.xml输出模块路径(需要注意放置的位置:默认从模块/src/main下开始)
     */
    String XML_OUTPUT_MODULE = "/"+AFTER_MODULE;
    String XML_OUTPUT_PATH = "/src/main" + XML_PATH;
    /**
     * IService.java, serviceImpl.java输出模块路径
     */
    String SERVICE_OUTPUT_MODULE = "/"+AFTER_MODULE;
    String SERVICE_OUTPUT_PATH = "/src/main/java" + PARENT_PACKAGE_PATH + SERVICE_OUTPUT_MODULE + SERVICE_PATH;
    String SERVICE_IMPL_OUTPUT_PATH = "/src/main/java" + PARENT_PACKAGE_PATH + SERVICE_OUTPUT_MODULE + SERVICE_IMPL_PATH;
    /**
     * Controller.java输出模块路径
     */
    String CONTROLLER_OUTPUT_MODULE = "/"+AFTER_MODULE;
    String CONTROLLER_OUTPUT_PATH = "/src/main/java" + PARENT_PACKAGE_PATH + CONTROLLER_OUTPUT_MODULE + CONTROLLER_PATH;
    /**
     * Entity.java, Mapper.java, Mapper.xml输出模块路径
     */
    String DAO_OUTPUT_MODULE = "/"+AFTER_MODULE;
    String ENTITY_OUTPUT_PATH = "/src/main/java" + PARENT_PACKAGE_PATH + DAO_OUTPUT_MODULE + ENTITY_PATH;
    String MAPPER_OUTPUT_PATH = "/src/main/java" + PARENT_PACKAGE_PATH + DAO_OUTPUT_MODULE + MAPPER_PATH;
}
