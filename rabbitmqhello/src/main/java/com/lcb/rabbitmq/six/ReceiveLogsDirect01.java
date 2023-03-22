package com.lcb.rabbitmq.six;

import com.lcb.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 *
 */
public class ReceiveLogsDirect01 {


    public static final String EXCHANGE_NAME = "direct_logs";


    public static void main(String[] args) throws Exception{

        Channel channel = RabbitMqUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //声明一个队列
        channel.queueDeclare("console",false,false,false,null);

        channel.queueBind("console",EXCHANGE_NAME,"info");
        channel.queueBind("console",EXCHANGE_NAME,"warning");


        DeliverCallback deliverCallback=(consumerTag, message)->{
            System.out.println("ReceiveLogsDirect01控制台打印接受到的消息："+new String(message.getBody(),"UTF-8"));
        };
        //接受消息
        channel.basicConsume("console",true,deliverCallback, consumerTag->{});


    }
}
