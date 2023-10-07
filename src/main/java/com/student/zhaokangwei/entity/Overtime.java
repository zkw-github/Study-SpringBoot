package com.student.zhaokangwei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 加班申请实体类
 */
@Data
public class Overtime implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;//加班申请ID
    private Integer userId;//加班申请人ID
    private Byte typeId;//加班申请类型
    private LocalDateTime starttime;//加班开始时间
    private LocalDateTime endtime;//加班结束时间
    private String reason;//加班原因
    private Byte stateId;//加班申请状态
    private String disposeReason;//处理意见
    private String processInstanceId;//进程实例ID


}
