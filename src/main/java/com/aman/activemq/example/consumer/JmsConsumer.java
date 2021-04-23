package com.aman.activemq.example.consumer;

import com.aman.activemq.example.model.User;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
public class JmsConsumer {

    private static final Logger logger = LoggerFactory.getLogger(JmsConsumer.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Value("${service.engine.websocket.topicname}")
    private String webSocketTopic;

    @JmsListener(destination = "${active-mq.queue}")
    public void onMessage(User user) {
        try{
            logger.info("message is receiving : {}", user.toString());
                    //do additional processing if needed
            logger.info("Received Message from Queue: "+ user.getUsername());
            this.simpMessagingTemplate.convertAndSend(new ActiveMQTopic(webSocketTopic).getTopicName(), user);
        } catch(Exception e) {
            logger.error("Received Exception while processing message: "+ e);
        }

    }
}