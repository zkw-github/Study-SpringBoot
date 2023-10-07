package com.student.zhaokangwei.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 部门实体类
 */
@Data
@ApiModel("部门实体类")
public class Department implements Serializable {
    @ApiModelProperty("部门ID")
    @NotNull(message = "必须传递部门编号")
    private Integer id;//部门编号
    @ApiModelProperty("部门名称")
    @NotBlank(message = "必须传递部门名称")
    private String name;//部门名称

}