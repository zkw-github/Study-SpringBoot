package com.student.zhaokangwei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.student.zhaokangwei.entity.Flow;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 流程Service
 */
public interface IFlowService extends IService<Flow> {

    /**
     * 部署流程
     */
    void deployFlow(String flowName, File bpmnFile, File svgFile) throws FileNotFoundException;


}