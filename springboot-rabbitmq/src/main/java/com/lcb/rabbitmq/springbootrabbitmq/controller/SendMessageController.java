package com.lcb.rabbitmq.springbootrabbitmq.controller;

import com.lcb.rabbitmq.springbootrabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
  * @Author lcb
  * @Description  发送延迟消息
  * @Date 2022/2/9
  * @Param
  * @return
  **/

@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMessageController {

   /* @Autowired
    private RabbitTemplate rabbitTemplate;

    //开始发送消息
    @GetMapping("/sendMsg/{message}")
    public void  sendMsg(@PathVariable String message){
        log.info("当前时间：{},发送一条信息给两个TTL队列：{}",new Date().toString(),message);

        rabbitTemplate.convertAndSend("X","XA","消息来自ttl为10s的队列:"+message);
        rabbitTemplate.convertAndSend("X","XB","消息来自ttl为40s的队列:"+message);
    }*/
   @Autowired
   private RabbitTemplate rabbitTemplate;
    @GetMapping("sendMsg/{message}")
    public void sendMsg(@PathVariable String message){
        log.info("当前时间：{},发送一条信息给两个 TTL 队列:{}", new Date(), message);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自 ttl 为 10S 的队列: "+message);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自 ttl 为 40S 的队列: "+message);
    }

    //开始发消息 与过期时间
    @GetMapping("/sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message,@PathVariable String ttlTime){
        log.info("当前时间：{},发送一条时长：{}的信息给 TTL 队列QC:{}", new Date(),
                ttlTime,message);
        rabbitTemplate.convertAndSend("X","XC",message,msg->{
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }

    //开始发消息 基于插件
    @GetMapping("/sendDelayMsg/{message}/{delayTime}")
    public void sendMsg(@PathVariable String message,@PathVariable Integer delayTime){
        log.info("当前时间：{},发送一条时长：{}的信息给延迟队列delayTime:{}", new Date(),
                delayTime,message);

        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME,
                DelayedQueueConfig.DELAYED_ROUTING_KEY,
                message, msg->{
            msg.getMessageProperties().setDelay(delayTime);
            return msg;
        });

        //开始发消息 发布确认

    }

}
