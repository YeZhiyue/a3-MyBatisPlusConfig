package com.example.springsecuritydemo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springsecuritydemo.entity.basic.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YeZhiyue
 * Description 用户表 服务实现类
 * Date 2020/10/04
 * Time 14:37
 * Mail 739153436@qq.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("info_user")
@ApiModel(value="User对象", description="用户表")
public class User extends BaseModel<User> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户名", reference = "varchar(64)")
    @TableField(value = "user_name")
    private String userName;

    @ApiModelProperty(value = "用户昵称", reference = "varchar(64)")
    @TableField(value = "full_name")
    private String fullName;

    @ApiModelProperty(value = "密码", reference = "varchar(64)")
    @TableField(value = "password")
    private String password;

    @ApiModelProperty(value = "是否可用", reference = "tinyint(1)")
    @TableField(value = "enabled")
    private Boolean enabled;

    private transient List<Role> roles;

    /**
     * @description 用户角色信息获取, 用于权限管理
     * @author 叶之越
     * @email 739153436@qq.com
     * @date 2020/10/5 9:26
     */
    public List<SimpleGrantedAuthority> getRoles() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorities;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
