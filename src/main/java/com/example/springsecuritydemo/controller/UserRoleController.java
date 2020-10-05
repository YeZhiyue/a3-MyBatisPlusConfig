package com.example.springsecuritydemo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springsecuritydemo.entity.UserRole;
import com.example.springsecuritydemo.service.IUserRoleService;
import com.example.springsecuritydemo.utils.resultbean.ErrorCodeInfo;
import com.example.springsecuritydemo.utils.resultbean.ResultBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author YeZhiyue
 * Description 角色用户关联表 服务实现类
 * Date 2020/10/04
 * Time 14:39
 * Mail 739153436@qq.com
 */
@RestController
@RequestMapping("/security/userRole")
public class UserRoleController {

    @Resource
    private IUserRoleService iUserRoleService;

// ===================== 管理员接口 ====================
// ======== 通常需要添加权限验证
// ======== 2020/10/04 14:39

    /**
     * @description 新增
     * @author YeZhiyue
     * @email 739153436@qq.com
     * @date 2020/10/04 14:39
     */
    @ApiOperation("新增")
    @PutMapping("/admin/insert")
    public ResultBean<Boolean> adminInsert(@RequestBody List<UserRole> pUserRoleList) {
        return ResultBean.restResult(iUserRoleService.adminInsert(pUserRoleList), ErrorCodeInfo.CREATED);
    }

    /**
     * @description 删除
     * @author YeZhiyue
     * @email 739153436@qq.com
     * @date 2020/10/04 14:39
     */
    @ApiOperation("删除")
    @DeleteMapping("/admin/delete")
    public ResultBean<Boolean> adminDelete(@NotEmpty(message = "删除的id列表不能为空") @RequestBody List<String> pIdList) {
        return ResultBean.restResult(iUserRoleService.adminDelete(pIdList), ErrorCodeInfo.NO_CONTENT);
    }

    /**
     * @description 分页查询
     * @author YeZhiyue
     * @email 739153436@qq.com
     * @date 2020/10/04 14:39
     */
    @ApiOperation("分页查询")
    @GetMapping("/admin/page")
    public ResultBean<Page<UserRole>> adminPage(@Min(value = -1, message = "页码过小") int pPageNum, int pPageSize, @RequestBody UserRole pUserRole) {
        return ResultBean.restResult(iUserRoleService.adminPage(pPageNum, pPageSize, pUserRole), ErrorCodeInfo.OK);
    }

    /**
     * @description 更新
     * @author YeZhiyue
     * @email 739153436@qq.com
     * @date 2020/10/04 14:39
     */
    @ApiOperation("更新")
    @PostMapping("/admin/update")
    public ResultBean<Boolean> adminUpdate(@Validated(value = UserRole.Update.class) @RequestBody List<UserRole> pUserRoleList) {
        return ResultBean.restResult(iUserRoleService.adminUpdate(pUserRoleList), ErrorCodeInfo.OK);
    }

// ===================== 用户接口 ====================
// ======== 可以设置权限等级或者不设置权限
// ======== 2020/10/04 14:39


// ===================== 预留接口 ====================
// ======== 代码生成的时候添加的预留接口
// ======== 2020/10/04 14:39


    /**
     * @description 预留接口
     */
    public ResultBean<Object> reserved1() {
        return ResultBean.restResult(iUserRoleService.reserved1(), ErrorCodeInfo.OK);
    }

    /**
     * @description 预留接口
     */
    public ResultBean<Object> reserved2() {
        return ResultBean.restResult(iUserRoleService.reserved2(), ErrorCodeInfo.OK);
    }

    /**
     * @description 预留接口
     */
    public ResultBean<Object> reserved3() {
        return ResultBean.restResult(iUserRoleService.reserved3(), ErrorCodeInfo.OK);
    }

    /**
     * @description 预留接口
     */
    public ResultBean<Object> reserved4() {
        return ResultBean.restResult(iUserRoleService.reserved4(), ErrorCodeInfo.OK);
    }
}

