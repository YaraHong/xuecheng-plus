package com.atyaoh.content.service.impl;

import com.atyaoh.content.mapper.MqMessageHistoryMapper;
import com.atyaoh.content.model.po.MqMessageHistory;
import com.atyaoh.content.service.MqMessageHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqMessageHistoryServiceImpl extends ServiceImpl<MqMessageHistoryMapper, MqMessageHistory> implements MqMessageHistoryService {

}
