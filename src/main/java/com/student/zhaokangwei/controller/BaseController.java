package com.student.zhaokangwei.controller;


import com.student.zhaokangwei.utils.ViewUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {

    @Resource
    HttpServletRequest request;
    @Resource
    HttpServletResponse response;

    /**
     * 成功
     *
     * @param message
     * @param value
     * @return
     */
    protected Object success(String message, Object value) {
        return ViewUtils.view(message, value, 200);
    }

    /**
     * 失败
     *
     * @param message
     * @param state
     * @return
     */
    protected Object fail(String message, int state) {
        response.setStatus(state);
        return ViewUtils.view(message, null, state);
    }

}