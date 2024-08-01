package com.atyaoh.content.service;

import com.atyaoh.base.model.PageParams;
import com.atyaoh.base.model.PageResult;
import com.atyaoh.content.model.dto.AddCourseDto;
import com.atyaoh.content.model.dto.CourseBaseInfoDto;
import com.atyaoh.content.model.dto.EditCourseDto;
import com.atyaoh.content.model.dto.QueryCourseParamsDto;
import com.atyaoh.content.model.po.CourseBase;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程基本信息 服务类
 * </p>
 *
 * @author YaraHong
 * @since 2024-07-09
 */
public interface CourseBaseService extends IService<CourseBase> {
    /**
     * 分页查询
     *
     * @param pageParams
     * @param queryDto
     * @return PageResult<CourseBase>
     */
    PageResult<CourseBase> queryCourseBaseInfo(PageParams pageParams, QueryCourseParamsDto queryDto);

    /**
     * 根据id查询
     *
     * @param id
     * @return CourseInfoVo
     */
    CourseBaseInfoDto getCourseBaseInfo(long id);

    /**
     * 添加
     *
     * @param addCourseDto
     * @return CourseBaseInfoDto
     */
    CourseBaseInfoDto addCourse(AddCourseDto addCourseDto);

    /**
     * 修改
     *
     * @param editCourseDto
     * @return CourseBaseInfoDto
     */
    CourseBaseInfoDto editCourse(EditCourseDto editCourseDto, long companyId);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    void remove(long id);
}
