package com.example.mybatispluscodegeneral.security.service.impl;

import com.example.mybatispluscodegeneral.security.entity.Role;
import com.example.mybatispluscodegeneral.security.mapper.RoleMapper;
import com.example.mybatispluscodegeneral.security.service.IRoleService;
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
 * Description 角色类型表 服务实现类
 * Date 2020/10/04
 * Time 14:38
 * Mail 739153436@qq.com
 */
@Primary
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMapper roleMapper;

    // ===================== 管理员接口 ====================
    // ======== 通常需要添加权限验证
    // ======== 2020/9/26 14:12

    @Override
    public Boolean adminInsert(List<Role> pRoleList) {
        return saveBatch(pRoleList);
    }

    @Override
    public Boolean adminDelete(List<String> pIdList) {
        return removeByIds(pIdList);
    }

    @Override
    public Page<Role> adminPage(int pPageNum, int pPageSize, Role pRole) {
        return page(new Page(pPageNum, pPageSize), Wrappers.lambdaQuery(pRole)
                .like(!StringUtils.isBlank(pRole.getName()), Role::getName, pRole.getName())
                .like(!StringUtils.isBlank(pRole.getDescription()), Role::getDescription, pRole.getDescription())
                .eq(!StringUtils.isBlank(pRole.getId()), Role::getId, pRole.getId())

                .orderByDesc(Role::getCreateTime)
        );
    }

    @Override
    public Boolean adminUpdate(List<Role> pRoleList) {
        return updateBatchById(pRoleList);
    }

    // ===================== 用户接口 ====================
    // ======== 可以设置权限等级或者不设置权限
    // ======== 2020/9/26 14:12


    // ===================== 预留接口 ====================
    // ======== 代码生成的时候添加的预留接口
    // ======== 2020/9/26 14:13

    @Override
    public Object reserved1() {
        return null;
    }

    @Override
    public Object reserved2() {
        return null;
    }

    @Override
    public Object reserved3() {
        return null;
    }

    @Override
    public Object reserved4() {
        return null;
    }
}
