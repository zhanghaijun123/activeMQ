package com.active.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @Description: mq消费者
 * @Author zhanghj
 * @Date 2021/2/4 15:32
 */
public class JmsConsumerListener {
    public static final String DEFAULT_BROKER_URL="tcp://127.0.0.1:61616/";
    public static final String QUEUE_NAME="queue01";
    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("一号消费者");
//        1、创建连接工厂
        ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory(DEFAULT_BROKER_URL);
//        2、创建连接
        Connection connection = factory.createConnection();
        connection.start();
//        3、创建会话session(两个参数，第一个是事务，第二个是签收)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        4、创建目的地，具体是序列(queue)还是主题(topic)
        Queue queue = session.createQueue(QUEUE_NAME);
//        5、创建消费者
        MessageConsumer consumer = session.createConsumer(queue);
//        6、通过监听的方式获取消息
       consumer.setMessageListener(new MessageListener() {
           @Override
           public void onMessage(Message message) {
               if(null!=message && message instanceof TextMessage){
                   TextMessage msg= (TextMessage) message;
                   try {
                       System.out.println("=======消费者监听到的消息=="+msg.getText());
                   } catch (JMSException e) {
                       e.printStackTrace();
                   }
               }
           }
       });
//       7、使系统保持连接
       System.in.read();
//       8、关闭连接
       consumer.close();
       session.close();
       connection.close();
    }
}
