package com.atyaoh.content.service.impl;

import com.atyaoh.content.mapper.TeachplanMapper;
import com.atyaoh.content.model.dto.TeachplanDto;
import com.atyaoh.content.model.po.Teachplan;
import com.atyaoh.content.service.TeachplanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class TeachplanServiceImpl extends ServiceImpl<TeachplanMapper, Teachplan> implements TeachplanService {

    @Resource
    private TeachplanMapper teachplanMapper;

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
}
