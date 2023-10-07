package com.student.zhaokangwei.controller;

import com.student.zhaokangwei.entity.Asset;
import com.student.zhaokangwei.service.IAssetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 资产控制器
 */
@Api(tags = "资产相关接口")
@RestController
@RequestMapping("admin/asset")
public class AssetController extends BaseController {

    @Resource
    IAssetService assetService;

    @ApiModel("资产列表参数实体")
    @Data
    static class AssetListParamBody {
        @ApiModelProperty("页码")
        @NotNull(message = "必须传递pageIndex")
        private Integer pageIndex;
        @ApiModelProperty("页大小，每一页多少条数据")
        @Max(value = 20, message = "页大小不能超过20条")
        @NotNull(message = "必须传递pageSize")
        private Integer pageSize;
        @ApiModelProperty("资产名称")
        private String name;
        @ApiModelProperty("资产类型")
        private String type;
    }

    @ApiOperation("资产列表")
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAnyAuthority('asset_show')")
//当前请求者只有拥有asset_show权限才能正常请求到该方法
    //当前请求者要么拥有user_show权限，要么拥有admin、boss这两个角色
    public Object index(@Valid @RequestBody AssetController.AssetListParamBody paramBody, BindingResult result) {
        return success(null, assetService.pageAssetInfo(paramBody.getPageIndex(),
                paramBody.getPageSize(), paramBody.getName(), paramBody.getType()));
    }

    @ApiOperation("根据ID获取资产")
    @GetMapping("get/{id}")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAuthority('asset_show')")//当前请求者只有拥有asset_show权限才能正常请求到该方法
    public Object get(@PathVariable Integer id) {
        return success(null, assetService.getById(id));
    }

    @ApiOperation("新增/修改资产")
    @PostMapping("save")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAuthority('user_save')")//当前请求者只有拥有asset_save权限才能正常请求到该方法
    public Object save(@Valid @RequestBody Asset asset, BindingResult result) {
        return success(assetService.saveAsset(asset), null);
    }

    @ApiOperation("移除资产")
    @PostMapping("remove")
    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_boss') OR hasAuthority('asset_remove')")
//当前请求者只有拥有asset_remove权限才能正常请求到该方法
    public Object remove(@RequestParam Integer id) {
        assetService.removeAsset(id);
        return success("移除成功", null);
    }

}
