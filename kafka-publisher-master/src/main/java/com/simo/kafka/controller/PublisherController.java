package com.simo.kafka.controller;

import com.simo.kafka.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PublisherController {
private static int counter=0;
    @Autowired
    private KafkaTemplate<String, Object> template;
    private List<User> mesUsers= new ArrayList<>();
    private String topic = "pauseTechno";

    @GetMapping("/publish/{name}")
    public String publishMessage(@PathVariable String name) {
        template.send(topic, name );
        return "Data published "+name ;
    }

    @GetMapping("/publishUser/{id}")
    public String publishMessage(@PathVariable int id) {
        User user = new User(id, "User "+id, new String[] { "Bangalore", "BTM", "house 90" });
        template.send(topic, user);
        return "Json Data published : "+id;
    }

    @PostMapping("/publishUser")
    public String publishMessage(@RequestBody User user) {
        user.setId(counter+1);
        user.setAddress(new String[] { "Bangalore", "BTM", "house 90" });
        // user = new User(id, "User "+id, new String[] { "Bangalore", "BTM", "house 90" });
        mesUsers.add(user);
        counter++;
       // template.send(topic, user);
        return "Json Data published : "+user.getId()+"---"+user.getName();
    }
    @GetMapping("/user/{id}")
    public String publishuser(@PathVariable int id) {
        User user = mesUsers.stream()
                .filter(u -> id == u.getId())
                .findAny()
                .orElse(null);
        if (user == null) {
            return "No data send : "+id +" Not Found ";
        }
        user.setName(user.getName().toUpperCase());
        template.send(topic, user);
        return "Send Json Data published : "+id;
    }

}
