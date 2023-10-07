package com.student.zhaokangwei;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.zhaokangwei.entity.Role;
import com.student.zhaokangwei.entity.User;
import com.student.zhaokangwei.mapper.IUserMapper;
import com.student.zhaokangwei.service.IRoleService;
import com.student.zhaokangwei.service.IUserService;
import com.student.zhaokangwei.utils.ViewUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class MyBatisPlusTests {

    @Resource
    IUserService userService;

    /**
     * 测试 getById()
     */
    @Test
    public void testGetById() {
        Integer id = 2; //用户ID
        User user = userService.getById(id);
        log.info("结果：");
        log.info(ViewUtils.view(null, user, 200));
    }

    /**
     * 测试 getOne()
     */
    @Test
    public void testGetOne() {
        String username = "jack";
        String password = "123456";
        QueryWrapper<User> queryWrapper = new QueryWrapper();   //条件构造器
        queryWrapper.select("id", "username", "realname", "mobile");  //查询指定字段
        queryWrapper.eq("username", username)
                .apply("password = MD5({0})", username + password);
        User user = userService.getOne(queryWrapper);
        log.info("结果：");
        log.info(ViewUtils.view(null, user, 200));
    }

    /**
     * 测试 getObj()
     */
    @Test
    public void testGetObj() {
        String username = "jack";
        String password = "123456";
        QueryWrapper<User> queryWrapper = new QueryWrapper();   //条件构造器
        queryWrapper.select("id");  //查询指定字段
        queryWrapper.eq("username", username)
                .apply("password = MD5({0})", username + password);
        Integer id = userService.getObj(queryWrapper, o -> Integer.parseInt(o.toString()));
        log.info("结果：");
        if (id == null) {
            log.info(ViewUtils.view("ID为空，用户名密码错误", null, 402));
        } else {
            log.info(ViewUtils.view("查询到ID了", id, 200));
        }
    }

    /**
     * 测试 getMap()
     */
    @Test
    public void testGetMap() {
        String username = "jack";
        String password = "123456";
        QueryWrapper<User> queryWrapper = new QueryWrapper();   //条件构造器
        queryWrapper.select("id", "username", "realname", "mobile");  //查询指定字段
        queryWrapper.eq("username", username)
                .apply("password = MD5({0})", username + password);
        Map<String, Object> map = userService.getMap(queryWrapper);
        log.info("结果：");
        log.info(ViewUtils.view(null, map, 200));
    }


    /**
     * 测试 list()
     *
     * sql where1: id > ? AND role_id <> ? OR (age <= ? AND username LIKE ?) AND department_id IN (select*****
     * sql where1: id > ? AND role_id <> ? AND (age <= ? OR username LIKE ?) AND department_id IN (select*****
     *
     */
    @Test
    public void testList() {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.select("id", "username", "realname", "mobile");  //查询指定字段
        queryWrapper.gt("id", 1);
        queryWrapper.ne("role_id", 1);
//        queryWrapper.or(wrapper -> wrapper.le("age", 45)
//                                          .like("username", "zh"));
        queryWrapper.and(wrapper -> wrapper.le("age", 45)
                .or()
                .like("username", "zh"));
//        queryWrapper.le("age", 45);
//        queryWrapper.like("username", "zh");
        queryWrapper.inSql("department_id", "select id from department where name in('财务室','后勤部')");
        queryWrapper.orderByAsc("id");
        queryWrapper.last("limit 5");
        List<User> users = userService.list(queryWrapper);
        log.info("结果：");
        log.info(ViewUtils.view(null, users, 200));
    }

    /**
     * 测试 listObj()
     */
    @Test
    public void testListObj() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("id");  //查询指定字段
        queryWrapper.gt("id", 1);
        List<Integer> ids = userService.listObjs(queryWrapper, o -> Integer.parseInt(o.toString()));
        log.info("结果：");
        log.info(ViewUtils.view(null, ids, 200));
    }


    /**
     * 测试 listMap()
     */
    @Test
    public void testListMap() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("id", "username", "realname", "mobile");  //查询指定字段
        queryWrapper.gt("id", 1);
        List<Map<String, Object>> mapList = userService.listMaps(queryWrapper);
        log.info("结果：");
        log.info(ViewUtils.view(null, mapList, 200));
    }

    /**
     * 测试 count()
     */
    @Test
    public void testCount() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.gt("id", 1);
        Long count = userService.count(queryWrapper);
        log.info("结果：");
        log.info(ViewUtils.view(null, count, 200));
    }


    /**
     * 测试 page()
     */
    @Test
    public void testPage() {
        Integer pageIndex = 2;  //页码
        Integer pageSize = 3;   //页大小

        Page page = new Page(pageIndex, pageSize);  //创建一个Page对象，该对象包含了页码、页大小等属性
        QueryWrapper queryWrapper = new QueryWrapper(); //条件构造器
        queryWrapper.select("id", "username", "realname", "mobile");  //查询指定字段
        queryWrapper.gt("id", 1);

        IPage<User> userIPage = userService.page(page, queryWrapper);
        log.info("结果：");
        log.info(ViewUtils.view(null, userIPage, 200));
    }

    /**
     * 测试 pageMaps()
     */
    @Test
    public void testPageMaps() {
        Integer pageIndex = 2;  //页码
        Integer pageSize = 3;   //页大小

        Page page = new Page(pageIndex, pageSize);  //创建一个Page对象，该对象包含了页码、页大小等属性
        QueryWrapper queryWrapper = new QueryWrapper(); //条件构造器
        queryWrapper.select("id", "username", "realname", "mobile");  //查询指定字段
        queryWrapper.gt("id", 1);

        IPage<Map<String, Object>> userIPage = userService.pageMaps(page, queryWrapper);
        log.info("结果：");
        log.info(ViewUtils.view(null, userIPage, 200));
    }

    @Resource
    IRoleService roleService;

    /**
     * 测试 save()
     */
    @Test
    public void testSave() {
//        User user = new User("caocao", "MD5('123456')", );
        Role role = new Role(0, "打更的");
        boolean success = roleService.save(role);
        if (success) {
            log.info(ViewUtils.view("新增成功", null, 200));
            log.info("当前插入的对象的ID：" + role.getId());
        }
    }

    /**
     * 测试 updateById()
     */
    @Test
    public void testUpdateById() {
//        Role role = new Role(16, "跑堂的");
//        boolean success = roleService.updateById(role);
//        if (success) {
//            log.info(ViewUtils.view("修改成功", null, 200));
//        }

        User user = userService.getById(2);
        user.setAge((short) 22);
        log.info("结果：" + userService.updateById(user));

    }


    /**
     * 测试 update
     */
    @Test
    public void testUpdate() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.gt("age", 40);

        User user = new User();
        user.setAuthority("articleList,articleAdd");

        log.info("结果：" + userService.update(user, queryWrapper));
    }

    /**
     * 测试 removeById
     */
    @Test
    public void testRemoveById() {
        log.info("结果：" + userService.removeById(11));
    }

    /**
     * 测试 removeByIds
     */
    @Test
    public void testRemoveByIds() {
        List<Integer> ids = Arrays.asList(2, 4, 5, 6, 7, 8, 11);
        log.info("结果：" + userService.removeByIds(ids));
    }


    /**
     * 测试 remove复杂用法
     */
    @Test
    public void testRemove() {
        /*
        条件1：id属于ids集合里面的数据
        条件2：性别等于女
         */
        List<Integer> ids = Arrays.asList(2, 4, 5, 6, 7, 8, 11);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("id", ids);
        queryWrapper.eq("sex", "女");
        log.info("结果：" + userService.remove(queryWrapper));
    }

    @Resource
    IUserMapper userMapper;

    /**
     * 测试 自定义SQL
     */
    @Test
    public void testMyCustomSQL() {
        Integer pageIndex = 1;  //页码
        Integer pageSize = 3;   //页大小

        Integer minAge = 10;    //最小年龄
        String sex = "男";       //性别
        String realname = null;  //姓名

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.gt("user.id", 1);
        queryWrapper.ge("age", minAge);
        queryWrapper.eq("sex", sex);
        if (realname != null) queryWrapper.likeRight("realname", realname);
        queryWrapper.between("age", 20, 50);
        queryWrapper.in("department.name", Arrays.asList("财务室", "后勤部"));

//        List<Map<String, Object>> maps = userMapper.selectUserJoinDetAndRol(queryWrapper);    //获取用户列表（无分页）
        Page page = new Page(pageIndex, pageSize);  //创建一个Page对象，该对象包含了页码、页大小等属性
        IPage<Map<String, Object>> userPage = userMapper.selectUserJoinDetAndRolPage(page, queryWrapper);

        log.info("结果：");
        log.info(ViewUtils.view(null, userPage, 200));
    }


}
