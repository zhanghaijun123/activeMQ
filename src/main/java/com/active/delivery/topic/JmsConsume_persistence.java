package com.active.delivery.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Description: 主题消费者
 * @Author zhanghj
 * @Date 2021/2/8 15:44
 */
public class JmsConsume_persistence {
    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        //设置客户端id,向mq注册自己
        connection.setClientID("zhanghaijun");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        // 创建一个topic订阅者对象。一参是topic，二参是订阅者名称
        TopicSubscriber durableSubscriber = session.createDurableSubscriber(topic, "remarks.......");
        //之后再开启链接
        connection.start();
        Message message = durableSubscriber.receive();
        while (null != message){
            TextMessage textMessage = (TextMessage)message;
            System.out.println(" 收到的持久化 topic ："+textMessage.getText());
            message = durableSubscriber.receive();
        }
        session.close();
        connection.close();
    }
}
