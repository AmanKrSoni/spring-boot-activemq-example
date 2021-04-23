package com.aman.activemq.example.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;

@Configuration
@EnableJms
public class CoreActiveMqConfig {

    @Value("${active-mq.broker-url}")
    private String brokerUrl;

    @Value("${active-mq.user}")
    private String username;

    @Value("${active-mq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory activeMQConnectionFactory  = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setUserName(username);
        activeMQConnectionFactory.setPassword(password);
        activeMQConnectionFactory.setTrustAllPackages(true);
        return  activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        jmsTemplate.setDeliveryPersistent(true);
        jmsTemplate.setConnectionFactory(connectionFactory());
        //jmsTemplate.setPubSubDomain(false);  // enable for Pub Sub to topic. Not Required for Queue.
        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMaxMessagesPerTask(10);
        factory.setConcurrency("10");
        //factory.setSubscriptionDurable(true); // use for topic
        //factory.setClientId("brokerClientId"); // used for topic
        //factory.setPubSubDomain(false); // used for topic
        return factory;
    }

}
