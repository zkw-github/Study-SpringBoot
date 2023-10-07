package com.student.zhaokangwei.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 加班申请类型 实体类
 */
@Data
public class OvertimeType implements Serializable {
    private Integer id;
    private String name;

}
