package com.student.zhaokangwei.service;

import com.alibaba.fastjson.JSONArray;


/**
 * 任务 Service
 */
public interface ITaskService {

    /**
     * 获取我的所有任务
     *
     * @return
     */
    JSONArray myList();

}