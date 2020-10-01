package com.example.mybatispluscodegeneral.entity;

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

/**
 * @author YeZhiyue
 * Description 楼房类型字典表 服务实现类
 * Date 2020/10/01
 * Time 16:35
 * Mail 739153436@qq.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dict_building")
@ApiModel(value="DictBuilding对象", description="楼房类型字典表")
public class DictBuilding extends BaseModel<DictBuilding> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "楼房类型名称（中文）", reference = "varchar(64)")
    @TableField(value = "cn_name")
    private String cnName;

    @ApiModelProperty(value = "楼房类型名称（英文）", reference = "varchar(64)")
    @TableField(value = "en_name")
    private String enName;

    @ApiModelProperty(value = "描述", reference = "varchar(255)")
    @TableField(value = "description")
    private String description;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
