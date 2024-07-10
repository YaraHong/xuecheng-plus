package com.atyaoh.content.service.impl;

import com.atyaoh.content.mapper.MqMessageMapper;
import com.atyaoh.content.model.po.MqMessage;
import com.atyaoh.content.service.MqMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqMessageServiceImpl extends ServiceImpl<MqMessageMapper, MqMessage> implements MqMessageService {

}
