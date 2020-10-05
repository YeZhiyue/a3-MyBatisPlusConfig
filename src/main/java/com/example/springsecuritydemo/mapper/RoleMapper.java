package com.example.springsecuritydemo.mapper;

import com.example.springsecuritydemo.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YeZhiyue
 * Description 角色类型表 服务实现类
 * Date 2020/10/04
 * Time 14:38
 * Mail 739153436@qq.com
 */
public interface RoleMapper extends BaseMapper<Role> {

    // ===================== 基础SQL ====================
    // ======== 在需要的时候可以使用，在多表联查或者批量插入的时候可以使用
    // ======== 2020/9/26 16:07

    List<Role> listByIds(@Param("ids") List<String> pIdList);

    Boolean saveBatch(@Param("objs") List<Role> pRoleList);
}
