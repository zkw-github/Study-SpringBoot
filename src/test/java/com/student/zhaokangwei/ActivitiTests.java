package com.student.zhaokangwei;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 流程部署
 */
@Slf4j
@SpringBootTest
public class ActivitiTests {

    @Resource
    RepositoryService repositoryService;

    /**
     * 部署流程
     */
    @Test
    public void createFlow(){
        Deployment deployment=repositoryService.createDeployment()
                .name("请假流程") //给部署的流程命名一个名称
                .addClasspathResource("activiti/leaveApply/diagram.bpmn") //部署流程数据
                .addClasspathResource("activiti/leaveApply/diagram.svg")  //部署流程图片
                .deploy();//执行部署
        log.info("部署完成");
        log.info("部署id: " + deployment.getId());
        log.info("部署名称：" + deployment.getName());
        log.info("部署时间：" + deployment.getDeploymentTime());
        log.info("部署key: " + deployment.getKey());

    }
}
