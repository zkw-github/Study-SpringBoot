package com.student.zhaokangwei.controller;

import com.student.zhaokangwei.entity.User;
import com.student.zhaokangwei.service.IUserService;
import com.student.zhaokangwei.utils.TokenUtils;
import io.swagger.annotations.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.ValidationException;
import java.util.Date;
import java.util.Map;

@Api(tags = "登录相关接口")
@Slf4j
@RestController
public class IndexController extends BaseController {
    @ApiOperation("测试")
    @PostMapping("test")
    public Object test(@ApiParam("任意参数") @RequestParam String param) {
        return success("hello world,这是我第一个SpringBoot项目，我接收到一个参数是：" + param, null);
    }

    @Resource
    IUserService userService;

    @ApiModel("登录参数实体")
    @Data
    static class LoginParam {
        @ApiModelProperty("用户名")
        @NotBlank(message = "用户名不能为空")
        private String username;
        @ApiModelProperty("密码")
        @NotBlank(message = "密码不能为空")
        private String password;
    }


    /**
     * 登录
     *
     * @param param
     * @return
     */
    @ApiOperation("登录")
    @PostMapping("login")
    public Object login(@Valid @RequestBody LoginParam param, BindingResult result) throws ValidationException {
        User user = new User(param.getUsername(), param.getPassword());
        Map<String, Object> map = userService.login(user);
        Date issuedTime = new Date();
        Date expiresTime = new Date(issuedTime.getTime() + 1000 * 60 * 60 * 72);  //过期时间为72个小时
        map.put("token", TokenUtils.generate(map.get("id"), issuedTime, expiresTime));
        return success("登录成功", map);
    }

}