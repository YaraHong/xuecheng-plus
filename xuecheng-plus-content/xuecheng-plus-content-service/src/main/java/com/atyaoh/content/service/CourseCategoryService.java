package com.atyaoh.content.service;

import com.atyaoh.content.model.po.CourseCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CourseCategoryService extends IService<CourseCategory> {

    /**
     * 查询课程分类树
     *
     * @param id
     * @return List<CourseCategory>
     */
    List<CourseCategory> queryTreeNodes(String id);

}
