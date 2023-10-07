package com.student.zhaokangwei.service;


import com.student.zhaokangwei.entity.Department;

import java.util.List;

/**
 * 部门业务接口
 */

public interface IDepartmentService {
    /**
     * 获取所有部门
     *
     * @return
     */
    List<Department> list();

    /**
     * 保存部门信息
     *
     * @param department
     * @return
     */
    boolean save(Department department);

    /**
     * 移除部门信息
     *
     * @param id
     * @return
     */
    boolean remove(Integer id);

}
