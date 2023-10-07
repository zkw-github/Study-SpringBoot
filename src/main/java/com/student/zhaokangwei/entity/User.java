package com.student.zhaokangwei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户表
 */
@Data
@Slf4j
public class User implements Serializable {

    @NotNull(message = "请传递ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @NotBlank(message = "用户名不能为空")
    private String username;
    private String password;
    @NotNull(message = "请传递部门ID")
    private Integer departmentId;
    @NotNull(message = "请传递角色ID")
    private Integer roleId;
    @NotBlank(message = "请传递姓名")
    private String realname;
    @NotBlank(message = "请传递性别")
    private String sex;
    @Min(value = 1, message = "年龄不能低于1")
    @Max(value = 150, message = "年龄不能超过150")
    @NotNull(message = "请传递年龄")
    private Short age;
    @NotBlank(message = "请传递手机号码")
    private String mobile;
    @NotNull(message = "请传递状态")
    private Byte state;
    private String authority;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password,
                Integer departmentId, Integer roleId, String realname, String sex, Short age,
                String mobile, Byte state, String authority) {
        this.username = username;
        this.password = password;
        this.departmentId = departmentId;
        this.roleId = roleId;
        this.realname = realname;
        this.sex = sex;
        this.age = age;
        this.mobile = mobile;
        this.state = state;
        this.authority = authority;
    }

    public User(Integer id, String username, String password,
                Integer departmentId, Integer roleId, String realname, String sex, Short age, String mobile, Byte state, String authority) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.departmentId = departmentId;
        this.roleId = roleId;
        this.realname = realname;
        this.sex = sex;
        this.age = age;
        this.mobile = mobile;
        this.state = state;
        this.authority = authority;
    }
}