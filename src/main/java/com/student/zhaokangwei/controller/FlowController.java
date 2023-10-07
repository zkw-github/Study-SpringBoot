package com.student.zhaokangwei.controller;

import com.student.zhaokangwei.service.IFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 流程管理
 */
@Api(tags = "流程相关接口")
@Slf4j
@RestController
@RequestMapping("admin/flow")
public class FlowController extends BaseController {

    @Value("${fileUploadPath}")
    String fileUploadPath;

    @Resource
    IFlowService flowService;

    @ApiOperation("流程列表")
    @GetMapping
    public Object index() {
        return success(null, flowService.list());
    }

    @ApiOperation("根据ID获取流程")
    @PostMapping("get")
    public Object get(@Param("流程ID") @RequestParam Integer id) {
        return success(null, flowService.getById(id));
    }

    /**
     * 上传bpmn/svg文件，部署流程
     *
     * @param bpmnPartFile
     * @param svgPartFile
     * @return
     * @throws IOException
     */
    @ApiOperation("部署流程")
    @PostMapping("deploy")
    public Object deploy(String flowName, MultipartFile bpmnPartFile, MultipartFile svgPartFile) throws IOException {
        File bpmnFile = new File(fileUploadPath + "/activiti/1.bpmn");
        File svgFile = new File(fileUploadPath + "/activiti/1.svg");

        //开始上传两个文件
        bpmnPartFile.transferTo(bpmnFile); //上传bpmn文件
        svgPartFile.transferTo(svgFile); //上传svg文件

        //根据上面上传的两个文件，部署到activiti里面
        flowService.deployFlow(flowName, bpmnFile, svgFile);

        return success("部署成功", null);
    }

    @Resource
    RepositoryService repositoryService;

    /**
     * 查看SVG图片
     *
     * @param processDefinitionId
     * @return
     */
    @ApiOperation("查看SVG图片")
    @GetMapping("getSvg/{processDefinitionId}")
    public void getSvg(@ApiParam("进程定义ID") @PathVariable String processDefinitionId) throws IOException {
        InputStream inputStream = repositoryService.getResourceAsStream(processDefinitionId, "diagram.svg");

        //输出流
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[inputStream.available()];
        int read;   //读取次数
        while (inputStream != null && (read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        //把文件流输出
        outputStream.flush();
        //关闭流
        outputStream.close();
        inputStream.close();

    }

}