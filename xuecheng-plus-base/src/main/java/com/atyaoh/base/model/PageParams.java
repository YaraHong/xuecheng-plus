package com.atyaoh.base.model;

import lombok.Data;

/**
 * 分页查询参数
 *
 * @author YaraHong
 */
@Data
public class PageParams {
    /**
     * 当前页码
     */
    private Long page = 1L;
    /**
     * 每页记录默认数
     */
    private Long pageSize = 10L;

    public PageParams() {

    }

    public PageParams(Long page, Long pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }
}
