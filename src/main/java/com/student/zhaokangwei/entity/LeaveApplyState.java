package com.student.zhaokangwei.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 请假申请状态 实体类
 */
@Data
public class LeaveApplyState implements Serializable {

    private Integer id;
    private String name;
}