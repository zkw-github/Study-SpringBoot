package com.student.zhaokangwei.controller;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.student.zhaokangwei.entity.Notice;
import com.student.zhaokangwei.service.INoticeService;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 公告 Controller
 */
@Api(tags = "公告相关接口")
@RestController
@RequestMapping("admin/notice")
public class NoticeController extends BaseController {

    @Resource
    INoticeService noticeService;

    @ApiModel("公告参数实体")
    @Data
    static class NoticeParamBody {
        @ApiModelProperty("页码")
        @NotNull(message = "请传递页码：pageIndex")
        private Integer pageIndex;
        @ApiModelProperty("页大小，每一页多少条数据")
        @Max(value = 12, message = "页大小不能超过12")
        @NotNull(message = "请传递页大小：pageSize")
        private Integer pageSize;
        @ApiModelProperty("公告标题")
        private String title;
    }

    @ApiOperation("公告列表")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAuthority('notice_show')")
    @PostMapping
    public Object index(@Valid @RequestBody NoticeParamBody paramBody, BindingResult result) {
        return success(null, noticeService.pageNotice(paramBody.getPageIndex(),
                paramBody.getPageSize(), paramBody.getTitle()));
    }

    /**
     * 根据ID获取公告
     *
     * @param id
     * @return
     */
    @ApiOperation("根据ID获取公告")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAuthority('notice_show')")
    @GetMapping("get/{id}")
    public Object get(@ApiParam("公告ID") @PathVariable Integer id) {
        return success(null, noticeService.getById(id));
    }

    @ApiModel("公告保存参数实体")
    @Data
    static class NoticeSaveParamBody {
        @ApiModelProperty("公告ID")
        @NotNull(message = "ID必须传递")
        @TableId(value = "id", type = IdType.AUTO)
        private Integer id;
        @ApiModelProperty("公告标题")
        @NotBlank(message = "必须传递公告标题")
        private String title;
        @ApiModelProperty("公告内容")
        @NotBlank(message = "必须传递公告内容")
        private String content;
    }

    /**
     * 保存公告
     *
     * @param paramBody
     * @param result
     * @return
     */
    @ApiOperation("新增/修改公告")
    @PostMapping("save")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAuthority('notice_save')")
    public Object save(@Valid @RequestBody NoticeSaveParamBody paramBody, BindingResult result) {
        Integer userId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()); //从请求属性里面获取标识，然后转换为用户ID
        noticeService.saveNotice(new Notice(paramBody.getId(), userId, paramBody.getTitle(), paramBody.getContent()));
        return success("保存成功", null);
    }

    /**
     * 移除公告
     *
     * @param id
     * @return
     */
    @ApiOperation("移除公告")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAuthority('notice_remove')")
    @PostMapping("remove")
    public Object remove(@ApiParam("公告ID") @RequestParam Integer id) {
        noticeService.removeNotice(id);
        return success("移除成功", null);
    }

}