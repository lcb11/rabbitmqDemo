package com.lcb.rabbitmq.seven;

import com.lcb.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 *
   * @Author lcb
   * @Description 声明主题交换机及其队列
   * @Date 2022/2/7
   * @Param
   * @return
   **/

public class ReceiveLogsTopic01 {

    public static final String  EXCHANGE_NAME = "topic_logs";


    //接受消息
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        //声明队列
        String queueName = "Q1";
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName,EXCHANGE_NAME,"*.orange.*");
        System.out.println("等待接受消息.....");


        DeliverCallback deliverCallback=(consumerTag, message)->{
            System.out.println("ReceiveLogsDirect02控制台打印接受到的消息："+new String(message.getBody(),"UTF-8"));
            System.out.println("接受队列："+queueName+" _绑定键："+message.getEnvelope().getRoutingKey());
        };
        channel.basicConsume(queueName,true,deliverCallback, consumerTag->{});

    }
}
