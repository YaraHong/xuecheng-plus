package com.atyaoh.content.model.dto;


import com.atyaoh.content.model.po.Teachplan;
import com.atyaoh.content.model.po.TeachplanMedia;
import lombok.Data;

import java.util.List;


/**
 * 课程计划树型结构dto
 */
@Data
public class TeachplanDto extends Teachplan {

    // 课程计划关联的媒资信息
    TeachplanMedia teachplanMedia;

    // 子结点
    List<TeachplanDto> teachPlanTreeNodes;

}