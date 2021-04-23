package com.aman.activemq.example.controller;

import com.aman.activemq.example.message.ActiveMqMessageSender;
import com.aman.activemq.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("msgs")
public class MessageSenderController {

    private static final Logger logger = LoggerFactory.getLogger(MessageSenderController.class);

    @Autowired
    private ActiveMqMessageSender mqMessageSender;

    @PostMapping("")
    public ResponseEntity sendMessage(@RequestBody User user,
                                      @RequestParam("queue") String queue){
        logger.info("username : {}", user.getUsername());
        mqMessageSender.sendMessage(queue, user);
        return ResponseEntity.ok().build();

    }

}
