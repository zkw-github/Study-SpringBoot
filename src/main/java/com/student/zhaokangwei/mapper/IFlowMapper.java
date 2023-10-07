package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.zhaokangwei.entity.Flow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * 流程Mapper
 */
@Mapper
public interface IFlowMapper extends BaseMapper<Flow> {


    @Select("select * from flow inner join act_re_procdef on flow.deployment_id=act_re_procdef.DEPLOYMENT_ID_ where ID_=#{processDefinitionId}")
    Map<String, Object> selectFlowAndProcdef(String processDefinitionId);

    @Select("select ID_ from act_re_procdef where DEPLOYMENT_ID_=#{deploymentId}")
    String selectDefinitionIdByDeployment(String deploymentId);

}