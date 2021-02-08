package com.active.delivery.queue.producer;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Description: mq生产者
 * @Author zhanghj
 * @Date 2021/2/4 14:32
 */
public class JmsProducer {
    public static final String DEFAULT_BROKER_URL="tcp://127.0.0.1:61616/";
    public static final String QUEUE_NAME="queueDeliv01";
    public static void main(String[] args) throws JMSException {
//        1、创建连接工厂
        ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
//        2、创建连接
        Connection connection = factory.createConnection();
        connection.start();
//        3、创建会话session(两个参数，第一个是事务，第二个是签收)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        4、创建目的地，具体是序列(queue)还是主题(topic)
        Queue queue = session.createQueue(QUEUE_NAME);
//        5、创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//        6、通过messageProducer生产3条信息发送到MQ的消息队列中
        for (int i = 0; i <3; i++) {
//           7、创建消息
            TextMessage textMessage = session.createTextMessage("msg====" + i);
            if(i==1){
                textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
            }
//            8、通过messageProducer发送到MQ
            messageProducer.send(textMessage);
        }
//        9、关闭连接
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("==========消息发布完成==========");
    }
}
