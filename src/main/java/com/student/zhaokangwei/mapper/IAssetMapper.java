package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.zhaokangwei.entity.Asset;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 资产类Mapper
 */
@Mapper
public interface IAssetMapper extends BaseMapper<Asset> {
    /**
     * 查询所有资产数据
     *
     * @return 资产列表
     */
    @Select("select asset.id as 'id', name,  user.realname as 'realname', role.name as 'rolename' from asset " +
            "inner join user on asset.user_id=user.id " +
            "inner join role on asset.role_id=role.id " +
            "${ew.customSqlSegment}")
    List<Map<String, Object>> selectAssetJoinDetAndRol(@Param("ew") Wrapper queryWrapper);

    /**
     * 资产分页数据
     *
     * @return
     */
    @Select("select id, name,type,state,mount,unity,price,discount_rate from asset " +
            "${ew.customSqlSegment}")
    IPage<Map<String, Object>> selectAssetJoinDetAndRolPage(Page page, @Param("ew") Wrapper queryWrapper);

    /**
     * 新增资产
     *
     * @param asset 资产对象
     * @return 影响行数
     */
    @Insert("insert into asset values(default,#{name},#{type},#{state},#{mount},#{unity},#{price},#{discountRate})")
    @Options(
            keyProperty = "id",
            useGeneratedKeys = true
    )
    int insertAsset(Asset asset);

    /**
     * 修改资产信息
     *
     * @param asset 资产对象
     * @return 影响行数
     */
    @Update("update asset set name=#{name},type=#{type},state=#{state},mount=#{mount},unity=#{unity},#{discount_rate} " +
            "where id=#{id}")
    int update(Asset asset);

    /**
     * 根据ID删除资产信息
     *
     * @param id 资产ID
     * @return 影响行数
     */
    @Delete("delete from asset where id=#{id}")
    int delete(Integer id);

}
