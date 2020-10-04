package com.example.mybatispluscodegeneral.security.service.impl;

import com.example.mybatispluscodegeneral.security.entity.User;
import com.example.mybatispluscodegeneral.security.mapper.UserMapper;
import com.example.mybatispluscodegeneral.security.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
/**
 * @author YeZhiyue
 * Description 用户表 服务实现类
 * Date 2020/10/04
 * Time 14:37
 * Mail 739153436@qq.com
 */
@Primary
@Service
@Transactional
        public class UserServiceImpl extends ServiceImpl<UserMapper, User>implements IUserService {

        @Resource
        private UserMapper userMapper;

        // ===================== 管理员接口 ====================
        // ======== 通常需要添加权限验证
        // ======== 2020/9/26 14:12

        @Override
        public Boolean adminInsert(List<User>pUserList){
        return saveBatch(pUserList);
        }

        @Override
        public Boolean adminDelete(List<String>pIdList){
        return removeByIds(pIdList);
        }

        @Override
        public Page<User>adminPage(int pPageNum,int pPageSize, User pUser){
        return page(new Page(pPageNum,pPageSize),Wrappers.lambdaQuery(pUser)
            .like(!StringUtils.isBlank(pUser.getUserName()), User::getUserName, pUser.getUserName())
.like(!StringUtils.isBlank(pUser.getFullName()), User::getFullName, pUser.getFullName())
.like(!StringUtils.isBlank(pUser.getPassword()), User::getPassword, pUser.getPassword())
.like(!Objects.isNull(pUser.getEnabled()), User::getEnabled, pUser.getEnabled())
.eq(!StringUtils.isBlank(pUser.getId()), User::getId, pUser.getId())

        .orderByDesc(User::getCreateTime)
        );
        }

        @Override
        public Boolean adminUpdate(List<User>pUserList){
        return updateBatchById(pUserList);
        }

        // ===================== 用户接口 ====================
        // ======== 可以设置权限等级或者不设置权限
        // ======== 2020/9/26 14:12


        // ===================== 预留接口 ====================
        // ======== 代码生成的时候添加的预留接口
        // ======== 2020/9/26 14:13

        @Override
        public Object reserved1(){
        return null;
        }

        @Override
        public Object reserved2(){
        return null;
        }

        @Override
        public Object reserved3(){
        return null;
        }

        @Override
        public Object reserved4(){
        return null;
        }
        }