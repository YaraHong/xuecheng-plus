package com.atyaoh.content.api;

import com.atyaoh.base.model.PageParams;
import com.atyaoh.base.model.PageResult;
import com.atyaoh.content.model.dto.QueryCourseParamsDTO;
import com.atyaoh.content.model.po.CourseBase;
import com.atyaoh.content.service.CourseBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 课程信息编辑接口
 *
 * @author YaraHong
 */
@Api(value = "课程信息编辑接口", tags = "课程信息编辑接口")
@RestController
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
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDTO queryDto) {
        return courseBaseService.list(pageParams, queryDto);
    }
}
