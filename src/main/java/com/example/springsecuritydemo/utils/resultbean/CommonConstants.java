package com.example.springsecuritydemo.utils.resultbean;

/**
 * @Auther: wangzy
 * @Date: 2019/3/31 11:34
 * @Description:
 */
public interface CommonConstants {

    /**
     * header 中租户ID
     */
    String TENANT_ID = "TENANT_ID";

    /**
     * 租户ID
     */
    Integer TENANT_ID_1 = 1;

    /**
     * 路由存放
     */
    String ROUTE_KEY = "gateway_route_key_bl";

    /**
     * 成功标记
     */
    Integer SUCCESS = 200;
    /**
     * 失败标记
     */
    Integer FAIL = 500;

    /**
     * 验证码前缀
     */
    String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY_";

    /**
     * 正常
     */
    Integer STATUS_NORMAL = 0;

    /**
     * 锁定
     */
    String STATUS_LOCK = "9";

    /**
     * 编码
     */
    String UTF8 = "UTF-8";

    /**
     * Ajax请求头名称
     */
    String AJAX_HEADER_NAME = "X-Requested-With";

    /**
     * Ajax请求头内容
     */
    String AJAX_HEADER_VALUE = "XMLHttpRequest";
}
