package com.example.mybatispluscodegeneral.service.impl;

import com.example.mybatispluscodegeneral.entity.DictBuilding;
import com.example.mybatispluscodegeneral.mapper.DictBuildingMapper;
import com.example.mybatispluscodegeneral.service.IDictBuildingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
/**
 * @author YeZhiyue
 * Description 楼房类型字典表 服务实现类
 * Date 2020/09/26
 * Time 23:57
 * Mail 739153436@qq.com
 */
@Primary
@Service
public class DictBuildingServiceImpl extends ServiceImpl<DictBuildingMapper, DictBuilding> implements IDictBuildingService {

    @Resource
    private DictBuildingMapper dictBuildingMapper;

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
        .eq(!StringUtils.isBlank(pDictBuilding.getId()), DictBuilding::getId, pDictBuilding.getId())
             ${propertyNameToType.get(0).getType()}
             ${propertyNameToType.get(0).type}
             ${field.getType()}
                     .like(!Objects.isNull(pDictBuilding.get${field.propertyName}()), DictBuilding::get${field.propertyName}, pDictBuilding.get${field.propertyName}())
                     .like(!StringUtils.isBlank(pDictBuilding.get${field.propertyName}()), DictBuilding::get${field.propertyName}, pDictBuilding.get${field.propertyName}())
             ${propertyNameToType.get(0).getType()}
             ${propertyNameToType.get(0).type}
             ${field.getType()}
                     .like(!Objects.isNull(pDictBuilding.get${field.propertyName}()), DictBuilding::get${field.propertyName}, pDictBuilding.get${field.propertyName}())
                     .like(!StringUtils.isBlank(pDictBuilding.get${field.propertyName}()), DictBuilding::get${field.propertyName}, pDictBuilding.get${field.propertyName}())
             ${propertyNameToType.get(0).getType()}
             ${propertyNameToType.get(0).type}
             ${field.getType()}
                     .like(!Objects.isNull(pDictBuilding.get${field.propertyName}()), DictBuilding::get${field.propertyName}, pDictBuilding.get${field.propertyName}())
                     .like(!StringUtils.isBlank(pDictBuilding.get${field.propertyName}()), DictBuilding::get${field.propertyName}, pDictBuilding.get${field.propertyName}())
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
