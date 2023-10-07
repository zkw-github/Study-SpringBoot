package com.student.zhaokangwei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.student.zhaokangwei.entity.Notice;

import java.util.Map;

public interface INoticeService extends IService<Notice> {
    /**
     * 分页查询公告数据
     *
     * @param pageIndex
     * @param pageSize
     * @param title
     * @return
     */
    IPage<Map<String, Object>> pageNotice(Integer pageIndex, Integer pageSize, String title);

    /**
     * 保存公告信息
     */
    void saveNotice(Notice notice);

    /**
     * 根据公告ID移除公告信息
     *
     * @param id
     */
    void removeNotice(Integer id);
}
