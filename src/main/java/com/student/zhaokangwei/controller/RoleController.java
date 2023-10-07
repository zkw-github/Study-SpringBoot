package com.student.zhaokangwei.controller;

import com.student.zhaokangwei.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 角色类控制器
 */
@Api(tags = "角色相关接口")
@RestController
@RequestMapping("admin/role")
public class RoleController extends BaseController {

    @Resource
    IRoleService roleService;


    @ApiOperation("角色列表")
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss')")
    public Object list() {
        return success(null, roleService.listRole());
    }
}