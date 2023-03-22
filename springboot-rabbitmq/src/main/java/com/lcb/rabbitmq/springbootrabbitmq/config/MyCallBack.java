package com.lcb.rabbitmq.springbootrabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *
   * @Author lcb
   * @Description  回调接口
   * @Date 2022/2/9
   * @Param
   * @return
   **/

@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback{


    //注入
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        String id=correlationData!=null?correlationData.getId():"";

        if(ack){
            log.info("交换机已经收到ID为：{}",id);
        }else {
            log.info("交换机还未收到ID为：{}的消息，由于什么原因：{}",id,cause);
        }
    }


    //消息路由失败，返回给消费者 不可达目的地
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
       log.error("消息{}，被交换机退回，退回原因：{}，路由KEY:{}",
               new String(message.getBody()),exchange,replyText,routingKey);

    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {

    }


}

