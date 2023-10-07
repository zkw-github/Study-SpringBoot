package com.student.zhaokangwei.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 请假申请类型 实体类
 */
@Data
public class LeaveApplyType implements Serializable {

    private Integer id;
    private String name;
}
