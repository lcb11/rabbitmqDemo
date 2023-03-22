package com.lcb.rabbitmq.springbootrabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Configuration
public class DelayedQueueConfig {


    //队列
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    //交换机
    public static final String  DELAYED_EXCHANGE_NAME = "delayed.exchange";
    //routingKey
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";


    @Bean
    public Queue delayedQuueue(){
        return new Queue(DELAYED_QUEUE_NAME);
    }

    //声明交换机  基于插件
    @Bean
    public CustomExchange delayedExchange(){


        Map<String,Object> arguments=new HashMap<>();
        arguments.put("x-delayed-type","direct");
        /*
          * @Author lcb
          * @Description
          * @Date 2022/2/9
          * @Param [] 交换机名称 交换机类型  是否需要持久化 是否需要自动删除 其他参数
          * @return org.springframework.amqp.core.CustomExchange
          **/
        return new CustomExchange(DELAYED_EXCHANGE_NAME,"x-delayed-message",
                true,false,arguments);
    }


    //绑定
    @Bean
    public Binding delayedQueueBindingDelayedExchange
    (@Qualifier("delayedQuueue") Queue delayedQueue,
     @Qualifier("delayedExchange") CustomExchange delayedExchange){

        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();

    }
}
