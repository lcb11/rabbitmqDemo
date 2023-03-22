package com.lcb.rabbitmq.three;

import com.lcb.rabbitmq.utils.RabbitMqUtils;
import com.lcb.rabbitmq.utils.SleepUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 *
   * @Author lcb
   * @Description  消息在手动应答时不丢失，丢失了放回队列中重新消费
   * @Date 2022/2/6
   * @Param
   * @return
   **/

public class Work03 {
    //队列名称
    public static final String task_queue_name="ack_queue";

    //接受消息
    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();

        System.out.println("C1等待消息时间处理-时间较短");


        DeliverCallback deliverCallback=(consumerTag,message)->{
            //接受消息之前要求沉睡1秒
            SleepUtils.sleep(1);
            System.out.println("接受到的消息："+new String(message.getBody(),"UTF-8"));

            //手动应答
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };

        //采用手动应答

        boolean autoAck=false;
        channel.basicConsume(task_queue_name,autoAck,deliverCallback,(consumerTag->{
            System.out.println("消费者取消消费接口回调逻辑"+consumerTag);
        }));
    }
}
