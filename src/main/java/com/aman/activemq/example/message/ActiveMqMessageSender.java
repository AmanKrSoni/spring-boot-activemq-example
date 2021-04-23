package com.aman.activemq.example.message;

import com.aman.activemq.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqMessageSender {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMqMessageSender.class);

    @Autowired
    JmsTemplate jmsTemplate;


    public void sendMessage(String  queue, User user){
        try{
            logger.info("Attempting Send message to Queue: "+ queue);
            jmsTemplate.convertAndSend(queue, user);
        } catch(Exception e){
            logger.error("Recieved Exception during send Message: ", e);
        }
    }
}
