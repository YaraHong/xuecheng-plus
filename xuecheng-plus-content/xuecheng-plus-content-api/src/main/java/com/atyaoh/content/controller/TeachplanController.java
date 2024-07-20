package com.atyaoh.content.controller;

import com.atyaoh.content.model.dto.TeachplanDto;
import com.atyaoh.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程计划 前端控制器
 *
 * @author YaraHong
 */
@Api(value = "课程计划相关接口", tags = "课程计划相关接口")
@Slf4j
@RestController
@RequestMapping("teachplan")
public class TeachplanController {

    @Resource
    private TeachplanService teachplanService;

    /**
     * 树结构查询
     *
     * @param courseId
     * @return TeachplanDto
     */
    @ApiOperation("树结构查询")
    @GetMapping("/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable long courseId) {
        return teachplanService.queryTreeNodes(courseId);
    }

}
