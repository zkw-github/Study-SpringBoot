package com.student.zhaokangwei.controller;

import com.student.zhaokangwei.service.ILeaveApplyService;
import com.student.zhaokangwei.service.ILeaveApplyStateService;
import com.student.zhaokangwei.service.ILeaveApplyTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 请假申请 Controller
 */
@Api(tags = "请假申请相关接口")
@Slf4j
@RestController
@RequestMapping("admin/leaveApply")
public class LeaveApplyController extends BaseController {

    @Resource
    ILeaveApplyService leaveApplyService;
    @Resource
    ILeaveApplyTypeService leaveApplyTypeService;
    @Resource
    ILeaveApplyStateService leaveApplyStateService;

    @ApiModel("我的请假申请参数实体")
    @Data
    static class MyLeaveApplyParamBody {
        @ApiModelProperty("页码")
        @NotNull(message = "请传递页码：pageIndex")
        private Integer pageIndex;
        @ApiModelProperty("页大小，每一页多少条数据")
        @Max(value = 12, message = "页大小不能超过12")
        @NotNull(message = "请传递页大小：pageSize")
        private Integer pageSize;
    }

    @ApiOperation("请假申请列表")
    @PostMapping
    public Object index(@Valid @RequestBody MyLeaveApplyParamBody paramBody, BindingResult result) {
        return success(null, leaveApplyService.pageMyLeaveApplyInfo(paramBody.getPageIndex(),
                paramBody.getPageSize()));
    }

    @Data
    static class LeaveApplyParamBody extends MyLeaveApplyParamBody {
        private String username;
    }

    @PostMapping("all")
    @PreAuthorize("hasAnyRole('admin','ROLE_boss')")
    public Object all(@Valid @RequestBody LeaveApplyParamBody paramBody, BindingResult result) {
        return success(null, leaveApplyService.pageLeaveApplyInfo(paramBody.getPageIndex(),
                paramBody.getPageSize(), paramBody.getUsername()));
    }
    @ApiOperation("请假类型")
    @GetMapping("types")
    public Object types() {
        return success(null, leaveApplyTypeService.list());
    }

    @ApiOperation("请假审核状态")
    @GetMapping("states")
    public Object states() {
        return success(null, leaveApplyStateService.list());
    }

    @Data
    static class LeaveApplySubmitParam {
        @NotNull(message = "请传递请假类型")
        private Byte typeId;
        @NotNull(message = "请传递请假开始时间")
        private LocalDateTime starttime;
        @NotNull(message = "请传递请假结束时间")
        private LocalDateTime endtime;
        @NotBlank(message = "请传递请假原因")
        private String reason;  //请假原因
    }

    /**
     * 提交申请
     *
     * @param param
     * @param result
     * @return
     */
    @ApiOperation("提交请假申请")
    @PostMapping("submit")
    public Object submit(@Valid @RequestBody LeaveApplySubmitParam param, BindingResult result) {

        leaveApplyService.submitApply(param.getTypeId(), param.getStarttime(), param.getEndtime(), param.getReason());
        return success("提交成功", null);
    }

    /**
     * 撤回申请
     *
     * @param id
     * @return
     */
    @ApiOperation("撤回申请")
    @PostMapping("callback")
    public Object callback(@RequestParam Integer id) {
        leaveApplyService.callbackApply(id);
        return success("撤回成功", null);
    }

    /**
     * 审批
     *
     * @param processInstanceId
     * @return
     */
    @ApiOperation("请假审批")
    @PostMapping("approval")
    public Object approval(@RequestParam String processInstanceId) {
        return success(null, leaveApplyService.approval(processInstanceId));
    }

    @Data
    static class ApprovalDisposeParam {
        @NotNull(message = "请传递请假记录ID")
        private Integer leaveApplyId;
        private String disposeReason;   //处理意见
        private String taskID;  //任务ID
    }

    /**
     * 审批通过
     *
     * @param approvalDisposeParam
     * @param result
     * @return
     */
    @ApiOperation("审批通过")
    @PostMapping("passDispose")
    public Object passDispose(@Valid @RequestBody ApprovalDisposeParam approvalDisposeParam, BindingResult result) {
        leaveApplyService.passDispose(approvalDisposeParam.getLeaveApplyId(),
                approvalDisposeParam.getDisposeReason(), approvalDisposeParam.getTaskID());
        return success("处理成功", null);
    }

    /**
     * 审批驳回
     *
     * @param approvalDisposeParam
     * @param result
     * @return
     */
    @ApiOperation("审批驳回")
    @PostMapping("rejectDispose")
    public Object rejectDispose(@Valid @RequestBody ApprovalDisposeParam approvalDisposeParam, BindingResult result) {
        leaveApplyService.rejectDispose(approvalDisposeParam.getLeaveApplyId(),
                approvalDisposeParam.getDisposeReason(), approvalDisposeParam.getTaskID());
        return success("处理成功", null);
    }

}