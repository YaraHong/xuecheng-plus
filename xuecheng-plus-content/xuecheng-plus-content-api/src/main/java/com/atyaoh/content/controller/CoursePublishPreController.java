package com.atyaoh.content.controller;

import com.atyaoh.content.service.CoursePublishPreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课程发布 前端控制器
 *
 * @author YaraHong
 */
@Slf4j
@RestController
@RequestMapping("coursePublishPre")
public class CoursePublishPreController {

    @Autowired
    private CoursePublishPreService coursePublishPreService;
}
