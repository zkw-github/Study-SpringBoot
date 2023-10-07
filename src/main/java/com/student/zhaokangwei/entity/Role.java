package com.student.zhaokangwei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 角色实体类
 */
@Data
@TableName("role")
public class Role implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "必须传递角色ID")
    private Integer id;

    @TableField("name")
    @NotBlank(message = "必须传递角色名称")
    private String name;

    private String code;

    public Role() {
    }

    public Role(@NotNull(message = "必须传递角色ID") Integer id, @NotBlank(message = "必须传递角色名称") String name) {
        this.id = id;
        this.name = name;
    }
}