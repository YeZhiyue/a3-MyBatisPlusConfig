package com.example.springsecuritydemo.exception.security;

import com.example.springsecuritydemo.exception.BaseException;
import com.example.springsecuritydemo.exception.ErrorCode;

import java.util.Map;

/**
 * @author shuang.kou
 */
public class UserNameAlreadyExistException extends BaseException {

    public UserNameAlreadyExistException(Map<String, Object> data) {
        super(ErrorCode.USER_NAME_ALREADY_EXIST, data);
    }
}
