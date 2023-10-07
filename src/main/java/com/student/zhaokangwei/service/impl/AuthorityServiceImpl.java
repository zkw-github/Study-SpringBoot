package com.student.zhaokangwei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.Authority;
import com.student.zhaokangwei.mapper.IAuthorityMapper;
import com.student.zhaokangwei.service.IAuthorityService;
import org.springframework.stereotype.Service;

/**
 * 权限字典 Service 实现类
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<IAuthorityMapper, Authority> implements IAuthorityService {
}