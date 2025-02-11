package com.atyaoh.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description 添加课程dto
 */
@Data
@ApiModel(value = "EditCourseDto", description = "修改课程基本信息")
public class EditCourseDto extends AddCourseDto {

    @NotNull(message = "课程id不可为空")
    @ApiModelProperty(value = "课程id", required = true)
    private Long id;
}