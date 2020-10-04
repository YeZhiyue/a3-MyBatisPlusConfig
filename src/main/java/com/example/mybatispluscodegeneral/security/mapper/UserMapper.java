package com.example.mybatispluscodegeneral.security.mapper;

import com.example.mybatispluscodegeneral.security.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author YeZhiyue
 * Description 用户表 服务实现类
 * Date 2020/10/04
 * Time 14:37
 * Mail 739153436@qq.com
 */
public interface UserMapper extends BaseMapper<User> {

        // ===================== 基础SQL ====================
        // ======== 在需要的时候可以使用，在多表联查或者批量插入的时候可以使用
        // ======== 2020/9/26 16:07

        List<User> listByIds(@Param("ids") List<String> pIdList);

        Boolean saveBatch(@Param("objs") List<User> pUserList);
}
