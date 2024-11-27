package com.example.FestivalDashboard.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private KafkaProducerService producerService;

    @PostMapping("/sendMessage")
    public String sendMessageToKafka(@RequestParam("message") String message) {
        producerService.sendMessage(message);
        return "Message sent to Kafka: " + message;
    }
}