package com.example.mybatispluscodegeneral.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.mybatispluscodegeneral.entity.basic.BaseModel;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
