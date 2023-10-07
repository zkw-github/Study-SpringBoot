package com.student.zhaokangwei.controller;

import com.student.zhaokangwei.entity.User;
import com.student.zhaokangwei.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Api(tags = "用户相关接口")
@Slf4j
@RestController
@RequestMapping("admin/user")
public class UserController extends BaseController {

    @Resource
    IUserService userService;

    @ApiModel("用户列表参数实体")
    @Data
    static class UserListParamBody {
        @ApiModelProperty("页码")
        @NotNull(message = "必须传递pageIndex")
        private Integer pageIndex;
        @ApiModelProperty("页大小，每一页多少条数据")
        @Max(value = 20, message = "页大小不能超过20条")
        @NotNull(message = "必须传递pageSize")
        private Integer pageSize;
        @ApiModelProperty("部门ID")
        private Integer departmentId;
        @ApiModelProperty("角色ID")
        private Integer roleId;
        @ApiModelProperty("用户名")
        private String username;
    }

    @ApiOperation("用户列表")
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAnyAuthority('user_show')")
    //当前请求者要么拥有user_show权限，要么拥有admin、boss这两个角色
    public Object index(@Valid @RequestBody UserListParamBody paramBody, BindingResult result) {
        return success(null, userService.pageUserInfo(paramBody.getPageIndex(),
                paramBody.getPageSize(), paramBody.getDepartmentId(), paramBody.getRoleId(), paramBody.getUsername()));
    }

    @ApiOperation("根据ID获取用户")
    @GetMapping("get/{id}")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAuthority('user_show')")
    public Object get(@PathVariable Integer id) {
        return success(null, userService.getById(id));
    }

    @ApiOperation("新增/修改用户")
    @PostMapping("save")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAuthority('user_save')")
    public Object save(@Valid @RequestBody User user, BindingResult result) {
        return success(userService.saveUser(user), null);
    }

    @ApiOperation("重置密码")
    @GetMapping("resetPwd/{id}")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAuthority('user_resetPwd')")
    public Object resetPwd(@PathVariable Integer id) {
        return success(userService.resetPassword(id), null);
    }

    @ApiOperation("移除用户")
    @PostMapping("remove")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAuthority('user_remove')")
    public Object remove(@RequestParam Integer id) {
        userService.removeUser(id);
        return success("移除成功", null);
    }


}