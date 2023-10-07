package com.student.zhaokangwei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.OvertimeType;
import com.student.zhaokangwei.mapper.IOvertimeTypeMapper;
import com.student.zhaokangwei.service.IOvertimeTypeService;
import org.springframework.stereotype.Service;

/**
 * 请假申请类型 Service实现类
 */
@Service
public class OvertimeTypeServiceImpl extends ServiceImpl<IOvertimeTypeMapper, OvertimeType>
        implements IOvertimeTypeService {
}
