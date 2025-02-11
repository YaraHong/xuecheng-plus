package com.atyaoh.content.service.impl;

import com.atyaoh.base.exception.CommonError;
import com.atyaoh.base.exception.CustomException;
import com.atyaoh.base.model.PageParams;
import com.atyaoh.base.model.PageResult;
import com.atyaoh.content.mapper.CourseBaseMapper;
import com.atyaoh.content.mapper.CourseMarketMapper;
import com.atyaoh.content.mapper.CourseTeacherMapper;
import com.atyaoh.content.mapper.TeachplanMapper;
import com.atyaoh.content.model.dto.AddCourseDto;
import com.atyaoh.content.model.dto.CourseBaseInfoDto;
import com.atyaoh.content.model.dto.EditCourseDto;
import com.atyaoh.content.model.dto.QueryCourseParamsDto;
import com.atyaoh.content.model.po.CourseBase;
import com.atyaoh.content.model.po.CourseMarket;
import com.atyaoh.content.model.po.CourseTeacher;
import com.atyaoh.content.model.po.Teachplan;
import com.atyaoh.content.service.CourseBaseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class CourseBaseServiceImpl extends ServiceImpl<CourseBaseMapper, CourseBase> implements CourseBaseService {

    @Resource
    private CourseBaseMapper courseBaseMapper;
    @Resource
    private CourseMarketMapper courseMarketMapper;
    @Resource
    private TeachplanMapper teachplanMapper;
    @Resource
    private CourseTeacherMapper courseTeacherMapper;

    /**
     * 分页查询
     *
     * @param pageParams
     * @param queryDto
     * @return PageResult<CourseBase>
     */
    @Override
    public PageResult<CourseBase> queryCourseBaseInfo(PageParams pageParams, QueryCourseParamsDto queryDto) {
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

    /**
     * 根据id查询
     *
     * @param id
     * @return CourseInfoVo
     */
    @Override
    public CourseBaseInfoDto getCourseBaseInfo(long id) {
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();

        CourseBase courseBase = courseBaseMapper.selectById(id);
        if (courseBase == null) {
            return null;
        }
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        CourseMarket courseMarket = courseMarketMapper.selectById(id);
        if (courseMarket != null) {
            BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        }
        return courseBaseInfoDto;
    }

    /**
     * 添加
     *
     * @param addCourseDto
     * @return CourseBaseInfoDto
     */
    @Transactional
    @Override
    public CourseBaseInfoDto addCourse(AddCourseDto addCourseDto) {
        // 添加课程基本信息
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(addCourseDto, courseBase);
        courseBase.setCompanyId(1232141425L); // 1、TODO 机构信息
        courseBase.setCompanyName("手动填充");
        courseBase.setAuditStatus("202002"); // 2、TODO 审核状态
        courseBase.setStatus("202002");
        int insert = courseBaseMapper.insert(courseBase);
        if (insert <= 0) {
            CustomException.cast("添加失败");
        }

        // 添加课程营销信息
        CourseMarket newCourseMarket = new CourseMarket();
        newCourseMarket.setId(courseBase.getId());
        BeanUtils.copyProperties(addCourseDto, newCourseMarket);
        saveCourseMarket(newCourseMarket);

        // 获取课程信息
        CourseBaseInfoDto courseBaseInfoDto = getCourseBaseInfo(courseBase.getId());
        return courseBaseInfoDto;
    }

    /**
     * 修改
     *
     * @param editCourseDto
     * @return CourseBaseInfoDto
     */
    @Override
    public CourseBaseInfoDto editCourse(EditCourseDto editCourseDto, long companyId) {
        // 修改课程基本信息
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(editCourseDto.getId());
        if (courseBaseInfo == null) {
            CustomException.cast(CommonError.OBJECT_NULL);
        }
        if (companyId != courseBaseInfo.getCompanyId()) {
            CustomException.cast("只能修改本机构课程");
        }
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(editCourseDto, courseBase);
        courseBaseMapper.updateById(courseBase);

        // 修改课程营销信息
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(editCourseDto, courseMarket);
        saveCourseMarket(courseMarket);
        // 查询课程信息
        return getCourseBaseInfo(editCourseDto.getId());
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public void remove(long id) {
        // 仅可删除未发布状态课程
        CourseBase courseBase = courseBaseMapper.selectById(id);
        if (courseBase == null) {
            CustomException.cast("无此课程");
        }
        if (!"203001".equals(courseBase.getStatus())) {
            CustomException.cast("仅可删除未发布状态的课程");
        }

        // 删除课程基本信息
        courseBaseMapper.deleteById(id);
        // 删除课程营销信息
        courseMarketMapper.deleteById(id);
        // 删除课程计划信息
        teachplanMapper.delete(new LambdaQueryWrapper<Teachplan>().eq(Teachplan::getCourseId, id));
        // 删除课程老师信息
        courseTeacherMapper.delete(new LambdaQueryWrapper<CourseTeacher>().eq(CourseTeacher::getCourseId, id));
    }

    /**
     * 保存课程营销信息
     *
     * @param newCourseMarket
     * @return int
     */
    private int saveCourseMarket(CourseMarket newCourseMarket) {
        CourseMarket courseMarket = courseMarketMapper.selectById(newCourseMarket.getId());

        // 如果是付费课程，需要传入价格
        if (newCourseMarket.getCharge().equals("201001")) { // TODO 数据字典后面优化
            if (newCourseMarket.getPrice() == null || newCourseMarket.getPrice() <= 0) {
                CustomException.cast("课程的价格不能为空并且必须大于0");
            }
        }
        if (courseMarket == null) {
            return courseMarketMapper.insert(newCourseMarket);
        } else {
            BeanUtils.copyProperties(newCourseMarket, courseMarket);
            return courseMarketMapper.updateById(courseMarket);
        }
    }
}
