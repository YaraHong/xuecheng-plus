package com.atyaoh.media.mapper;

import com.atyaoh.media.model.po.MqMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author itcast
 */
@Mapper
public interface MqMessageMapper extends BaseMapper<MqMessage> {

}
