package com.atyaoh.content.controller;

import com.atyaoh.content.service.TeachplanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 课程计划 前端控制器
 *
 * @author YaraHong
 */
@Slf4j
@RestController
@RequestMapping("teachplan")
public class TeachplanController {

    @Resource
    private TeachplanService teachplanService;


}
