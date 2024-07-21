package com.atyaoh.content.controller;

import com.atyaoh.content.model.dto.SaveTeachplanDto;
import com.atyaoh.content.model.dto.TeachplanDto;
import com.atyaoh.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId) {
        return teachplanService.queryTreeNodes(courseId);
    }

    /**
     * 新增或修改
     *
     * @param
     * @return
     */
    @ApiOperation("新增或修改")
    @PostMapping
    public void addOrEdit(@RequestBody SaveTeachplanDto saveTeachplanDto) {
        teachplanService.saveOrUpdate(saveTeachplanDto);
    }

    /**
     * 删除
     *
     * @param
     * @return
     */
    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        teachplanService.delete(id);
    }
}
