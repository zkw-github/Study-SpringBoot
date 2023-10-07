package com.student.zhaokangwei.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.student.zhaokangwei.entity.Asset;


public interface IAssetService extends IService<Asset> {
    /**
     * 分页查询资产数据
     *
     * @param pageIndex 页码
     * @param pageSize  页大小
     * @param name      用于查询的资产名称
     * @param type      用于查询的资产类型
     * @return
     */
    IPage pageAssetInfo(Integer pageIndex, Integer pageSize, String name, String type);

    /**
     * 保存资产信息
     *
     * @param asset
     */
    String saveAsset(Asset asset);

    /**
     * 根据ID移除资产
     *
     * @param id
     */
    void removeAsset(Integer id);
}
