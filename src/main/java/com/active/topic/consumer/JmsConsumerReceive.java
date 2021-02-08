package com.active.topic.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Description: mq消费者
 * @Author zhanghj
 * @Date 2021/2/4 15:32
 */
public class JmsConsumerReceive {
    public static final String DEFAULT_BROKER_URL="tcp://127.0.0.1:61616/";
    public static final String TOPIC_NAME="topic01";
    public static void main(String[] args) throws JMSException {
//        1、创建连接工厂
        ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
//        2、创建连接
        Connection connection = factory.createConnection();
        connection.start();
//        3、创建会话session(两个参数，第一个是事务，第二个是签收)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        4、创建目的地，具体是序列(queue)还是主题(topic)
        Topic topic = session.createTopic(TOPIC_NAME);
//        5、创建消费者
        MessageConsumer consumer = session.createConsumer(topic);
        while (true){
            /**
             * consumer.receive()若不设置等待时间则程序会一直监听等待
             *                   若设置等待时间，过了等待时间自动关闭
             */
            TextMessage textMessage = (TextMessage) consumer.receive();
//            TextMessage textMessage = (TextMessage) consumer.receive(4000L);
            if(null!=textMessage){
                System.out.println("接收到的消息"+textMessage.getText());
            }else {
                break;
            }
        }
//      6、关闭连接
        consumer.close();
        session.close();
        connection.close();

    }
}
