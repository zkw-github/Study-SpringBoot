package com.student.zhaokangwei.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 流程 实体类
 */
@Data
public class Flow implements Serializable {

    private Integer id;
    private String flowName;
    private String deploymentId;
    private String definitionId;
}
