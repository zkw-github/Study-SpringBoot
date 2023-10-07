package com.student.zhaokangwei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.LeaveApplyType;
import com.student.zhaokangwei.mapper.ILeaveApplyTypeMapper;
import com.student.zhaokangwei.service.ILeaveApplyTypeService;
import org.springframework.stereotype.Service;

/**
 * 请假申请类型 Service实现类
 */
@Service
public class LeaveApplyTypeServiceImpl extends ServiceImpl<ILeaveApplyTypeMapper, LeaveApplyType>
        implements ILeaveApplyTypeService {
}
