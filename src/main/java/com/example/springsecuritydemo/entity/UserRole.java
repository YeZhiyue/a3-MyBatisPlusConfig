package com.example.springsecuritydemo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springsecuritydemo.entity.basic.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author YeZhiyue
 * Description 角色用户关联表 服务实现类
 * Date 2020/10/04
 * Time 14:39
 * Mail 739153436@qq.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("relation_user_role")
@ApiModel(value = "UserRole对象", description = "角色用户关联表")
public class UserRole extends BaseModel<UserRole> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "user外键", reference = "varchar(36)")
    @TableField(value = "user_id")
    private String userId;

    @ApiModelProperty(value = "role外键", reference = "varchar(36)")
    @TableField(value = "role_id")
    private String roleId;

    public UserRole(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
