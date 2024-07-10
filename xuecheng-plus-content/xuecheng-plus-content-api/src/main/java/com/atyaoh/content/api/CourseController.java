package com.atyaoh.content.api;

import com.atyaoh.base.model.PageParams;
import com.atyaoh.base.model.PageResult;
import com.atyaoh.content.model.dto.QueryCourseParamsDto;
import com.atyaoh.content.model.po.CourseBase;
import com.atyaoh.content.model.vo.CourseInfoVo;
import com.atyaoh.content.service.CourseBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

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
        return courseBaseService.list(pageParams, queryDto);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return CourseBase
     */
    @ApiOperation("根据id查询")
    @GetMapping("/{id}")
    public CourseInfoVo getById(@PathVariable @NotNull long id) {
        return courseBaseService.getById(id);
    }
}
