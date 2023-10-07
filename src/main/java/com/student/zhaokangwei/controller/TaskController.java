package com.student.zhaokangwei.controller;

import com.student.zhaokangwei.service.ITaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 我的任务 Controller
 */
@Api(tags = "任务相关接口")
@RestController
@RequestMapping("admin/task")
public class TaskController extends BaseController {

    @Resource
    ITaskService taskService;

    @ApiOperation("我的任务列表")
    @GetMapping
    public Object myTask() {
        return success(null, taskService.myList());
    }

}