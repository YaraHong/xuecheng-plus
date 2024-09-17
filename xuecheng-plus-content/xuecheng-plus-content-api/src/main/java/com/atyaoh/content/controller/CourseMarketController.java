package com.atyaoh.content.controller;

import com.atyaoh.content.service.CourseMarketService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课程营销信息 前端控制器
 *
 * @author YaraHong
 */
@Api(value = "课程营销信息相关接口", tags = "课程营销信息相关接口")
@Slf4j
@RestController
@RequestMapping("courseMarket")
public class CourseMarketController {

    @Autowired
    private CourseMarketService courseMarketService;
}
