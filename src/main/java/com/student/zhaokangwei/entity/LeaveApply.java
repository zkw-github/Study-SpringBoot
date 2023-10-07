package com.student.zhaokangwei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 请假申请实体类
 */
@Data
public class LeaveApply implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Byte typeId;
    private LocalDateTime starttime;
    private LocalDateTime endtime;
    private String reason;  //请假原因
    private Byte stateId;     //1: 待审  2:审核中 3: 驳回  4:通过 5:撤回
    private String disposeReason;   //处理意见

    private String processInstanceId;   //发起的流程的实例ID

}