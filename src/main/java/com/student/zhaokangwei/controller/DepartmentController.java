package com.student.zhaokangwei.controller;

import com.student.zhaokangwei.entity.Department;
import com.student.zhaokangwei.service.IDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 部门类控制器
 */
@Api(tags = "部门相关接口")
@RestController
@RequestMapping("admin/department")
public class DepartmentController extends BaseController {

    @Resource
    IDepartmentService departmentService;

    @ApiOperation("部门列表")
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAuthority('dep_show')")
    //当前请求者只有拥有dep_show权限才能正常请求到该方法
    public Object list() {
        return success(null, departmentService.list());
    }

    @ApiOperation("新增/修改部门")
    @PostMapping("save")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAuthority('dep_save')")
    //当前请求者只有拥有dep_save权限才能正常请求到该方法
    public Object save(@Valid @RequestBody Department department, BindingResult result) {
        departmentService.save(department);
        return success("保存成功", null);
    }

    @ApiOperation("移除部门")
    @GetMapping("remove/{id}")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAuthority('dep_remove')")
    public Object remove(@ApiParam("部门ID") @PathVariable Integer id) {
        departmentService.remove(id);
        return success("移除成功", null);
    }

}