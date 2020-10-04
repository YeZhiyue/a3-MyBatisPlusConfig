package com.example.mybatispluscodegeneral.security.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.mybatispluscodegeneral.security.entity.JwtUser;
import com.example.mybatispluscodegeneral.security.entity.User;
import com.example.mybatispluscodegeneral.security.service.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author shuang.kou
 * @description UserDetailsService实现类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private IUserService iUserService;

    public UserDetailsServiceImpl(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String name) {
        User user = iUserService.getOne(Wrappers.lambdaQuery(User.class)
                .eq(User::getUserName, name), false
        );
        return new JwtUser(user);
    }
}
