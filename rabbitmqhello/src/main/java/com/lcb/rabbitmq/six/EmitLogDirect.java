package com.lcb.rabbitmq.six;

import com.lcb.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 *
   * @Author lcb
   * @Description 直接交换机 消息捆绑  定向发送
   * @Date 2022/2/7
   * @Param
   * @return
   **/
public class EmitLogDirect {



    //交换机名称
    public static final String EXCHANGE_NAME="direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"error",null,message.getBytes("UTF-8"));
            System.out.println("生产者发出消息："+message);
        }
    }
}
