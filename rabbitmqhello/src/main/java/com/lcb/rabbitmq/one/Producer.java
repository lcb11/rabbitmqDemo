package com.lcb.rabbitmq.one;

/**
   * @Author lcb
   * @Description   生产者:发消息
   * @Date 2022/2/6
   * @Param
   * @return
   **/

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
public class Producer {

     //队列名称
    public static final String QUEUE_NAME="hello";


    //发消息
    public static void main(String[] args) throws Exception {
        //创建一个连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        //设置工厂ip连接rabbitmq的队列
        factory.setHost("192.168.248.132");
        factory.setUsername("admin");
        factory.setPassword("123");

        //创建连接
        Connection connection=factory.newConnection();
        //获取信道
        Channel channel=connection.createChannel();
        //创建一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //发消息
        String message="hello,world!!!";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("消息发送完毕！！！");
    }
}
