package com.student.zhaokangwei;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.student.zhaokangwei.entity.Asset;
import com.student.zhaokangwei.entity.Role;
import com.student.zhaokangwei.entity.User;
import com.student.zhaokangwei.mapper.IAssetMapper;
import com.student.zhaokangwei.mapper.IRoleMapper;
import com.student.zhaokangwei.service.IRoleService;
import com.student.zhaokangwei.service.IUserService;
import com.student.zhaokangwei.utils.ViewUtils;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.swing.text.View;
import java.util.List;
import java.util.function.Function;

@Slf4j
@SpringBootTest
class StudySpringBootApplicationTests {
    @Resource
    IRoleMapper roleMapper;
    @Resource
    IRoleService roleService;
    @Resource
    IUserService userService;

//    @Test
//    void contextLoads() {
//        log.info("Hello world.");
//    }


    /**
     * 测试getById()
     * 根据主键查询单个标准对象
     */
    @Test
    public void testGetById(){
        Integer id=2;
       Role role= roleService.getById(id);
       log.info("查询结果：");
       log.info(ViewUtils.view(null, role, 200));

    }

    @Test
    public void testRoleMapper(){
      List<Role> roles=roleMapper.selectList(null);
        log.info(ViewUtils.view(null, roles, 200));
    }

    /**
     * 测试getOne()
     */
    @Test
    public void testGetOne(){
        String username="jack";
        String password="123456";
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();//创建一个条件构造器对象
        userQueryWrapper.select("id","username","realname","sex","age","mobile");//查询指定的字段
        userQueryWrapper.eq("username", username)
                .apply("password=MD5({0})", username+password);
        User user=userService.getOne(userQueryWrapper);
        log.info("结果：");
        log.info(ViewUtils.view(null, user, 200));
    }

    /**
     * 测试getObj()
     */
    @Test
    public void testGetObj() {
        String username = "jack";
        String password = "123456";
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.select("id");
        userQueryWrapper.eq("username", username)
                .apply("password=MD5({0})", username + password);
        Integer id = userService.getObj(userQueryWrapper, o -> Integer.parseInt(o.toString()));
        log.info("结果：");
        if (id == null) {
            log.info(ViewUtils.view("id为空，账号密码错误", null, 402));
        } else {
            log.info(ViewUtils.view("找到id了", id, 200));

        }
    }
//    @Resource
//    IAssetMapper assetMapper;
//    @Test
//    public void testAssetMapper(){
//        List<Asset> assets=assetMapper.select();
//        log.info(ViewUtils.view(null, assets, 200));
//    }
}
