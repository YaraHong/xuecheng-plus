package com.atyaoh.content;


import com.atyaoh.base.model.PageParams;
import com.atyaoh.base.model.PageResult;
import com.atyaoh.content.model.dto.QueryCourseParamsDTO;
import com.atyaoh.content.model.po.CourseBase;
import com.atyaoh.content.service.CourseBaseService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class CourseBaseServiceTest {

    @Resource
    private CourseBaseService courseBaseService;

    @Test
    void test1() {
        PageParams pageParams = new PageParams();
        QueryCourseParamsDTO dto = new QueryCourseParamsDTO();
        dto.setCourseName("汉语言");
        dto.setAuditStatus("1");
        dto.setPublishStatus("1");
        PageResult<CourseBase> pageResult = courseBaseService.list(pageParams, dto);
        System.out.println(pageResult);
    }
}
