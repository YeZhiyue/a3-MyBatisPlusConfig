package com.example.mybatispluscodegeneral.entity.basic;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 叶之越
 * Description
 * Date 2020/9/24
 * Time 7:52
 * Mail 739153436@qq.com
 */
@Data
public class BaseModel<T extends Model<?>> extends Model<T> {

    // 参数校验的分组使用参数
    public interface Default { }
    public interface Insert { }
    public interface Update { }

    @NotNull(groups = {Update.class}, message = "更新的对象id不能为空")
    @ApiModelProperty(value = "id", reference = "varchar(30)")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "权重", reference = "int(11)")
    @TableField(value = "weight")
    private Integer weight;

    @ApiModelProperty(value = "创建时间", reference = "bigint(20)")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Long createTime;

    @ApiModelProperty(value = "更新时间", reference = "bigint(20)")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Long updateTime;

    @ApiModelProperty(value = "创建者", reference = "varchar(36)")
    @TableField(value = "create_by")
    private String createBy;

    @ApiModelProperty(value = "更新者", reference = "varchar(36)")
    @TableField(value = "update_by")
    private String updateBy;

    @ApiModelProperty(value = "版本", reference = "int(11)")
    @TableField(value = "version")
    @Version
    private Integer version;

    @ApiModelProperty(value = "是否有效 0-未删除 1-已删除", reference = "int(11)")
    @TableField(value = "deleted")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "额外信息", reference = "varchar(1000)")
    @TableField(value = "extra")
    private String extra;

    @ApiModelProperty(value = "租户id", reference = "varchar(255)")
    @TableField(value = "tenant_id")
    private String tenantId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
