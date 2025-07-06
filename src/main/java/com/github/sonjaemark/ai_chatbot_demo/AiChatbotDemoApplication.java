package com.github.sonjaemark.ai_chatbot_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AiChatbotDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiChatbotDemoApplication.class, args);

		 HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> wakeupResponseEntity = restTemplate.exchange(
            "https://ai-chatbot-0mmy.onrender.com/",
            HttpMethod.GET,
            entity,
            String.class
        );

		wakeupResponseEntity.getBody();
		System.out.println("Wakeup response: " + wakeupResponseEntity.getBody());
		System.out.println("Application started successfully.");
	}

}
