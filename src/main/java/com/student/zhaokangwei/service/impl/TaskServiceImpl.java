package com.student.zhaokangwei.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.student.zhaokangwei.entity.User;
import com.student.zhaokangwei.mapper.IFlowMapper;
import com.student.zhaokangwei.service.ITaskService;
import com.student.zhaokangwei.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TaskServiceImpl implements ITaskService {

    @Resource
    TaskService taskService;
    @Resource
    IFlowMapper flowMapper;

    @Resource
    RuntimeService runtimeService;
    @Resource
    IUserService userService;

    @Override
    public JSONArray myList() {
        //直接从Security上下文对象中取出当前请求者的身份信息
        Integer userId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        //根据当前请求者的ID，获取全部任务
        List<Task> tasks = taskService.createTaskQuery().list();
        //创建一个需要返回的JSONArray对象
        JSONArray array = new JSONArray();
        //然后去循环tasks，取出数据封装到array里面
        for (Task task : tasks) {
            log.info("任务ID：" + task.getId());
            log.info("任务名称：" + task.getName());
            log.info("请假申请对应的实例ID：" + task.getProcessInstanceId());
            log.info("请假申请对应的定义ID：" + task.getProcessDefinitionId());

            String[] assignees = task.getAssignee().split(","); //将受理人ID拆分为数组
            if (!Arrays.asList(assignees).contains(String.valueOf(userId)))
                continue;   //如果当前任务的受理人里面没有包括当前请求者，则continue

            Map<String, Object> flow = flowMapper.selectFlowAndProcdef(task.getProcessDefinitionId());
            log.info("flow:" + flow);
            if(flow == null) continue;


            //获取当前实例对象
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId()).singleResult();
            User user = userService.getById(Integer.parseInt(processInstance.getStartUserId()));

            //创建一个JSONObject用于保存我们需要返回的数据
            JSONObject object = new JSONObject();
            object.put("flowName", flow.get("flow_name"));
            log.info("flow_name" + flow.get("flow_name"));
            object.put("startUser", user.getRealname());
            object.put("taskId", task.getId());
            object.put("taskName", task.getName());
            object.put("taskProcessInstanceId", task.getProcessInstanceId());


            array.add(object);
        }

        return array;
    }
}