package com.lcb.rabbitmq.five;

import com.lcb.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 *
   * @Author lcb
   * @Description  接受消息1
   * @Date 2022/2/7
   * @Param
   * @return
   **/

public class ReceiveLogs01 {

    //交换机名称
    public static final String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws Exception{

        Channel channel = RabbitMqUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //声明一个队列零时的   队列名称随机的  断开连接时自动删除
        String queueName = channel.queueDeclare().getQueue();

        //对列交换机绑定
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        System.out.println("等待接受消息，把接受的消息打印在屏幕上.....");


        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println("ReceiveLogs01控制台打印接受到的消息："+new String(message.getBody(),"UTF-8"));
        };
        //接受消息
        channel.basicConsume(queueName,true,deliverCallback, consumerTag->{});

    }
}

