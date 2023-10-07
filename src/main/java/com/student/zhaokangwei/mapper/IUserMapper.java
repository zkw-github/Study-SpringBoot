package com.student.zhaokangwei.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.zhaokangwei.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 用户类Mapper
 */
@Mapper
public interface IUserMapper extends BaseMapper<User> {

    /**
     * 获取用户数据
     *
     * @return
     */
    @Select("select user.id as 'id', username, mobile, department.name as 'departmentname', role.name as 'rolename' from user " +
            "inner join department on user.department_id=department.id " +
            "inner join role on user.role_id=role.id " +
            "${ew.customSqlSegment}")
    List<Map<String, Object>> selectUserJoinDetAndRol(@Param("ew") Wrapper queryWrapper);

    /**
     * 分页获取用户数据
     *
     * @return
     */
    @Select("select user.id as 'id', username, sex,age,realname, mobile, department.name as 'departmentname', role.name as 'rolename' from user " +
            "inner join department on user.department_id=department.id " +
            "inner join role on user.role_id=role.id " +
            "${ew.customSqlSegment}")
    IPage<Map<String, Object>> selectUserJoinDetAndRolPage(Page page, @Param("ew") Wrapper queryWrapper);

    @Insert("insert into user values(default,#{username},MD5(CONCAT(#{username},#{password})),#{departmentId},#{roleId},#{realname},#{sex}," +
            "#{age},#{mobile},#{state},#{authority})")
    @Options(
            keyProperty = "id",
            useGeneratedKeys = true
    )
    int insertUser(User user);

    /**
     * 修改密码字段
     *
     * @param user
     * @return
     */
    @Update("update user set password=MD5(CONCAT(username,#{password})) where id=#{id};")
    int updatePassword(User user);

}