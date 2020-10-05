package com.example.springsecuritydemo.config.security.util;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author shuang.kou
 * @description 获取当前请求的用户
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CurrentUserUtils {

    @Resource
    private IUserService iUserService;

    public User getCurrentUser() {
        return iUserService.getOne(Wrappers.lambdaQuery(User.class)
                .eq(User::getUserName, getCurrentUserName()));
    }

    private static String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
}
