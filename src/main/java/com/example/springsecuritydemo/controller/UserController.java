package com.example.springsecuritydemo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springsecuritydemo.entity.Role;
import com.example.springsecuritydemo.entity.User;
import com.example.springsecuritydemo.entity.UserRole;
import com.example.springsecuritydemo.config.security.entity.RoleEnum;
import com.example.springsecuritydemo.service.IRoleService;
import com.example.springsecuritydemo.service.IUserRoleService;
import com.example.springsecuritydemo.service.IUserService;
import com.example.springsecuritydemo.utils.resultbean.ErrorCodeInfo;
import com.example.springsecuritydemo.utils.resultbean.ResultBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YeZhiyue
 * Description 用户表 服务实现类
 * Date 2020/10/04
 * Time 14:37
 * Mail 739153436@qq.com
 */
@RestController
@RequestMapping("/user")
public class UserController {

        @Resource
        private IUserService iUserService;

        @Resource
        private IUserRoleService iUserRoleService;

        @Resource
        private IRoleService iRoleService;

        @Resource
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        // ===================== 管理员接口 ====================
        // ======== 通常需要添加权限验证
        // ======== 2020/10/04 14:37

        /**
         * @description 新增
         * @author YeZhiyue
         * @email 739153436@qq.com
         * @date 2020/10/04 14:37
         */
        @ApiOperation("新增")
        @PutMapping("/admin/insert")
        public ResultBean<Boolean> adminInsert(@RequestBody List<User> pUserList) {
                return ResultBean.restResult(iUserService.adminInsert(pUserList), ErrorCodeInfo.CREATED);
        }

        /**
         * @description 删除
         * @author YeZhiyue
         * @email 739153436@qq.com
         * @date 2020/10/04 14:37
         */
        @ApiOperation("删除")
        @DeleteMapping("/admin/delete")
        public ResultBean<Boolean> adminDelete(@NotEmpty(message = "删除的id列表不能为空") @RequestBody List<String> pIdList) {
                return ResultBean.restResult(iUserService.adminDelete(pIdList), ErrorCodeInfo.NO_CONTENT);
        }

        /**
         * @description 分页查询
         * @author YeZhiyue
         * @email 739153436@qq.com
         * @date 2020/10/04 14:37
         */
        @ApiOperation("分页查询")
        @GetMapping("/admin/page")
        public ResultBean<Page<User>> adminPage(@Min(value = -1, message = "页码过小") int pPageNum, int pPageSize, @RequestBody User pUser) {
                return ResultBean.restResult(iUserService.adminPage(pPageNum, pPageSize, pUser), ErrorCodeInfo.OK);
        }

        /**
         * @description 更新
         * @author YeZhiyue
         * @email 739153436@qq.com
         * @date 2020/10/04 14:37
         */
        @ApiOperation("更新")
        @PostMapping("/admin/update")
        public ResultBean<Boolean> adminUpdate(@Validated(value = User.Update.class) @RequestBody List<User> pUserList) {
                return ResultBean.restResult(iUserService.adminUpdate(pUserList), ErrorCodeInfo.OK);
        }

        // ===================== 用户接口 ====================
        // ======== 可以设置权限等级或者不设置权限
        // ======== 2020/10/04 14:37

        @PostMapping("/sign-up")
        public ResultBean<Boolean> signUp(@RequestBody User user) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                iUserService.save(user);
                Role one = iRoleService.getOne(Wrappers.lambdaQuery(Role.class)
                        .eq(Role::getName, RoleEnum.USER.getName())
                );
                Role two = iRoleService.getOne(Wrappers.lambdaQuery(Role.class)
                        .eq(Role::getName, RoleEnum.MANAGER.getName())
                );

                List<UserRole> userRoleList = new ArrayList<>();
                UserRole userRole = new UserRole(user.getId(), one.getId());
                userRoleList.add(userRole);
                UserRole e = new UserRole(user.getId(), two.getId());
                userRoleList.add(e);
                iUserRoleService.saveBatch(userRoleList);
                return ResultBean.ok(true);
        }

        @GetMapping
        @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
        public ResultBean<Page<User>> getAllUsers(@Min(value = -1, message = "页码过小") int pPageNum, int pPageSize, @RequestBody User pUser) {
                return ResultBean.restResult(iUserService.adminPage(pPageNum, pPageSize, pUser), ErrorCodeInfo.OK);
        }


// ===================== 预留接口 ====================
// ======== 代码生成的时候添加的预留接口
// ======== 2020/10/04 14:37


        /**
         * @description 预留接口
         */
        public ResultBean<Object> reserved1() {
                return ResultBean.restResult(iUserService.reserved1(), ErrorCodeInfo.OK);
        }

        /**
         * @description 预留接口
         */
        public ResultBean<Object> reserved2() {
                return ResultBean.restResult(iUserService.reserved2(), ErrorCodeInfo.OK);
        }

        /**
         * @description 预留接口
         */
        public ResultBean<Object> reserved3() {
                return ResultBean.restResult(iUserService.reserved3(), ErrorCodeInfo.OK);
        }

        /**
         * @description 预留接口
         */
        public ResultBean<Object> reserved4() {
                return ResultBean.restResult(iUserService.reserved4(), ErrorCodeInfo.OK);
        }
}

