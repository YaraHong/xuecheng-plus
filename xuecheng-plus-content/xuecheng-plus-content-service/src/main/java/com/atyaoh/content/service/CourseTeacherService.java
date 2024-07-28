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

    /**
     * 添加或修改
     *
     * @param courseTeacher
     * @return
     */
    CourseTeacher addOrUpdate(CourseTeacher courseTeacher);

    /**
     * 删除
     *
     * @param courseId
     * @param teacherId
     * @return
     */
    void remove(long courseId, long teacherId);
}
