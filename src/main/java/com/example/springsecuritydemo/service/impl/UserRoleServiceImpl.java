package com.example.springsecuritydemo.service.impl;

import com.example.springsecuritydemo.entity.UserRole;
import com.example.springsecuritydemo.mapper.UserRoleMapper;
import com.example.springsecuritydemo.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YeZhiyue
 * Description 角色用户关联表 服务实现类
 * Date 2020/10/04
 * Time 14:39
 * Mail 739153436@qq.com
 */
@Primary
@Service
@Transactional
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    // ===================== 管理员接口 ====================
    // ======== 通常需要添加权限验证
    // ======== 2020/9/26 14:12

    @Override
    public Boolean adminInsert(List<UserRole> pUserRoleList) {
        return saveBatch(pUserRoleList);
    }

    @Override
    public Boolean adminDelete(List<String> pIdList) {
        return removeByIds(pIdList);
    }

    @Override
    public Page<UserRole> adminPage(int pPageNum, int pPageSize, UserRole pUserRole) {
        return page(new Page(pPageNum, pPageSize), Wrappers.lambdaQuery(pUserRole)
                .like(!StringUtils.isBlank(pUserRole.getUserId()), UserRole::getUserId, pUserRole.getUserId())
                .like(!StringUtils.isBlank(pUserRole.getRoleId()), UserRole::getRoleId, pUserRole.getRoleId())
                .eq(!StringUtils.isBlank(pUserRole.getId()), UserRole::getId, pUserRole.getId())

                .orderByDesc(UserRole::getCreateTime)
        );
    }

    @Override
    public Boolean adminUpdate(List<UserRole> pUserRoleList) {
        return updateBatchById(pUserRoleList);
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
