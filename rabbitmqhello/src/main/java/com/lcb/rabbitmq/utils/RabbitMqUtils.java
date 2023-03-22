package com.lcb.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *
   * @Author lcb
   * @Description 获取信道工具类
   * @Date 2022/2/6
   * @Param
   * @return
   **/
public class RabbitMqUtils {

        //得到一个连接的 channel
        public static Channel getChannel() throws Exception{
            //创建一个连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("192.168.248.132");
            factory.setUsername("admin");
            factory.setPassword("123");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            return channel;
        }
    }

