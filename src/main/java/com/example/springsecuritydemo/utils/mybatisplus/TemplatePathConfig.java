package com.example.springsecuritydemo.utils.mybatisplus;

/**
 * @author 叶之越
 * Description
 * Date 2020/9/23
 * Time 12:42
 * Mail 739153436@qq.com
 */
public interface TemplatePathConfig extends BasicParamConfig {

    /**
     * entity输出模板
     */
    String ENTITY_TEMPLATE = "templates/entity.java.vm";
    /**
     * mapper.xml输出模板
     */
    String XML_TEMPLATE = "templates/mapper.xml.vm";
    /**
     * mapper.java输出模板
     */
    String MAPPER_TEMPLATE = "templates/mapper.java.vm";
    /**
     * service输出模板
     */
    String SERVICE_TEMPLATE = "templates/service.java.vm";
    /**
     * serviceImpl输出模板
     */
    String SERVICE_IMPL_TEMPLATE = "templates/serviceImpl.java.vm";
    /**
     * controller输出模板
     */
    String CONTROLLER_TEMPLATE = "templates/controller.java.vm";
}
