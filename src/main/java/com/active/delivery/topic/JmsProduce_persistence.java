package com.active.delivery.topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.stomp.Stomp;

import javax.jms.*;

/**
 * @Description: 生产者主题持久化
 * @Author zhanghj
 * @Date 2021/2/8 15:11
 */
public class JmsProduce_persistence {
    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException {
        //1、创建连接工厂
        ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2、创建连接
        Connection connection = factory.createConnection();
        //3、创建会话session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4、创建目的地
        Topic topic = session.createTopic(TOPIC_NAME);
        //5、创建生产者
        MessageProducer producer = session.createProducer(topic);
        //*先设置持久化，后启动链接*
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();
        //6、创建消息
        for (int i = 0; i <3 ; i++) {
            TextMessage textMessage = session.createTextMessage("top消息~~~~" + i);
            //7、消息发送
            producer.send(textMessage);
        }
        //7、关闭链接
        producer.close();
        session.close();
        connection.close();
        System.out.println("topic消息发送成功");
    }

}
