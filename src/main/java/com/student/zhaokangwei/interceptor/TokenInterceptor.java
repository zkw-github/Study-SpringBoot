package com.student.zhaokangwei.interceptor;

import com.student.zhaokangwei.exception.ServiceValidationException;
import com.student.zhaokangwei.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token认证拦截器
 */
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    /**
     * 在请求前进行拦截，进行Token认证
     *
     * @param request
     * @param response
     * @param handler
     * @return true:放行  false:拒绝
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.debug("执行了TokenInterceptor拦截器.......");

        //1. 先从header里面获取token令牌
        String token = request.getHeader("Token");

        //2. 使用TokenUtils进行验证
        Object sign = TokenUtils.verify(token);
        if (sign == null) {
            //说明令牌验证未通过
            throw new ServiceValidationException("Token验证未通过", 401);
        }

        log.debug("Token认证通过，标识为：" + sign);
        request.setAttribute("sign", sign);
        return true;
    }
}
