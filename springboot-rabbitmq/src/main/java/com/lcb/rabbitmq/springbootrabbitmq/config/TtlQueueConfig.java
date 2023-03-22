package com.lcb.rabbitmq.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
  * @Author lcb
  * @Description  TTL队列  配置类代码
  * @Date 2022/2/9
  * @Param
  * @return
  **/

@Configuration
public class TtlQueueConfig {

/*
    //普通交换机名称
    public static final String x_exchange="X";
    //死信交换机名称
    public static final String y_dead_letter_exchange="Y";
    //普通队列名称
    public static final String queue_A="QA";
    public static final String queue_B="QB";
    //死信队列名称
    public static final String dead_letter_queue="QD";

    //声明xExchange
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(x_exchange);
    }

    //声明xExchange
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(y_dead_letter_exchange);
    }

    //声明队列
    @Bean("queueA")
    public Queue queueA(){
        Map<String,Object> arguments=new HashMap<>(3);
        //设置死信交换机
        arguments.put("x_dead_letter_exchange",y_dead_letter_exchange);
        //设置死信RoutingKey
        arguments.put("x_dead_letter_routing-key","YD");
        //设置TTL
        arguments.put("x-message-ttl",10000);
        return QueueBuilder.durable(queue_A).withArguments(arguments).build();
    }

    //声明队列
    @Bean("queueB")
    public Queue queueB(){
        Map<String,Object> arguments=new HashMap<>(3);
        //设置死信交换机
        arguments.put("x_dead_letter_exchange",y_dead_letter_exchange);
        //设置死信RoutingKey
        arguments.put("x_dead_letter_routing-key","YD");
        //设置TTL
        arguments.put("x-message-ttl",40000);
        return QueueBuilder.durable(queue_B).withArguments(arguments).build();
    }

    //死信队列
    @Bean("queueD")
    public Queue queueD(){

        return new Queue(dead_letter_queue);
    }

    //绑定
    @Bean
    public Binding queueABindingX(@Qualifier("queueA")Queue queueA,
                                  @Qualifier("xExchange")DirectExchange xExchange){

        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    //绑定
    @Bean
    public Binding queueBBindingX(@Qualifier("queueB")Queue queueB,
                                  @Qualifier("xExchange")DirectExchange xExchange){

        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    //绑定
    @Bean
    public Binding queueABindingY(@Qualifier("queueD")Queue queueD,
                                  @Qualifier("yExchange")DirectExchange yExchange){

        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }*/

    public static final String X_EXCHANGE = "X";
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    public static final String DEAD_LETTER_QUEUE = "QD";


    @Bean("queueC")
    public Queue queueC(){
        Map<String, Object> args = new HashMap<>(3);
        //设置死信交换机
        args.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        //设置routingKey
        args.put("x-dead-letter-routing-key","YD");

        return  QueueBuilder.durable(QUEUE_C).withArguments(args).build();
    }

    @Bean
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueC,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }
    // 声明 xExchange
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }
    // 声明 xExchange
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }
    //声明队列 A ttl 为 10s 并绑定到对应的死信交换机
    @Bean("queueA")
    public Queue queueA(){
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", "YD");
        //声明队列的 TTL
        args.put("x-message-ttl", 10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(args).build();
    }
    // 声明队列 A 绑定 X 交换机
    @Bean
    public Binding queueaBindingX(@Qualifier("queueA") Queue queueA,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }
    //声明队列 B ttl 为 40s 并绑定到对应的死信交换机
    @Bean("queueB")
    public Queue queueB(){
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", "YD");
        //声明队列的 TTL
        args.put("x-message-ttl", 40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(args).build();
    }
    //声明队列 B 绑定 X 交换机
    @Bean
    public Binding queuebBindingX(@Qualifier("queueB") Queue queue1B,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queue1B).to(xExchange).with("XB");
    }
    //声明死信队列 QD
    @Bean("queueD")
    public Queue queueD(){
        return new Queue(DEAD_LETTER_QUEUE);
    }
    //声明死信队列 QD 绑定关系
    @Bean
    public Binding deadLetterBindingQAD(@Qualifier("queueD") Queue queueD,
                                        @Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }


}
