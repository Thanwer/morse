package com.thanwer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Thanwer on 15/05/2017.
 */
public class PeerSender {
    public static void sendPeer (Peer peer) throws JsonProcessingException,ResourceAccessException {


        ObjectMapper mapper = new ObjectMapper();

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:8080/peer";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonMessage = mapper.writeValueAsString(peer);
        HttpEntity<String> entity = new HttpEntity<String>(jsonMessage,headers);
        restTemplate.postForObject(url, entity, String.class);


    }
}
