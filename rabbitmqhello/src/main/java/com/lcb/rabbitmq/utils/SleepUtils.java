package com.lcb.rabbitmq.utils;

/*
  * @Author lcb
  * @Description  让线程进行等待
  * @Date 2022/2/6
  * @Param
  * @return
  **/
public class SleepUtils {
 public static void sleep(int second){
 try {
 Thread.sleep(1000*second);
 } catch (InterruptedException _ignored) {
 Thread.currentThread().interrupt();
 }
 }
}