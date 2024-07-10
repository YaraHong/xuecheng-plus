package com.atyaoh.content.service;

import com.atyaoh.base.model.PageParams;
import com.atyaoh.base.model.PageResult;
import com.atyaoh.content.model.dto.QueryCourseParamsDto;
import com.atyaoh.content.model.po.CourseBase;
import com.atyaoh.content.model.vo.CourseInfoVo;
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
    PageResult<CourseBase> list(PageParams pageParams, QueryCourseParamsDto queryDto);

    /**
     * 根据id查询
     *
     * @param id
     * @return CourseInfoVo
     */
    CourseInfoVo getById(long id);
}
