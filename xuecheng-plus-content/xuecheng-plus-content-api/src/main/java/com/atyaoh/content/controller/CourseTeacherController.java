package com.atyaoh.content.controller;

import com.atyaoh.content.model.po.CourseTeacher;
import com.atyaoh.content.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程-教师关系表 前端控制器
 *
 * @author YaraHong
 */
@Api(value = "课程-老师信息相关接口", tags = "课程-老师信息相关接口")
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

    /**
     * 添加或修改
     *
     * @param courseTeacher
     * @return
     */
    @ApiOperation("添加")
    @PostMapping
    public CourseTeacher addOrUpdate(@RequestBody CourseTeacher courseTeacher) {
        return courseTeacherService.addOrUpdate(courseTeacher);
    }

    /**
     * 删除
     *
     * @param courseId
     * @param teacherId
     * @return
     */
    @ApiOperation("删除")
    @DeleteMapping("/course/{courseId}/{teacherId}")
    public void delete(@PathVariable("courseId") long courseId, @PathVariable("teacherId") long teacherId) {
        courseTeacherService.remove(courseId, teacherId);
    }
}
