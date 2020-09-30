package com.example.mybatispluscodegeneral.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatispluscodegeneral.entity.DictBuilding;
import com.example.mybatispluscodegeneral.service.IDictBuildingService;
import com.example.mybatispluscodegeneral.utils.resultbean.ErrorCodeInfo;
import com.example.mybatispluscodegeneral.utils.resultbean.ResultBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author YeZhiyue
 * Description 楼房类型字典表 服务实现类
 * Date 2020/09/30
 * Time 20:56
 * Mail 739153436@qq.com
 */
@RestController
@RequestMapping("/dictBuilding")
public class DictBuildingController {
        
        @Resource
        private IDictBuildingService iDictBuildingService;
        
        // ===================== 管理员接口 ====================
        // ======== 通常需要添加权限验证
        // ======== 2020/09/30 20:56
        
        /**
         * @description 新增
         * @author YeZhiyue
         * @email 739153436@qq.com
         * @date 2020/09/30 20:56
         */
        @ApiOperation("新增")
        @PutMapping("/admin/insert")
        public ResultBean<Boolean> adminInsert(@RequestBody List<DictBuilding> pDictBuildingList) {
                return ResultBean.restResult(iDictBuildingService.adminInsert(pDictBuildingList), ErrorCodeInfo.CREATED);
        }

        /**
         * @description 删除
         * @author YeZhiyue
         * @email 739153436@qq.com
         * @date 2020/09/30 20:56
         */
        @ApiOperation("删除")
        @DeleteMapping("/admin/delete")
        public ResultBean<Boolean> adminDelete(@NotEmpty(message = "删除的id列表不能为空") @RequestBody List<String> pIdList) {
                return ResultBean.restResult(iDictBuildingService.adminDelete(pIdList), ErrorCodeInfo.NO_CONTENT);
        }

        /**
         * @description 分页查询
         * @author YeZhiyue
         * @email 739153436@qq.com
         * @date 2020/09/30 20:56
         */
        @ApiOperation("分页查询")
        @GetMapping("/admin/page")
        public ResultBean<Page<DictBuilding>> adminPage(@Min(value = -1 ,message = "页码过小") int pPageNum, int pPageSize, @RequestBody DictBuilding pDictBuilding) {
                return ResultBean.restResult(iDictBuildingService.adminPage(pPageNum,pPageSize,pDictBuilding), ErrorCodeInfo.OK);
        }

        /**
         * @description 更新
         * @author YeZhiyue
         * @email 739153436@qq.com
         * @date 2020/09/30 20:56
         */
        @ApiOperation("更新")
        @PostMapping("/admin/update")
        public ResultBean<Boolean> adminUpdate(@Validated(value = DictBuilding.Update.class) @RequestBody List<DictBuilding> pDictBuildingList) {
                return ResultBean.restResult(iDictBuildingService.adminUpdate(pDictBuildingList), ErrorCodeInfo.OK);
        }
        
        // ===================== 用户接口 ====================
        // ======== 可以设置权限等级或者不设置权限
        // ======== 2020/09/30 20:56
        
        
        // ===================== 预留接口 ====================
        // ======== 代码生成的时候添加的预留接口
        // ======== 2020/09/30 20:56


        /**
         * @description 预留接口
         */
        public ResultBean<Object> reserved1() {
                return ResultBean.restResult(iDictBuildingService.reserved1(), ErrorCodeInfo.OK);
        }

        /**
         * @description 预留接口
         */
        public ResultBean<Object> reserved2() {
                return ResultBean.restResult(iDictBuildingService.reserved2(), ErrorCodeInfo.OK);
        }

        /**
         * @description 预留接口
         */
        public ResultBean<Object> reserved3() {
                return ResultBean.restResult(iDictBuildingService.reserved3(), ErrorCodeInfo.OK);
        }

        /**
         * @description 预留接口
         */
        public ResultBean<Object> reserved4() {
                return ResultBean.restResult(iDictBuildingService.reserved4(), ErrorCodeInfo.OK);
        }
}

