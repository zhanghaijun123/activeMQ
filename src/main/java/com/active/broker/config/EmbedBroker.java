package com.active.broker.config;

import org.apache.activemq.broker.BrokerService;

/**
 * @Description: broker启动器
 * @Author zhanghj
 * @Date 2021/2/8 16:40
 */
public class EmbedBroker {
    public static void main(String[] args) throws Exception {
        BrokerService brokerService=new BrokerService();
        brokerService.setPopulateJMSXUserID(true);
        brokerService.addConnector("tcp://127.0.0.1:61616");
        brokerService.start();
    }
}
