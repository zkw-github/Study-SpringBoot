package com.student.zhaokangwei.service.impl;


import com.student.zhaokangwei.entity.Department;
import com.student.zhaokangwei.exception.ServiceValidationException;
import com.student.zhaokangwei.mapper.IDepartmentMapper;
import com.student.zhaokangwei.service.IDepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {
    @Resource
    IDepartmentMapper departmentMapper;

    /**
     * 获取部门列表
     *
     * @return
     */
    @Override
    public List<Department> list() {
        return departmentMapper.select();
    }

    @Override
    public boolean save(Department department) {
        //判断该部门是否已存在
        if (departmentMapper.selectCountByName(department.getName()) > 0) {
            throw new ServiceValidationException("该部门名称已存在，请修改", 402);
        }

        if (department.getId() == 0) {
            //执行新增
            return departmentMapper.insert(department) > 0;
        } else {
            if (departmentMapper.update(department) == 0) {
                throw new ServiceValidationException("传递的部门ID不存在，请检查", 402);
            }
            return true;
        }

    }

    @Override
    public boolean remove(Integer id) {
        if (departmentMapper.delete(id) == 0) {
            //未删除到任何数据
            throw new ServiceValidationException("传递的部门ID不存在，请检查", 402);
        }
        return true;
    }
}
