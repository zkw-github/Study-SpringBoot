package com.student.zhaokangwei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.student.zhaokangwei.entity.LeaveApply;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 请假申请 Service
 */
public interface ILeaveApplyService extends IService<LeaveApply> {


    /**
     * 查询我的请假记录
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    IPage<Map<String, Object>> pageMyLeaveApplyInfo(Integer pageIndex, Integer pageSize);

    /**
     * 查询请假记录（管理员或者boss去查询的）
     *
     * @param pageIndex
     * @param pageSize
     * @param username
     * @return
     */
    IPage<Map<String, Object>> pageLeaveApplyInfo(Integer pageIndex, Integer pageSize, String username);

    /**
     * 提交请假
     *
     * @param typeId
     * @param starttime
     * @param endtime
     * @param reason
     */
    void submitApply(Byte typeId, LocalDateTime starttime, LocalDateTime endtime, String reason);


    /**
     * 撤回申请
     *
     * @param applyId
     */
    void callbackApply(Integer applyId);

    /**
     * 获取审批数据
     *
     * @param processInstanceId 流程的实例ID
     */
    Map<String, Object> approval(String processInstanceId);

    /**
     * 处理请假申请（通过）
     *
     * @param leaveApplyId
     * @param disposeReason
     */
    void passDispose(int leaveApplyId, String disposeReason, String taskID);

    /**
     * 处理请假申请（驳回）
     *
     * @param leaveApplyId
     * @param disposeReason
     */
    void rejectDispose(int leaveApplyId, String disposeReason, String taskID);
}