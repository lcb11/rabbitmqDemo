package com.lcb.rabbitmq.springbootrabbitmq.consumer;

import com.lcb.rabbitmq.springbootrabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 *
 */
@Slf4j
@Component
public class Consumer {
    @RabbitListener(queues = ConfirmConfig.confirm_queue_name)
    public void receiveConfirmConfig(Message message){
        String msg = new String(message.getBody());
        log.info("接受到的多列confirm.queue消息：{}",msg);

    }
}
