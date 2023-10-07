package com.student.zhaokangwei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.student.zhaokangwei.entity.Role;

import java.util.List;

/**
 * 角色 Service
 */
public interface IRoleService extends IService<Role> {

    /**
     * 获取角色列表
     *
     * @return
     */
    List<Role> listRole();


}