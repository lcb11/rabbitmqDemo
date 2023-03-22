package com.lcb.rabbitmq.one;

import com.rabbitmq.client.*;

/**
 *
   * @Author lcb
   * @Description  消费者：接受消息
   * @Date 2022/2/6
   * @Param
   * @return
   **/
public class Consumer {
     //队列名称
    public static final String QUEUE_NAME="hello";
    //接受消息
    public static void main(String[] args) throws Exception{
        //创建连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("192.168.248.132");
        factory.setUsername("admin");
        factory.setPassword("123");
        Connection connection=factory.newConnection();

        Channel channel=connection.createChannel();

        //声明  接受消息
        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println(new String(message.getBody()));
        };

        //声明 取消消息时回调
        CancelCallback cancelCallback=consumerTag->{
            System.out.println("消息消费被中断");
        };

        //消费者接受消息
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
