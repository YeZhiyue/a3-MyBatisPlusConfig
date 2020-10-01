package com.example.mybatispluscodegeneral.mapper;

import com.example.mybatispluscodegeneral.entity.DictBed;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author YeZhiyue
 * Description 床类型字典表 服务实现类
 * Date 2020/10/01
 * Time 16:35
 * Mail 739153436@qq.com
 */
public interface DictBedMapper extends BaseMapper<DictBed> {

        // ===================== 基础SQL ====================
        // ======== 在需要的时候可以使用，在多表联查或者批量插入的时候可以使用
        // ======== 2020/9/26 16:07

        List<DictBed> listByIds(@Param("ids") List<String> pIdList);

        Boolean saveBatch(@Param("objs") List<DictBed> pDictBedList);
}
