package com.atyaoh.content.service;

import com.atyaoh.content.model.po.CourseTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CourseTeacherService extends IService<CourseTeacher> {

    /**
     * 列表查询
     *
     * @param courseId
     * @return List<CourseTeacher>
     */
    List<CourseTeacher> list(long courseId);
}
