package com.thanwer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Thanwer on 05/04/2017.
 */

@Component
public class MessageSeeder implements CommandLineRunner{
    private MessageRepository messageRepository;

    @Autowired

    public MessageSeeder(MessageRepository messageRepository) {this.messageRepository = messageRepository; }

    @Override
    public void run(String... strings) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages = new ArrayList<>();

        messages.add(new Message("."));

        messageRepository.save(messages);


        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/messages/receive";
        Message test = new Message("Tester", Date.from(Instant.now()),"Test Text");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonMessage = mapper.writeValueAsString(test);
        HttpEntity<String> entity = new HttpEntity<String>(jsonMessage,headers);
        restTemplate.postForObject(url, entity, String.class);


    }

}
