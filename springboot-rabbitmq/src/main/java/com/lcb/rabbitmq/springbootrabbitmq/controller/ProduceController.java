package com.lcb.rabbitmq.springbootrabbitmq.controller;

import com.lcb.rabbitmq.springbootrabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
   * @Author lcb
   * @Description  开始发消息
   * @Date 2022/2/9
   * @Param
   * @return
   **/
@Slf4j
@RestController
@RequestMapping("/confirm")
public class ProduceController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //发消息
    @GetMapping("/sendMessage/{message}")
    public void sendMessage(@PathVariable String message){

        CorrelationData correlationData=new CorrelationData("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.confim_exchange_name+"111",
                ConfirmConfig.confirm_routing_key,message,correlationData);
        log.info("发送消息的类容：{}",message);

        CorrelationData correlationData2=new CorrelationData("2");
        rabbitTemplate.convertAndSend(ConfirmConfig.confim_exchange_name,
                ConfirmConfig.confirm_routing_key+"111",message,correlationData2);
        log.info("发送消息的类容：{}",message);
    }
}
