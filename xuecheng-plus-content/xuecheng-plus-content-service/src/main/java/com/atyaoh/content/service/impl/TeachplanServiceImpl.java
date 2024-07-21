package com.atyaoh.content.service.impl;

import com.atyaoh.base.exception.CommonError;
import com.atyaoh.base.exception.CustomException;
import com.atyaoh.content.mapper.TeachplanMapper;
import com.atyaoh.content.mapper.TeachplanMediaMapper;
import com.atyaoh.content.model.dto.SaveTeachplanDto;
import com.atyaoh.content.model.dto.TeachplanDto;
import com.atyaoh.content.model.po.Teachplan;
import com.atyaoh.content.model.po.TeachplanMedia;
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
    private TeachplanMediaMapper teachplanMediaMapper;

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
     * 删除
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public void delete(long id) {
        Teachplan teachplan = teachplanMapper.selectById(id);
        if (teachplan == null) {
            CustomException.cast(CommonError.QUERY_NULL);
        }
        Integer grade = teachplan.getGrade();
        if (grade != null && grade == 1) {
            Integer count = getChildreNodeCount(id);
            if (count > 0) {
                CustomException.cast("课程计划信息还有子级信息，无法操作");
            }
            teachplanMapper.deleteById(id);
        } else {
            // 删除小节
            teachplanMapper.deleteById(id);

            // 删除相关媒体资源
            removeMediaInfo(id);

            // 如果是仅剩的最后一个小节，则将章节一同删除
            Integer count = getChildreNodeCount(teachplan.getParentid());
            if (count == 0) {
                teachplanMapper.deleteById(teachplan.getParentid());
            }

        }
    }

    /**
     * 上移
     *
     * @param id
     * @return
     */
    @Override
    public void moveup(long id) {
        Teachplan teachplan = teachplanMapper.selectById(id);

        if (teachplan == null) {
            CustomException.cast(CommonError.QUERY_NULL);
        }
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Teachplan::getParentid, teachplan.getParentid())
                .orderBy(teachplan.getOrderby() != null, true, Teachplan::getOrderby);
        List<Teachplan> teachplans = teachplanMapper.selectList(queryWrapper);

        // 如果仅有一个子节点或者该节点已经排在第一位，直接返回
        if (teachplans.size() == 1 || teachplans.get(0).getId() == id) {
            return;
        } else {
            for (int i = 0; i < teachplans.size(); i++) {
                // 交换排序字段
                if (teachplans.get(i).getId().equals(id)) {
                    Teachplan currentNode = teachplans.get(i);
                    Teachplan frontNode = teachplans.get(i - 1);
                    Integer tmpOrderby = currentNode.getOrderby();
                    currentNode.setOrderby(frontNode.getOrderby());
                    frontNode.setOrderby(tmpOrderby);

                    teachplanMapper.updateById(currentNode);
                    teachplanMapper.updateById(frontNode);
                }
            }
        }
    }

    /**
     * 下移
     *
     * @param id
     * @return
     */
    @Override
    public void movedown(long id) {
        Teachplan teachplan = teachplanMapper.selectById(id);

        if (teachplan == null) {
            CustomException.cast(CommonError.QUERY_NULL);
        }
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Teachplan::getParentid, teachplan.getParentid())
                .orderBy(teachplan.getOrderby() != null, false, Teachplan::getOrderby);
        List<Teachplan> teachplans = teachplanMapper.selectList(queryWrapper);

        // 如果仅有一个子节点或者该节点已经排在第一位，直接返回
        if (teachplans.size() == 1 || teachplans.get(0).getId() == id) {
            return;
        } else {
            for (int i = 0; i < teachplans.size(); i++) {
                // 交换排序字段
                if (teachplans.get(i).getId().equals(id)) {
                    Teachplan currentNode = teachplans.get(i);
                    Teachplan frontNode = teachplans.get(i - 1);
                    Integer tmpOrderby = currentNode.getOrderby();
                    currentNode.setOrderby(frontNode.getOrderby());
                    frontNode.setOrderby(tmpOrderby);

                    teachplanMapper.updateById(currentNode);
                    teachplanMapper.updateById(frontNode);
                }
            }
        }
    }


    /**
     * 删除媒体资源信息
     *
     * @param teachplanId
     * @return
     */
    private void removeMediaInfo(Long teachplanId) {
        LambdaQueryWrapper<TeachplanMedia> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeachplanMedia::getTeachplanId, teachplanId);
        teachplanMediaMapper.delete(queryWrapper);
    }

    /**
     * 获取子节点数量
     *
     * @param parentId
     * @return Integer
     */
    private Integer getChildreNodeCount(Long parentId) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(parentId != null, Teachplan::getParentid, parentId);
        return teachplanMapper.selectCount(queryWrapper);
    }

    /**
     * 获取orderby
     *
     * @param courseId
     * @param parentId
     * @return int
     */
    private Integer getLastOrderby(Long courseId, Long parentId) {
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
