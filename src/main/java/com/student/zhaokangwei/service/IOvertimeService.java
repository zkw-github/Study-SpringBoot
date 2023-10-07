package com.student.zhaokangwei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.student.zhaokangwei.entity.Overtime;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 请假申请 Service
 */
public interface IOvertimeService extends IService<Overtime> {


    /**
     * 查询我的请假记录
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    IPage<Map<String, Object>> pageMyOvertimeInfo(Integer pageIndex, Integer pageSize);

    /**
     * 查询请假记录（管理员或者boss去查询的）
     *
     * @param pageIndex
     * @param pageSize
     * @param username
     * @return
     */
    IPage<Map<String, Object>> pageOvertimeInfo(Integer pageIndex, Integer pageSize, String username);

    /**
     * 提交申请
     *
     * @param typeId    类型ID
     * @param starttime 开始时间
     * @param endtime   结束时间
     * @param reason    申请原因
     */
    void submitApply(Byte typeId, LocalDateTime starttime, LocalDateTime endtime, String reason);


    /**
     * 撤回申请
     *
     * @param applyId 加班申请ID
     */
    void callbackApply(Integer applyId);

    /**
     * 获取审批数据
     *
     * @param processInstanceId 流程的实例ID
     */
    Map<String, Object> approval(String processInstanceId);

    /**
     * @param overtimeId    加班申请ID
     * @param disposeReason 处理意见
     * @param taskID        任务ID
     */
    void passDispose(int overtimeId, String disposeReason, String taskID);

    /**
     * @param overtimeId    加班申请ID
     * @param disposeReason 处理意见
     * @param taskID        任务ID
     */
    void rejectDispose(int overtimeId, String disposeReason, String taskID);
}