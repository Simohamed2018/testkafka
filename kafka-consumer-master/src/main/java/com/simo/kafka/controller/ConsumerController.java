package com.simo.kafka.controller;

import com.simo.kafka.model.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public class ConsumerController {

    List<String> messages = new ArrayList<>();

    List<User> users= new ArrayList<>();

    User userFromTopic = null;
    String message ="";

    @GetMapping("/messages")
    public List<String> consumeMsgs() {
        return messages;
    }

    @GetMapping("/message")
    public String consumeMsg() {
        return message;
    }

    @GetMapping("/user")
    public User consumeJsonMessage() {
        return userFromTopic;
    }

    @GetMapping("/users")
    public List<User> consumeJsonUsers() {
        return users;
    }

    @KafkaListener(groupId = "pauseTechno-1", topics = "pauseTechno", containerFactory = "kafkaListenerContainerFactory")
    public String getMsgFromTopic(String data) {
        message = data;
        messages.add(message);

        return message;
    }

  /*  @KafkaListener(groupId = "pauseTechno-2", topics = "pauseTechno", containerFactory = "userKafkaListenerContainerFactory")
    public List<User> getAllUsers() {
        return users;
    }
*/
    @KafkaListener(groupId = "pauseTechno-2", topics = "pauseTechno", containerFactory = "userKafkaListenerContainerFactory")
    public User getJsonMsgFromTopic(User user) {
        userFromTopic = user;
        users.add(userFromTopic);
        return userFromTopic;
    }

    /*@KafkaListener(groupId = "pauseTechno-2", topics = "pauseTechno", containerFactory = "userKafkaListenerContainerFactory")
    public List<User>  getUsers() {
        return users;
    }*/
}
