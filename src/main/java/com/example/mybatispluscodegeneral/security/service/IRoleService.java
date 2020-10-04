package com.example.mybatispluscodegeneral.security.service;

import com.example.mybatispluscodegeneral.security.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author YeZhiyue
 * Description 角色类型表 服务实现类
 * Date 2020/10/04
 * Time 14:38
 * Mail 739153436@qq.com
 */
public interface IRoleService extends IService<Role> {
    // ===================== 管理员接口 ====================
    // ======== 通常需要添加权限验证
    // ======== 2020/9/26 14:12

    Boolean adminInsert(List<Role> pRoleList);

    Boolean adminDelete(List<String> pIdList);

    Page<Role> adminPage(int pPageNum, int pPageSize, Role pRole);

    Boolean adminUpdate(List<Role> pRoleList);

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
