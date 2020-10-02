package com.example.mybatispluscodegeneral.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatispluscodegeneral.entity.DictBed;
import com.example.mybatispluscodegeneral.mapper.DictBedMapper;
import com.example.mybatispluscodegeneral.service.IDictBedService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YeZhiyue
 * Description 床类型字典表 服务实现类
 * Date 2020/10/01
 * Time 16:35
 * Mail 739153436@qq.com
 */
@Primary
@Service
@Transactional
public class DictBedServiceImpl extends ServiceImpl<DictBedMapper, DictBed> implements IDictBedService {

    @Resource
    private DictBedMapper dictBedMapper;

    // ===================== 管理员接口 ====================
    // ======== 通常需要添加权限验证
    // ======== 2020/9/26 14:12

    @Override
    public Boolean adminInsert(List<DictBed> pDictBedList) {
        return saveBatch(pDictBedList);
    }

    @Override
    public Boolean adminDelete(List<String> pIdList) {
        return removeByIds(pIdList);
    }

    @Override
    public Page<DictBed> adminPage(int pPageNum, int pPageSize, DictBed pDictBed) {
        return page(new Page(pPageNum, pPageSize), Wrappers.lambdaQuery(pDictBed)
                .like(!StringUtils.isBlank(pDictBed.getCnName()), DictBed::getCnName, pDictBed.getCnName())
                .like(!StringUtils.isBlank(pDictBed.getEnName()), DictBed::getEnName, pDictBed.getEnName())
                .like(!StringUtils.isBlank(pDictBed.getDescription()), DictBed::getDescription, pDictBed.getDescription())
                .eq(!StringUtils.isBlank(pDictBed.getId()), DictBed::getId, pDictBed.getId())
                .orderByDesc(DictBed::getCreateTime)
        );
    }

    @Override
    public Boolean adminUpdate(List<DictBed> pDictBedList) {
        return updateBatchById(pDictBedList);
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
