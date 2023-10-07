package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.zhaokangwei.entity.Overtime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * 加班申请 Mapper
 */
@Mapper
public interface IOvertimeMapper extends BaseMapper<Overtime> {

    @Select("select overtime.id as 'id', starttime, endtime, reason, realname," +
            "overtime_state.name as 'state', overtime_type.name as 'type', dispose_reason,process_instance_id from overtime " +
            "inner join user on overtime.user_id=user.id " +
            "inner join overtime_state on overtime.state_id=overtime_state.id " +
            "inner join overtime_type on overtime.type_id=overtime_type.id " +
            "${ew.customSqlSegment}")
    Map<String, Object> getOvertimeJoinUser(@Param("ew") Wrapper queryWrapper);

    @Select("select overtime.id as 'id', starttime, endtime, reason, realname," +
            "overtime_state.name as 'state', overtime_type.name as 'type', dispose_reason,process_instance_id from overtime " +
            "inner join user on overtime.user_id=user.id " +
            "inner join overtime_state on overtime.state_id=overtime_state.id " +
            "inner join overtime_type on overtime.type_id=overtime_type.id " +
            "${ew.customSqlSegment}")
    IPage<Map<String, Object>> pageOvertimeJoinUser(Page page, @Param("ew") Wrapper queryWrapper);


}