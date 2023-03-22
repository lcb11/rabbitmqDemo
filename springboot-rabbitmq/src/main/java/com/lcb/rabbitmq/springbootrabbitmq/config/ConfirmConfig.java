package com.lcb.rabbitmq.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
   * @Author lcb
   * @Description  发布确认高级
   * @Date 2022/2/9
   * @Param
   * @return
   **/

@Configuration
public class ConfirmConfig {
    //交换机
    public static final String confim_exchange_name="confirm_exchange";
    //队列
    public static final String confirm_queue_name="confirm_queue";
    //RoutingKey
    public static final String confirm_routing_key="key1";

    //声明交换机
    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){
        return new DirectExchange(confim_exchange_name);
    }

    @Bean("confirmQueue")
    public Queue confirmQueue(){
        return QueueBuilder.durable(confirm_queue_name).build();
    }

    //绑定
    @Bean
    public Binding queueBindingExchange(@Qualifier("confirmQueue") Queue confirmQueue,
                                        @Qualifier("confirmExchange") DirectExchange confirmExchange){
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(confirm_routing_key);
    }
}
