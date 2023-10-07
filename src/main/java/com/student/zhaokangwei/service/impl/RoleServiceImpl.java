package com.student.zhaokangwei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.Role;
import com.student.zhaokangwei.mapper.IRoleMapper;
import com.student.zhaokangwei.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<IRoleMapper, Role> implements IRoleService {

    @Override
    public List<Role> listRole() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.gt("id", 1);
        return list(queryWrapper);
    }

}