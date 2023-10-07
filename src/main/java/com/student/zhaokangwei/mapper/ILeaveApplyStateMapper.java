package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.zhaokangwei.entity.LeaveApplyState;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * LeaveApplyState Mapper
 */
@Mapper
@CacheNamespace
public interface ILeaveApplyStateMapper extends BaseMapper<LeaveApplyState> {
}