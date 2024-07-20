package com.atyaoh.content.service.impl;

import com.atyaoh.content.mapper.CourseMarketMapper;
import com.atyaoh.content.mapper.TeachplanMapper;
import com.atyaoh.content.model.dto.SaveTeachplanDto;
import com.atyaoh.content.model.dto.TeachplanDto;
import com.atyaoh.content.model.po.Teachplan;
import com.atyaoh.content.service.TeachplanService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TeachplanServiceImpl extends ServiceImpl<TeachplanMapper, Teachplan> implements TeachplanService {

    @Resource
    private TeachplanMapper teachplanMapper;
    @Resource
    private CourseMarketMapper courseMarketMapper;

    /**
     * 树结构查询
     *
     * @param courseId
     * @return TeachplanDto
     */
    @Override
    public List<TeachplanDto> queryTreeNodes(long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    /**
     * 新增或修改
     *
     * @param saveTeachplanDto
     * @return
     */
    @Transactional
    @Override
    public void saveOrUpdate(SaveTeachplanDto saveTeachplanDto) {
        Long id = saveTeachplanDto.getId();

        Teachplan teachplan = new Teachplan();
        BeanUtils.copyProperties(saveTeachplanDto, teachplan);
        // id 为null，添加
        if (id == null) {
            Integer lastOrderby = getLastOrderby(saveTeachplanDto.getCourseId(), saveTeachplanDto.getParentid());
            teachplan.setOrderby(lastOrderby);
            teachplanMapper.insert(teachplan);

            // 如果是第一个章，需要自动新增一个小节
            if (saveTeachplanDto.getGrade() == 1) {
                Teachplan firstNode = new Teachplan();
                firstNode.setParentid(teachplan.getId());
                firstNode.setCourseId(teachplan.getCourseId());
                firstNode.setGrade(2);
                firstNode.setPname("新小节名称 [点击修改]");
                teachplanMapper.insert(firstNode);
            }
        }
        // id 不为null，修改
        else {
            teachplanMapper.updateById(teachplan);
        }
    }

    /**
     * 获取orderby
     *
     * @param courseId
     * @param parentId
     * @return int
     */
    private Integer getLastOrderby(long courseId, long parentId) {
        // 需要添加到最后位置
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Teachplan::getCourseId, courseId)
                .eq(Teachplan::getParentid, parentId);
        List<Teachplan> teachplans = teachplanMapper.selectList(queryWrapper);
        Optional<Integer> max = teachplans.stream()
                .map(Teachplan::getOrderby)
                .max(Comparator.comparingInt(plan -> plan));
        if (max.isPresent()) {
            return max.get() + 1;
        }
        return 1;
    }
}
