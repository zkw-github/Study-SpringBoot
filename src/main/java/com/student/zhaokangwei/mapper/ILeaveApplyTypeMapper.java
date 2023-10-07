package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.zhaokangwei.entity.LeaveApplyType;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * LeaveApplyType Mapper
 */
@Mapper
@CacheNamespace
public interface ILeaveApplyTypeMapper extends BaseMapper<LeaveApplyType> {
}
