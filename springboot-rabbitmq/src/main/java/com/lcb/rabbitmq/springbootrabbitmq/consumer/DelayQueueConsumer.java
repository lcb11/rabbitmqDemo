package com.lcb.rabbitmq.springbootrabbitmq.consumer;

import com.lcb.rabbitmq.springbootrabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
  * @Author lcb
  * @Description  消费者 基于插件
  * @Date 2022/2/9
  * @Param
  * @return
  **/

@Slf4j
@Component
public class DelayQueueConsumer {

    //监听消息
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayQueue(Message message){
        String msg=new String(message.getBody());

        log.info("当前时间：{}，收到延迟队列消息时间：{}",new Date().toString(),msg);
    }
}
