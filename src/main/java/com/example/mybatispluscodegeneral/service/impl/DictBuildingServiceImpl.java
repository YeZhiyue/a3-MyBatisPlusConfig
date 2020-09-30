package com.example.mybatispluscodegeneral.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatispluscodegeneral.entity.DictBuilding;
import com.example.mybatispluscodegeneral.mapper.DictBuildingMapper;
import com.example.mybatispluscodegeneral.service.IDictBuildingService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
/**
 * @author YeZhiyue
 * Description 楼房类型字典表 服务实现类
 * Date 2020/09/30
 * Time 20:56
 * Mail 739153436@qq.com
 */
@Primary
@Service
@Transactional
public class DictBuildingServiceImpl extends ServiceImpl<DictBuildingMapper, DictBuilding> implements IDictBuildingService {

    @Resource
    private DictBuildingMapper DictBuildingMapper;

    // ===================== 管理员接口 ====================
    // ======== 通常需要添加权限验证
    // ======== 2020/9/26 14:12

    @Override
    public Boolean adminInsert(List<DictBuilding> pDictBuildingList) {
      return saveBatch(pDictBuildingList);
    }

    @Override
    public Boolean adminDelete(List<String> pIdList) {
        return removeByIds(pIdList);
    }

    @Override
    public Page<DictBuilding> adminPage(int pPageNum, int pPageSize, DictBuilding pDictBuilding) {
        return page(new Page<DictBuilding>(pPageNum, pPageSize), Wrappers.lambdaQuery(pDictBuilding)
        .like(!StringUtils.isBlank(pDictBuilding.getCnName()), DictBuilding::getCnName, pDictBuilding.getCnName())
.like(!StringUtils.isBlank(pDictBuilding.getEnName()), DictBuilding::getEnName, pDictBuilding.getEnName())
.like(!StringUtils.isBlank(pDictBuilding.getDescription()), DictBuilding::getDescription, pDictBuilding.getDescription())
.eq(!Objects.isNull(pDictBuilding.getId()), DictBuilding::getId, pDictBuilding.getId())

        .orderByDesc(DictBuilding::getCreateTime)
        );
    }

    @Override
    public Boolean adminUpdate(List<DictBuilding> pDictBuildingList) {
         return updateBatchById(pDictBuildingList);
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
