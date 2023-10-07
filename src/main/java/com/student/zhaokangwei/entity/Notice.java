package com.student.zhaokangwei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 公告实体类
 */
@Data
public class Notice implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String title;
    private String content;
    private LocalDateTime publishtime;

    public Notice(Integer id, Integer userId, String title, String content) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}