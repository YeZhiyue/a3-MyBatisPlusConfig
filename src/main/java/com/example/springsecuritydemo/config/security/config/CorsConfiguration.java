package com.example.springsecuritydemo.config.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 叶之越
 * Description 跨域设置，可以让前端获取 token
 * Date 2020/9/24
 * Time 7:52
 * Mail 739153436@qq.com
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    /**
     * @description 跨域设置
     * @author 叶之越
     * @email 739153436@qq.com
     * @date 2020/10/5 8:55
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                //暴露header中的其他属性给客户端应用程序
                //如果不设置这个属性前端无法通过response header获取到Authorization也就是token
                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }

    /**
     * @description 视图映射配置
     * @author 叶之越
     * @email 739153436@qq.com
     * @date 2020/10/5 8:55
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
    }
}