package com.student.zhaokangwei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.LeaveApplyState;
import com.student.zhaokangwei.mapper.ILeaveApplyStateMapper;
import com.student.zhaokangwei.service.ILeaveApplyStateService;
import org.springframework.stereotype.Service;

/**
 * 请假申请状态 Service实现类
 */
@Service
public class LeaveApplyStateServiceImpl extends ServiceImpl<ILeaveApplyStateMapper, LeaveApplyState>
        implements ILeaveApplyStateService {
}