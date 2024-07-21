package com.atyaoh.content.service;

import com.atyaoh.content.model.dto.SaveTeachplanDto;
import com.atyaoh.content.model.dto.TeachplanDto;
import com.atyaoh.content.model.po.Teachplan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TeachplanService extends IService<Teachplan> {

    /**
     * 树结构查询
     *
     * @param courseId
     * @return TeachplanDto
     */
    List<TeachplanDto> queryTreeNodes(Long courseId);

    /**
     * 新增或修改
     *
     * @param saveTeachplanDto
     * @return
     */
    void saveOrUpdate(SaveTeachplanDto saveTeachplanDto);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    void delete(Long id);
}
