package com.student.zhaokangwei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.Notice;
import com.student.zhaokangwei.exception.ServiceValidationException;
import com.student.zhaokangwei.mapper.INoticeMapper;
import com.student.zhaokangwei.service.INoticeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 公告 Service 实现类
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<INoticeMapper, Notice> implements INoticeService {


    @Override
    public IPage<Map<String, Object>> pageNotice(Integer pageIndex, Integer pageSize, String title) {
        Page page = new Page(pageIndex, pageSize);  //page对象
        QueryWrapper<Notice> noticeQueryWrapper = new QueryWrapper<>(); //条件构造器对象
        noticeQueryWrapper.select("id", "title", "publishtime");
        if (title != null && !title.equals("")) {
            noticeQueryWrapper.like("title", title);
        }
        noticeQueryWrapper.orderByDesc("id");
        return pageMaps(page, noticeQueryWrapper);
    }


    @Override
    public void saveNotice(Notice notice) {
        if (notice.getId() == 0) {
            notice.setPublishtime(LocalDateTime.now()); //新增时，给发布时间赋值，赋值为当前时间  now()
            if (!save(notice)) {
                throw new ServiceValidationException("新增失败，原因未知", 402);
            }
        } else {
            QueryWrapper<Notice> queryWrapper = new QueryWrapper();
            queryWrapper.eq("id", notice.getId())
                    .eq("user_id", notice.getUserId());
            if (!update(notice, queryWrapper)) {
                throw new ServiceValidationException("修改失败，原因未知", 402);
            }
        }
    }

    @Override
    public void removeNotice(Integer id) {
        //直接从Security上下文对象中取出当前请求者的身份信息
        Integer userId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        QueryWrapper<Notice> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id)
                .eq("user_id", userId);
        if (!remove(queryWrapper)) {
            throw new ServiceValidationException("移除失败，原因未知", 402);
        }
    }


}