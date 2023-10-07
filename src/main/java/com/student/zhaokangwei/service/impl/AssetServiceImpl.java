package com.student.zhaokangwei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.Asset;
import com.student.zhaokangwei.exception.ServiceValidationException;
import com.student.zhaokangwei.mapper.IAssetMapper;
import com.student.zhaokangwei.service.IAssetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class AssetServiceImpl extends ServiceImpl<IAssetMapper, Asset> implements IAssetService {
    @Resource
    IAssetMapper assetMapper;

    @Override
    public IPage pageAssetInfo(Integer pageIndex, Integer pageSize, String name, String type) {
        Page page = new Page(pageIndex, pageSize);
        QueryWrapper<Asset> assetQueryWrapper = new QueryWrapper<>();
        assetQueryWrapper.select("id", "name", "type", "state", "mount", "unity", "price", "discount_rate");
        assetQueryWrapper.gt("id", 1);
        if (name != null && !name.equals("")) {

            assetQueryWrapper.and(warpper -> warpper.like("name", name));

        }
        if (type != null && !type.equals("")) {
            assetQueryWrapper.and(warpper -> warpper.like("type", type));
        }
        return assetMapper.selectAssetJoinDetAndRolPage(page, assetQueryWrapper);
    }

    @Override
    public String saveAsset(Asset asset) {
        //1.先判断资产名是否存在
        QueryWrapper<Asset> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", asset.getName())
                .ne("id", asset.getId());
        if (count(queryWrapper) > 0) {
            throw new ServiceValidationException("资产名已存在", 402);
        }
        if (asset.getId() == 0) {
            //新增
            if (assetMapper.insertAsset(asset) == 0) {
                throw new ServiceValidationException("新增失败", 402);
            }
            log.info("新增后资产的ID：" + asset.getId());
            return "新增成功";
        } else {
            //修改
            if (!updateById(asset)) {
                throw new ServiceValidationException("修改失败，可能ID不存在", 402);
            }
            return "修改成功";
        }
    }

    @Override
    public void removeAsset(Integer id) {
        if (!removeById(id)) {
            throw new ServiceValidationException("移除失败，可能ID不存在", 402);
        }
    }
}
