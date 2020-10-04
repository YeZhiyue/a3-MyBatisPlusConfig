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
 * Description 角色类型表 服务实现类
 * Date 2020/10/04
 * Time 14:38
 * Mail 739153436@qq.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("dict_role")
@ApiModel(value = "Role对象", description = "角色类型表")
public class Role extends BaseModel<Role> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色类型名称", reference = "varchar(64)")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "描述", reference = "varchar(255)")
    @TableField(value = "description")
    private String description;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
