package com.student.zhaokangwei;

import com.student.zhaokangwei.service.IDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class DepartmentTests {

    @Resource
    private IDepartmentService departmentService;

    @Test
    public void remove(){
        log.info("删除部门：" + departmentService.remove(5));
    }
}
