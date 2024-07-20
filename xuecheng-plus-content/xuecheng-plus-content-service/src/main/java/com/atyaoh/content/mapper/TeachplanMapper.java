package com.atyaoh.content.mapper;

import com.atyaoh.content.model.dto.TeachplanDto;
import com.atyaoh.content.model.po.Teachplan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface TeachplanMapper extends BaseMapper<Teachplan> {

    /**
     * 树结构查询
     *
     * @param courseId
     * @return TeachplanDto
     */
    List<TeachplanDto> selectTreeNodes(long courseId);
}
