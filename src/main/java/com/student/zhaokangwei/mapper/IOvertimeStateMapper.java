package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.zhaokangwei.entity.OvertimeState;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * OvertimeState Mapper
 */
@Mapper
@CacheNamespace
public interface IOvertimeStateMapper extends BaseMapper<OvertimeState> {
}