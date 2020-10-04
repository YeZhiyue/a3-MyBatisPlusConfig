package com.example.mybatispluscodegeneral.service;

import com.example.mybatispluscodegeneral.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @author YeZhiyue
 * Description 用户表 服务实现类
 * Date 2020/10/04
 * Time 09:48
 * Mail 739153436@qq.com
 */
public interface IUserService extends IService<User> {
        // ===================== 管理员接口 ====================
        // ======== 通常需要添加权限验证
        // ======== 2020/9/26 14:12

        Boolean adminInsert(List<User> pUserList);

        Boolean adminDelete(List<String> pIdList);

        Page<User> adminPage(int pPageNum, int pPageSize, User pUser);

        Boolean adminUpdate(List<User> pUserList);

        // ===================== 用户接口 ====================
        // ======== 可以设置权限等级或者不设置权限
        // ======== 2020/9/26 14:12


        // ===================== 预留接口 ====================
        // ======== 代码生成的时候添加的预留接口
        // ======== 2020/9/26 14:13

        Object reserved1();
        Object reserved2();
        Object reserved3();
        Object reserved4();
}
