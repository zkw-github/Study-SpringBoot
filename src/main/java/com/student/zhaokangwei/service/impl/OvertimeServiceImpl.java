package com.student.zhaokangwei.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.Flow;
import com.student.zhaokangwei.entity.Overtime;
import com.student.zhaokangwei.entity.User;
import com.student.zhaokangwei.exception.ServiceValidationException;
import com.student.zhaokangwei.mapper.IOvertimeMapper;
import com.student.zhaokangwei.service.IFlowService;
import com.student.zhaokangwei.service.IOvertimeService;
import com.student.zhaokangwei.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 加班申请 Service实现类
 */
@Slf4j
@Service
public class OvertimeServiceImpl extends ServiceImpl<IOvertimeMapper, Overtime> implements IOvertimeService {

    @Resource
    IOvertimeMapper overtimeMapper;
    @Resource
    TaskService taskService;
    @Resource
    IUserService userService;

    @Resource
    RepositoryService repositoryService;

    @Override
    public IPage<Map<String, Object>> pageMyOvertimeInfo(Integer pageIndex, Integer pageSize) {
        //直接从Security上下文对象中取出当前请求者的身份信息
        Integer userId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        Page page = new Page(pageIndex, pageSize);  //分页对象
        QueryWrapper<Overtime> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        IPage<Map<String, Object>> mapIPage = overtimeMapper.pageOvertimeJoinUser(page, queryWrapper);
        for (Map<String, Object> map : mapIPage.getRecords()) {
            if (map.get("process_instance_id") == null) continue;
            //根据从加班记录里面获取的实例ID，来获取对应的任务集合
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(map.get("process_instance_id").toString()).list();
            JSONArray taskArray = new JSONArray();
            for (Task task : tasks) {
                JSONObject taskObject = new JSONObject();
                taskObject.put("taskName", task.getName());
//                taskObject.put("deposeUser", userService.getById(Integer.parseInt(task.getAssignee())).getRealname());
                /*
                task.getAssignee()获取的值，有可能是单个数据，如：2  ，还可能有多个数据，如：2,3
                 */
                String[] assignees = task.getAssignee().split(",");
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.select("realname");
                userQueryWrapper.in("id", Arrays.asList(assignees));
                List<String> deposeUserRealNames = userService.listObjs(userQueryWrapper, o -> o.toString());
                taskObject.put("deposeUsers", deposeUserRealNames);
                taskArray.add(taskObject);
            }
            map.put("task", taskArray);
        }
        return mapIPage;
    }


    @Override
    public IPage<Map<String, Object>> pageOvertimeInfo(Integer pageIndex, Integer pageSize, String username) {
        Page page = new Page(pageIndex, pageSize);  //分页对象
        QueryWrapper<Overtime> queryWrapper = new QueryWrapper<>();
        if (username != null && !username.equals("")) {
            queryWrapper.and(wrapper -> wrapper.like("username", username)
                    .or()
                    .like("realname", username));
        }
        return overtimeMapper.pageOvertimeJoinUser(page, queryWrapper);
    }

    @Resource
    IFlowService flowService;

    @Resource
    RuntimeService runtimeService;

    @Override
    public void submitApply(Byte typeId, LocalDateTime starttime, LocalDateTime endtime, String reason) {
        //获取开始时间
        long starttimeStamp = starttime.toEpochSecond(ZoneOffset.of("+8"));
        //获取结束时间
        long endtimeStamp = endtime.toEpochSecond(ZoneOffset.of("+8"));
        //后面就是验证两个时间的有效性
        if (starttimeStamp >= endtimeStamp) {
            throw new ServiceValidationException("开始时间不能在结束时间之后", 402);
        }
        if (endtimeStamp - starttimeStamp < 30 * 60) {
            throw new ServiceValidationException("时间间隔不能小于30分钟", 402);
        }
        //直接从Security上下文对象中取出当前请求者的身份信息
        Integer userId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        //获取“加班流程”的信息
        Flow flow = flowService.getById(2);

        //设置发起人标识
        Authentication.setAuthenticatedUserId(String.valueOf(userId));
        //发起流程->启动一个流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(flow.getDefinitionId());
        log.info("已启动的流程进程实例的ID：" + processInstance.getId());
        log.info("已启动的流程进程实例的部署ID：" + processInstance.getProcessDefinitionId());
        //完成流程的第一个（发起）环节
        //1.获取当前流程实例ID的任务
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        //2.完成“发起”环节
        taskService.complete(task.getId());
        Overtime apply = new Overtime();
        apply.setUserId(userId);
        apply.setTypeId(typeId);
        apply.setStarttime(starttime);
        apply.setEndtime(endtime);
        apply.setReason(reason);
        apply.setStateId((byte) 1);
        apply.setProcessInstanceId(processInstance.getId());
        if (!save(apply)) {
            throw new ServiceValidationException("加班申请失败，原因未知", 402);
        }
    }

    @Override
    public void callbackApply(Integer applyId) {
        //直接从Security上下文对象中取出当前请求者的身份信息
        Integer userId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        QueryWrapper<Overtime> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", applyId)
                .eq("user_id", userId)
                .eq("state", 1);
        Overtime apply = new Overtime();
        apply.setStateId((byte) 4);
        if (!update(apply, queryWrapper)) {
            throw new ServiceValidationException("加班撤回失败，该申请已不能再撤回，如已审核中", 402);
        }
    }

    @Override
    public Map<String, Object> approval(String processInstanceId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("process_instance_id", processInstanceId);
        Map<String, Object> map = overtimeMapper.getOvertimeJoinUser(queryWrapper);
        if (map == null) {
            throw new ServiceValidationException("未找到对应的加班记录", 402);
        }
        return map;
    }

    @Override
    public void passDispose(int overtimeId, String disposeReason, String taskID) {
        //1.获取任务ID，并通过当前任务，进入到下一个任务环节
        Task task = taskService.createTaskQuery().taskId(taskID).singleResult();
        String processInstanceId = task.getProcessInstanceId(); //获取当前任务的实例ID
        taskService.complete(taskID);

        //2. 判断是否为最后 不念旧恶审批环节
        long count = taskService.createTaskQuery().processInstanceId(processInstanceId).count();
        if (count == 0) {

            //3. 如果是最后一个审批环节，则更新申请记录的state值，改为3
            changeState(overtimeId, disposeReason, (byte) 3);

            //通过后，我们还可以继续在这里添加其它的业务逻辑代码
        }

    }


    @Override
    public void rejectDispose(int overtimeId, String disposeReason, String taskID) {
        //1. 终止当前任务所在的实例流程
        Task task = taskService.createTaskQuery().taskId(taskID).singleResult();

        //通过任务的部署ID获取到流程的Bpmn模型数据
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        //获取结束结束事件集合
        List endEventList = bpmnModel.getMainProcess().findFlowElementsOfType(EndEvent.class);

        //获取当前环节的流程节点对象
        FlowNode currentFlowNode = (FlowNode) bpmnModel.getFlowElement(task.getTaskDefinitionKey());

        //获取结束节点对象
        FlowNode targetFlowNode = (FlowNode) endEventList.get(0);

        //临时保存当前活动的原始方向
        List<SequenceFlow> originalSequenceFlow = new ArrayList<>();
        originalSequenceFlow.addAll(currentFlowNode.getOutgoingFlows());
        currentFlowNode.getOutgoingFlows().clear(); //清除当前流程节点的所有向外的指向线

        //建立新的指向线
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setId("newSequenceFlow");
        newSequenceFlow.setSourceFlowElement(currentFlowNode);
        newSequenceFlow.setTargetFlowElement(targetFlowNode);

        //新的指向线集合
        List<SequenceFlow> newSequenceFlows = new ArrayList<>();
        newSequenceFlows.add(newSequenceFlow);

        //为当前节点指向新的指向线
        currentFlowNode.setOutgoingFlows(newSequenceFlows);
        //在最后一个环节执行完毕
        taskService.complete(taskID);

        //恢复原始方向
        currentFlowNode.setOutgoingFlows(originalSequenceFlow);

        //2. 更新加班记录的state值，改为2
        changeState(overtimeId, disposeReason, (byte) 2);

    }

    /**
     * 修改加班记录状态和意见
     *
     * @param overtimeId
     * @param disposeReason
     * @param state
     */
    private void changeState(int overtimeId, String disposeReason, byte state) {
        //直接从Security上下文对象中取出当前请求者的身份信息
        Integer userId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        User user = userService.getById(userId);

        QueryWrapper<Overtime> overtimeQueryWrapper = new QueryWrapper<>();
        overtimeQueryWrapper.eq("id", overtimeId);
        Overtime overtime = new Overtime();
        overtime.setStateId(state); //设置新的状态值
        overtime.setDisposeReason(user.getRealname() + ":" + disposeReason); //更新处理意见
        update(overtime, overtimeQueryWrapper); //执行UPDATE的SQL语句
    }


}