package com.atyaoh.content.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 课程-教师关系表
 *
 * @author YaraHong
 */
@Data
@TableName("course_teacher")
public class CourseTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程标识
     */
    @NotNull(message = "课程标识不可为空")
    private Long courseId;

    /**
     * 教师标识
     */
    @NotEmpty(message = "教师标识不可为空")
    private String teacherName;

    /**
     * 教师职位
     */
    @NotEmpty(message = "教师职位不可为空")
    private String position;

    /**
     * 教师简介
     */
    private String introduction;

    /**
     * 照片
     */
    private String photograph;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;


}
