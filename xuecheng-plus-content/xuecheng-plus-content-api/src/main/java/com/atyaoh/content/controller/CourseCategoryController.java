package com.atyaoh.content.controller;

import com.atyaoh.content.model.po.CourseCategory;
import com.atyaoh.content.service.CourseCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程分类 前端控制器
 *
 * @author YaraHong
 */
@Api(value = "课程分类相关接口", tags = "课程分类相关接口")
@Slf4j
@RestController
@RequestMapping("course-category")
public class CourseCategoryController {

    @Resource
    private CourseCategoryService courseCategoryService;

    /**
     * 获取课程分类树
     *
     * @param
     * @return
     */
    @ApiOperation("获取课程分类树")
    @GetMapping("/tree-nodes")
    public List<CourseCategory> getTreeNode() {
        return courseCategoryService.queryTreeNodes("1");
    }
}
