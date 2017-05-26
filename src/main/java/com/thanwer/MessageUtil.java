package com.thanwer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import rice.p2p.commonapi.Application;
import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.RouteMessage;
import rice.pastry.NodeHandle;

import javax.validation.constraints.Null;

import static com.thanwer.PiApplication.name;

/**
 * Created by Thanwer on 13/05/2017.
 */
@Service
public class MessageUtil implements Application{

    private static PeerRepository peerRepository;

    @Autowired
    public MessageUtil(PeerRepository peerRepository){
        MessageUtil.peerRepository = peerRepository;
    }

    static void sendMessage(String id, String text) throws JsonProcessingException,ResourceAccessException {

        ObjectMapper mapper = new ObjectMapper();

        Peer peer = peerRepository.findByName(id);
        RestTemplate restTemplate = new RestTemplate();
        String url = null;
        try {
            url = "http:/" + peer.getIpLAN() + ":8080/messages";
        } catch (NullPointerException e) {
            System.out.println("Peer not found");
            return;
        }

        Message test = new Message(name, text);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonMessage = mapper.writeValueAsString(test);
        HttpEntity<String> entity = new HttpEntity<String>(jsonMessage, headers);
        restTemplate.postForObject(url, entity, String.class);

    }

    @Override
    public boolean forward(RouteMessage routeMessage) {
        return false;
    }

    @Override
    public void deliver(Id id, rice.p2p.commonapi.Message message) {
        System.out.println(message);
    }

    @Override
    public void update(rice.p2p.commonapi.NodeHandle nodeHandle, boolean b) {

    }
}
