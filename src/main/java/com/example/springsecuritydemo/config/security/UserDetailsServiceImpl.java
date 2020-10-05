package com.example.springsecuritydemo.config.security;

import com.example.springsecuritydemo.config.security.entity.JwtUser;
import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.service.IUserService;
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
        User user = iUserService.getUserWithRoles(name);
        return new JwtUser(user);
    }
}
