package com.atyaoh.content.service.impl;

import com.atyaoh.content.mapper.CourseCategoryMapper;
import com.atyaoh.content.model.po.CourseCategory;
import com.atyaoh.content.service.CourseCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements CourseCategoryService {

    @Resource
    private CourseCategoryMapper courseCategoryMapper;

    /**
     * 查询课程分类树
     *
     * @param id
     * @return List<CourseCategory>
     */
    @Override
    public List<CourseCategory> queryTreeNodes(String id) {
        List<CourseCategory> categories = courseCategoryMapper.selectTreeNodes(id);

        // 过滤根结点，并将 list 转为 map 临时使用
        Map<String, CourseCategory> categoryMap = categories.stream()
                .filter(category -> !category.getId().equals(id))
                .collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));

        // 返回数据
        List<CourseCategory> treeNodes = new ArrayList<>();

        categories.stream()
                .filter(category -> !category.getId().equals(id))
                .forEach(category -> {
                            if (category.getParentid().equals(id)) {
                                treeNodes.add(category);
                            }
                            CourseCategory parentCategory = categoryMap.get(category.getParentid());
                            if (parentCategory != null) {
                                if (parentCategory.getChildrenTreeNodes() == null) {
                                    parentCategory.setChildrenTreeNodes(new ArrayList<CourseCategory>());
                                }
                                // 设置子节点数据
                                parentCategory.getChildrenTreeNodes().add(category);
                            }
                        }
                );
        return treeNodes;
    }
}

