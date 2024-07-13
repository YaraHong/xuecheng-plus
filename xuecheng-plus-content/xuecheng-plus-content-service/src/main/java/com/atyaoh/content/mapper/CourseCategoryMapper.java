package com.atyaoh.content.mapper;

import com.atyaoh.content.model.po.CourseCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    /**
     * 查询课程分类树
     *
     * @param
     * @return List<CourseCategory>
     */
    List<CourseCategory> selectTreeNodes(String id);
}
