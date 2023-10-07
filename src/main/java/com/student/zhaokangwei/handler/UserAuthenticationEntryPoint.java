package com.student.zhaokangwei.handler;

import com.student.zhaokangwei.utils.ViewUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 当身份认证未通过时执行的操作
 */
@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {

        ViewUtils.print(response, "请携带有效的Token令牌", null, 401);

    }
}