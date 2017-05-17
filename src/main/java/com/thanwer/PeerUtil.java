package com.thanwer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Thanwer on 15/05/2017.
 */
@Service
public class PeerUtil {

    private static PeerRepository peerRepository;

    @Autowired
    public PeerUtil(PeerRepository peerRepository){
        PeerUtil.peerRepository = peerRepository;
    }

    public static void sendPeer (Peer peer) throws JsonProcessingException,ResourceAccessException {
        peerRepository.save(peer);
        /*
        ObjectMapper mapper = new ObjectMapper();

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:8080/peer";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonMessage = mapper.writeValueAsString(peer);
        HttpEntity<String> entity = new HttpEntity<String>(jsonMessage,headers);
        restTemplate.postForObject(url, entity, String.class);
        */

    }

    public static void getPeer(){
        List<Peer> peerList = peerRepository.findAll();
        //RestTemplate restTemplate = new RestTemplate();
        //String url = "http://127.0.0.1:8080/peer";

        //List<LinkedHashMap<String, Object>> peerList = restTemplate.getForObject(url, List.class);
        for (Peer peer : peerList)
                System.out.println(peer.toString());
    }
}
