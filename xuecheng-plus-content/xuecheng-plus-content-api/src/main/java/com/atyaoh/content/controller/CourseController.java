package com.atyaoh.content.controller;

import com.atyaoh.base.model.PageParams;
import com.atyaoh.base.model.PageResult;
import com.atyaoh.content.model.dto.AddCourseDto;
import com.atyaoh.content.model.dto.CourseBaseInfoDto;
import com.atyaoh.content.model.dto.EditCourseDto;
import com.atyaoh.content.model.dto.QueryCourseParamsDto;
import com.atyaoh.content.model.po.CourseBase;
import com.atyaoh.content.service.CourseBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 课程信息编辑接口
 *
 * @author YaraHong
 */
@Api(value = "课程信息编辑接口", tags = "课程信息编辑接口")
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseBaseService courseBaseService;

    /**
     * 分页查询
     *
     * @param pageParams
     * @param queryDto
     * @return PageResult<CourseBase>
     */
    @ApiOperation("分页查询")
    @PostMapping("/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryDto) {
        return courseBaseService.queryCourseBaseInfo(pageParams, queryDto);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return CourseBase
     */
    @ApiOperation("根据id查询")
    @GetMapping("/{id}")
    public CourseBaseInfoDto getById(@PathVariable long id) {
        return courseBaseService.getCourseBaseInfo(id);
    }

    /**
     * 添加
     *
     * @param addCourseDto
     * @return CourseBaseInfoDto
     */
    @ApiOperation("添加")
    @PostMapping
    public CourseBaseInfoDto add(@RequestBody @Validated AddCourseDto addCourseDto) {
        return courseBaseService.addCourse(addCourseDto);
    }

    /**
     * 修改
     *
     * @param editCourseDto
     * @return
     */
    @ApiOperation("修改")
    @PutMapping
    public CourseBaseInfoDto edit(@RequestBody @Validated EditCourseDto editCourseDto) {
        return courseBaseService.editCourse(editCourseDto, 200202L);
    }
}
