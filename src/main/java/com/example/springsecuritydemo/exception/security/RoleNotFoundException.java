package com.example.springsecuritydemo.exception.security;

import com.example.springsecuritydemo.exception.BaseException;
import com.example.springsecuritydemo.exception.ErrorCode;

import java.util.Map;

/**
 * @author shuang.kou
 */
public class RoleNotFoundException extends BaseException {
    public RoleNotFoundException(Map<String, Object> data) {
        super(ErrorCode.Role_NOT_FOUND, data);
    }
}
