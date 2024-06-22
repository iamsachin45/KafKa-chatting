package com.example.kafka.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafka.kafka.producer.MessageProducer;


@SpringBootApplication
@RestController
public class KafkaApplication{

	@Autowired
	private MessageProducer messageProducer;

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}

	@Bean
    public NewTopic topic() {
        return TopicBuilder.name("my-topic")
                .partitions(10)
                .replicas(1)
                .build();
    }

	@GetMapping("/find")
	public String getMethodName() {
		messageProducer.sendMessage("my-topic", "Hello");
		return "Message Sent";
	}

	@PostMapping("/send")
    public String sendMessage(@RequestParam("message") String message) {
        messageProducer.sendMessage("my-topic", message);
        return "Message sent: " + message;
    }
	
}
