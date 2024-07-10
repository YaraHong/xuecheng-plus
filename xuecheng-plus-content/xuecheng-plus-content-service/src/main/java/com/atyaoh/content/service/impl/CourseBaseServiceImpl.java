package com.atyaoh.content.service.impl;

import com.atyaoh.base.model.PageParams;
import com.atyaoh.base.model.PageResult;
import com.atyaoh.content.mapper.CourseBaseMapper;
import com.atyaoh.content.model.dto.QueryCourseParamsDTO;
import com.atyaoh.content.model.po.CourseBase;
import com.atyaoh.content.service.CourseBaseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class CourseBaseServiceImpl extends ServiceImpl<CourseBaseMapper, CourseBase> implements CourseBaseService {

    @Resource
    private CourseBaseMapper courseBaseMapper;

    /**
     * 分页查询
     *
     * @param pageParams
     * @param queryDto
     * @return PageResult<CourseBase>
     */
    @Override
    public PageResult<CourseBase> list(PageParams pageParams, QueryCourseParamsDTO queryDto) {

        Page<CourseBase> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());

        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        if (queryDto != null) {
            queryWrapper.like(StringUtils.isNotEmpty(queryDto.getCourseName()), CourseBase::getName,
                    queryDto.getCourseName());
            queryWrapper.eq(StringUtils.isNotEmpty(queryDto.getAuditStatus()), CourseBase::getAuditStatus,
                    queryDto.getAuditStatus());
            queryWrapper.eq(StringUtils.isNotEmpty(queryDto.getPublishStatus()), CourseBase::getStatus,
                    queryDto.getPublishStatus());
        }

        Page<CourseBase> courseBasePage = courseBaseMapper.selectPage(page, queryWrapper);

        List<CourseBase> items = courseBasePage.getRecords();
        long counts = courseBasePage.getTotal();

        return new PageResult<CourseBase>(items, counts, pageParams.getPage(), pageParams.getPageSize());
    }
}
