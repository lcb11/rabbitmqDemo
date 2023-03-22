package com.lcb.rabbitmq.three;

import com.lcb.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 *
   * @Author lcb
   * @Description  消息在手动应答时不丢失，丢失了放回队列中重新消费
   * @Date 2022/2/6
   * @Param
   * @return
   **/

public class Task02 {

    //队列名称
    public static final String task_queue_name="ack_queue";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();


        //声明队列
       channel.queueDeclare(task_queue_name,false,false,false,null);
       //从控制台输入信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("",task_queue_name,null,message.getBytes("UTF-8"));
            System.out.println("生产者发出消息："+message);
        }
    }
}
