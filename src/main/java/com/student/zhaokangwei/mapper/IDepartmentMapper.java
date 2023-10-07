package com.student.zhaokangwei.mapper;

import com.student.zhaokangwei.entity.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IDepartmentMapper {

    /**
     * 查询所有部门
     *
     * @return
     */
    @Select("select * from department")
    List<Department> select();


    /**
     * 根据部门名称查询数量
     *
     * @param name
     * @return
     */
    @Select("select count(0) from department where name=#{name}")
    int selectCountByName(String name);

    /**
     * 新增部门
     *
     * @param department
     * @return
     */
    @Insert("insert into department values(default, #{name})")
    int insert(Department department);

    /**
     * 修改部门
     *
     * @param department
     * @return
     */
    @Update("update department set name=#{name} where id=#{id}")
    int update(Department department);

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    @Delete("delete from department where id=#{id}")
    int delete(Integer id);
}