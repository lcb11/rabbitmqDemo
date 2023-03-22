package com.lcb.rabbitmq.springbootrabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 *
   * @Author lcb
   * @Description  队列TTL  消费者
   * @Date 2022/2/9
   * @Param
   * @return
   **/

@Slf4j
@Component
public class DeadLetterQueueConsumer {

   /* //接受消息
    @RabbitListener(queues="QD")
    public void receiveD(Message message, Channel channel)throws IOException {
        String msg=new String(message.getBody());
        log.info("当前时间:{},收到死信队列的消息：{}",new Date().toString(),msg);
    }*/
   @RabbitListener(queues = "QD")
   public void receiveD(Message message, Channel channel) throws IOException {
       String msg = new String(message.getBody());
       log.info("当前时间：{},收到死信队列信息{}", new Date().toString(), msg);
   }
}
