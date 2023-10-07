package com.student.zhaokangwei.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 加班申请状态 实体类
 */
@Data
public class OvertimeState implements Serializable {

    private Integer id;
    private String name;

}
