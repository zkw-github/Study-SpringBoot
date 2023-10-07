package com.student.zhaokangwei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.Flow;
import com.student.zhaokangwei.exception.ServiceValidationException;
import com.student.zhaokangwei.mapper.IFlowMapper;
import com.student.zhaokangwei.service.IFlowService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class FlowServiceImpl extends ServiceImpl<IFlowMapper, Flow> implements IFlowService {


    @Resource
    RepositoryService repositoryService;

    @Resource
    IFlowMapper flowMapper;

    @Override
    public void deployFlow(String flowName, File bpmnFile, File svgFile) throws FileNotFoundException {
        QueryWrapper queryWrapper = new QueryWrapper(); //定义一个条件构造器，用于更新flow表的数据
        queryWrapper.eq("flow_name", flowName);
        if (count(queryWrapper) == 0) {
            throw new ServiceValidationException("传递的流程名称不存在", 402);
        }

        Deployment deployment = repositoryService.createDeployment()            // 创建一个流程部署对象
                .name(flowName)                 // 给部署的流程命名一个名称
                .addInputStream("diagram.bpmn", new FileInputStream(bpmnFile))   //部署流程数据
                .addInputStream("diagram.svg", new FileInputStream(svgFile))      //部署流程图片
                .deploy();      //执行部署


        Flow flow = new Flow();
        flow.setDeploymentId(deployment.getId());
        flow.setDefinitionId(flowMapper.selectDefinitionIdByDeployment(deployment.getId()));
        //更新数据
        update(flow, queryWrapper);

    }
}