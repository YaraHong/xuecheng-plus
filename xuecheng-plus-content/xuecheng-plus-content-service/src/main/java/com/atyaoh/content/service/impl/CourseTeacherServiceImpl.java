package com.atyaoh.content.service.impl;

import com.atyaoh.base.exception.CustomException;
import com.atyaoh.content.mapper.CourseBaseMapper;
import com.atyaoh.content.mapper.CourseTeacherMapper;
import com.atyaoh.content.model.po.CourseBase;
import com.atyaoh.content.model.po.CourseTeacher;
import com.atyaoh.content.service.CourseTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper, CourseTeacher> implements CourseTeacherService {

    @Resource
    private CourseTeacherMapper courseTeacherMapper;
    @Resource
    private CourseBaseMapper courseBaseMapper;

    /**
     * 列表查询
     *
     * @param courseId
     * @return List<CourseTeacher>
     */
    @Override
    public List<CourseTeacher> list(long courseId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CourseTeacher::getCourseId, courseId)
                .orderBy(true, true, CourseTeacher::getCreateDate);
        return courseTeacherMapper.selectList(queryWrapper);
    }

    /**
     * 添加
     *
     * @param courseTeacher
     * @return
     */
    @Override
    public CourseTeacher add(CourseTeacher courseTeacher) {
        CourseBase courseBase = courseBaseMapper.selectById(courseTeacher.getCourseId());
        if (courseBase == null) {
            CustomException.cast("此课程不存在");
        }
        if (courseBase.getCompanyId() != null && courseBase.getCompanyId() != 1232141425L) {// TODO 后期获取登录信息中的机构id
            CustomException.cast("只能为本机构课程添加老师");
        }
        // 添加
        int id = courseTeacherMapper.insert(courseTeacher);
        // 返回老师信息
        return courseTeacherMapper.selectById(id);
    }
}
