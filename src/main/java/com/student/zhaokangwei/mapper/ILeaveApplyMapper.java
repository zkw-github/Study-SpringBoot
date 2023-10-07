package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.zhaokangwei.entity.LeaveApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * 请假申请 Mapper
 */
@Mapper
public interface ILeaveApplyMapper extends BaseMapper<LeaveApply> {

    @Select("select leave_apply.id as 'id', starttime, endtime, reason, realname," +
            "leave_apply_state.name as 'state', leave_apply_type.name as 'type', dispose_reason,process_instance_id from leave_apply " +
            "inner join user on leave_apply.user_id=user.id " +
            "inner join leave_apply_state on leave_apply.state_id=leave_apply_state.id " +
            "inner join leave_apply_type on leave_apply.type_id=leave_apply_type.id " +
            "${ew.customSqlSegment}")
    Map<String, Object> getLeaveApplyJoinUser(@Param("ew") Wrapper queryWrapper);

    @Select("select leave_apply.id as 'id', starttime, endtime, reason, realname," +
            "leave_apply_state.name as 'state', leave_apply_type.name as 'type', dispose_reason,process_instance_id from leave_apply " +
            "inner join user on leave_apply.user_id=user.id " +
            "inner join leave_apply_state on leave_apply.state_id=leave_apply_state.id " +
            "inner join leave_apply_type on leave_apply.type_id=leave_apply_type.id " +
            "${ew.customSqlSegment}")
    IPage<Map<String, Object>> pageLeaveApplyJoinUser(Page page, @Param("ew") Wrapper queryWrapper);


}