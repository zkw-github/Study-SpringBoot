package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.zhaokangwei.entity.Authority;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限字典 Mapper
 */
@Mapper
public interface IAuthorityMapper extends BaseMapper<Authority> {
}