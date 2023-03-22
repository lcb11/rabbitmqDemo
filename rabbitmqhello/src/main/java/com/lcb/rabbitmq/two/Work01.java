package com.lcb.rabbitmq.two;

import com.lcb.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 *
   * @Author lcb
   * @Description  工作线程
   * @Date 2022/2/6
   * @Param
   * @return
   **/

public class Work01 {
    //队列名称
    public static final String QUEUE_NAME="hello";

    //接受消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        //消息接收
        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println("接受到的消息："+new String(message.getBody()));
        };

        //消息取消时
      CancelCallback cancelCallback=(consumerTag)->{
            System.out.println("消息被取消了"+consumerTag);
        };

        System.out.println("C2等待接受消息.....");
        //消息接收
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
