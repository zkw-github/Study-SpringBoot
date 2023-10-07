package com.student.zhaokangwei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.OvertimeState;
import com.student.zhaokangwei.mapper.IOvertimeStateMapper;
import com.student.zhaokangwei.service.IOvertimeStateService;
import org.springframework.stereotype.Service;

/**
 * 加班申请状态 Service实现类
 */
@Service
public class OvertimeStateServiceImpl extends ServiceImpl<IOvertimeStateMapper, OvertimeState>
        implements IOvertimeStateService {
}