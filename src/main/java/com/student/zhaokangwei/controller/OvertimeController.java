package com.student.zhaokangwei.controller;

import com.student.zhaokangwei.service.IOvertimeService;
import com.student.zhaokangwei.service.IOvertimeStateService;
import com.student.zhaokangwei.service.IOvertimeTypeService;
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
 * 加班申请 Controller
 */
@Api(tags = "加班申请相关接口")
@Slf4j
@RestController
@RequestMapping("admin/overtime")
public class OvertimeController extends BaseController {

    @Resource
    IOvertimeService overtimeService;
    @Resource
    IOvertimeTypeService overtimeTypeService;
    @Resource
    IOvertimeStateService overtimeStateService;

    @ApiModel("我的加班申请参数实体")
    @Data
    static class MyOvertimeParamBody {
        @ApiModelProperty("页码")
        @NotNull(message = "请传递页码：pageIndex")
        private Integer pageIndex;
        @ApiModelProperty("页大小，每一页多少条数据")
        @Max(value = 12, message = "页大小不能超过12")
        @NotNull(message = "请传递页大小：pageSize")
        private Integer pageSize;
    }

    @ApiOperation("加班申请列表")
    @PostMapping
    public Object index(@Valid @RequestBody MyOvertimeParamBody paramBody, BindingResult result) {
        return success(null, overtimeService.pageMyOvertimeInfo(paramBody.getPageIndex(),
                paramBody.getPageSize()));
    }

    @Data
    static class OvertimeParamBody extends MyOvertimeParamBody {
        private String username;
    }

    @PostMapping("all")
    @PreAuthorize("hasAnyRole('admin','ROLE_boss')")
    public Object all(@Valid @RequestBody OvertimeParamBody paramBody, BindingResult result) {
        return success(null, overtimeService.pageOvertimeInfo(paramBody.getPageIndex(),
                paramBody.getPageSize(), paramBody.getUsername()));
    }
    @ApiOperation("加班类型")
    @GetMapping("types")
    public Object types() {
        return success(null, overtimeTypeService.list());
    }

    @ApiOperation("加班申请审核状态")
    @GetMapping("states")
    public Object states() {
        return success(null, overtimeStateService.list());
    }

    @Data
    static class OvertimeSubmitParam {
        @NotNull(message = "请传递加班类型")
        private Byte typeId;
        @NotNull(message = "请传递加班开始时间")
        private LocalDateTime starttime;
        @NotNull(message = "请传递加班结束时间")
        private LocalDateTime endtime;
        @NotBlank(message = "请传加班原因")
        private String reason;  //请假原因
    }

    /**
     * 提交申请
     *
     * @param param
     * @param result
     * @return
     */
    @ApiOperation("提交申请")
    @PostMapping("submit")
    public Object submit(@Valid @RequestBody OvertimeSubmitParam param, BindingResult result) {

        overtimeService.submitApply(param.getTypeId(), param.getStarttime(), param.getEndtime(), param.getReason());
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
        overtimeService.callbackApply(id);
        return success("撤回成功", null);
    }

    /**
     * 审批
     *
     * @param processInstanceId
     * @return
     */
    @ApiOperation("审批")
    @PostMapping("approval")
    public Object approval(@RequestParam String processInstanceId) {
        return success(null, overtimeService.approval(processInstanceId));
    }

    @Data
    static class ApprovalDisposeParam {
        @NotNull(message = "请传递请假记录ID")
        private Integer overtimeId;
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
        overtimeService.passDispose(approvalDisposeParam.getOvertimeId(),
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
        overtimeService.rejectDispose(approvalDisposeParam.getOvertimeId(),
                approvalDisposeParam.getDisposeReason(), approvalDisposeParam.getTaskID());
        return success("处理成功", null);
    }

}