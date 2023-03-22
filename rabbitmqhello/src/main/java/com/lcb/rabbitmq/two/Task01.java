package com.lcb.rabbitmq.two;

import com.lcb.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 *
   * @Author lcb
   * @Description 生产者发送大量消息
   * @Date 2022/2/6
   * @Param
   * @return
   **/

public class Task01 {
    //队列名称
    public static final String QUEUE_NAME="hello";

    //发送消息
    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();

        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //从控制台中接受或者发送消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message=scanner.next();
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("发送消息完成："+message);
        }
    }
}
