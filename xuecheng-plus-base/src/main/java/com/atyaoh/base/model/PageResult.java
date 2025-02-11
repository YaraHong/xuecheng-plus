package com.atyaoh.base.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果响应类
 *
 * @author YaraHong
 */
@Data
public class PageResult<T> implements Serializable {

    /**
     * 数据列表
     */
    private List<T> items;

    /**
     * 总记录数
     */
    private long count;

    /**
     * 当前页码
     */
    private long page;

    /**
     * 每页记录数
     */
    private long pageSize;

    public PageResult(List<T> items, long count, long page, long pageSize) {
        this.items = items;
        this.count = count;
        this.page = page;
        this.pageSize = pageSize;
    }
}
