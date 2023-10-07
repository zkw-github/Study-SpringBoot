package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.zhaokangwei.entity.OvertimeType;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * OvertimeType Mapper
 */
@Mapper
@CacheNamespace
public interface IOvertimeTypeMapper extends BaseMapper<OvertimeType> {
}
