package com.student.zhaokangwei.controller;

import com.student.zhaokangwei.service.IAuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 权限字典 Controller
 */
@Api(tags = "权限字典相关接口")
@RestController
@RequestMapping("admin/authority")
public class AuthorityController extends BaseController {

    @Resource
    IAuthorityService authorityService;

    @ApiOperation("权限字典列表")
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss')")
    public Object index() {
        return success(null, authorityService.list());
    }

}