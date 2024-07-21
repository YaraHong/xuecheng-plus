package com.atyaoh.content.controller;

import com.atyaoh.content.model.po.CourseTeacher;
import com.atyaoh.content.service.CourseTeacherService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程-教师关系表 前端控制器
 *
 * @author YaraHong
 */
@Slf4j
@RestController
@RequestMapping("courseTeacher")
public class CourseTeacherController {

    @Resource
    private CourseTeacherService courseTeacherService;

    /**
     * 列表查询
     *
     * @param courseId
     * @return List<CourseTeacher>
     */
    @ApiOperation("列表查询")
    @GetMapping("/list/{courseId}")
    public List<CourseTeacher> list(@PathVariable long courseId) {
        return courseTeacherService.list(courseId);
    }
}
