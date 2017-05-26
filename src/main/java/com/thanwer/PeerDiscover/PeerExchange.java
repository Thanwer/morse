package com.thanwer.PeerDiscover;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanwer.Peer;
import com.thanwer.PeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.TimerTask;

import static com.thanwer.PiApplication.name;

/**
 * Created by Thanwer on 26/05/2017.
 */
@Service
public class PeerExchange extends TimerTask {

    private static PeerRepository peerRepository;

    @Autowired
    public PeerExchange(PeerRepository peerRepository){
        PeerExchange.peerRepository = peerRepository;
    }

    public PeerExchange() {}

    @Override
    public void run() {
        List<Peer> peerList = peerRepository.findAll();
        for (Peer peer : peerList){
            ObjectMapper mapper = new ObjectMapper();
            RestTemplate restTemplate = new RestTemplate();
            String url = null;
            try {
                url = "http:/" + peer.getIpLAN() + ":8080/peer";
            } catch (NullPointerException e) {
                System.out.println("PEX: Peer not found");
                return;
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String jsonMessage = null;
            try {
                jsonMessage = mapper.writeValueAsString(peer);
            } catch (JsonProcessingException e) {
                System.out.println("PEX Exception");
            }
            HttpEntity<String> entity = new HttpEntity<String>(jsonMessage, headers);
            restTemplate.postForObject(url, entity, String.class);

        }


    }
}
