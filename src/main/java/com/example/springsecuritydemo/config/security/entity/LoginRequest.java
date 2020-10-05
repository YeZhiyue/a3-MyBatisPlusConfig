package com.example.springsecuritydemo.config.security.entity;

import lombok.Data;


/**
 * @author shuang.kou
 * @description 用户登录请求DTO
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
    private Boolean rememberMe;
}
