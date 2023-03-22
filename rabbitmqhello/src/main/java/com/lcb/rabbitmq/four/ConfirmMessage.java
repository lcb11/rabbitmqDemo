package com.lcb.rabbitmq.four;

import com.lcb.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.util.UUID;

/**
 *
   * @Author lcb
   * @Description 验证发布确认模式  单个确认  批量确认  异步批量确认
   * @Date 2022/2/7
   * @Param
   * @return
   **/
public class ConfirmMessage {
    //批量发消息的个数
    public static final int message_count=1000;

    public static void main(String[] args) throws Exception {
        //单个确认
       //ConfirmMessage.publishMessageIndividually();  //发布1000个单独确认消息，耗时566ms
        //批量确认
        //ConfirmMessage.publishMessageBatch();  //发布1000个批量确认消息，耗时81ms
        //异步确认发布
        ConfirmMessage.publishMessageAsync();  //发布1000个异步批量确认消息，耗时52ms


    }


    //单个确认
    public static void publishMessageIndividually() throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        //队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,false,false,false,null);

        //开启发布确认
        channel.confirmSelect();

        //开始时间
        long begin = System.currentTimeMillis();
        //批量发消息
        for (int i=0;i<message_count;i++){
            String message=i+"";
            channel.basicPublish("",queueName,null,message.getBytes());
            //单个消息确认
            boolean b = channel.waitForConfirms();
            if(b){
                System.out.println("消息发送成功");

            }
        }
        //结束时间
        long end = System.currentTimeMillis();
        System.out.println("发布"+message_count+"个单独确认消息，耗时"+(end-begin)+"ms");

    }


    //批量发布
    public static void publishMessageBatch() throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        //队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,false,false,false,null);

        //开启发布确认
        channel.confirmSelect();

        //开始时间
        long begin = System.currentTimeMillis();

        //批量确认消息大小
        int batchSize=100;

        //批量发布 批量确认
        for (int i = 0; i < message_count; i++) {
            String message=i+"";
            channel.basicPublish("",queueName,null,message.getBytes());

            //判断达到100条消息时，批量确认一次
            if(i%batchSize==0){
                //发布确认
                channel.waitForConfirms();
            }
        }



        //结束时间
        long end = System.currentTimeMillis();
        System.out.println("发布"+message_count+"个批量确认消息，耗时"+(end-begin)+"ms");
    }


    //异步发布消息确认
    public static void publishMessageAsync() throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        //队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,false,false,false,null);

        //开启发布确认
        channel.confirmSelect();

        //开始时间
        long begin = System.currentTimeMillis();

        //消息确认成功回调函数
        ConfirmCallback ackCallback=(deliverTag,multiple)->{

            System.out.println("确认消息："+deliverTag);
        };
        //消息确认失败回调函数
        ConfirmCallback nackCallback=(deliverTag,multiple)->{

            System.out.println("未确认消息："+deliverTag);
        };
        //准备消息监听器 监听那些消息成功了 那些消息失败了
        channel.addConfirmListener(ackCallback,nackCallback);

        //批量发布消息
        for (int i = 0; i < message_count; i++) {
            String message=i+"";
            channel.basicPublish("",queueName,null,message.getBytes());
        }

        //结束时间
        long end = System.currentTimeMillis();
        System.out.println("发布"+message_count+"个异步批量确认消息，耗时"+(end-begin)+"ms");
    }
}
