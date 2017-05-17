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
    }

    public static void getPeer(){
        List<Peer> peerList = peerRepository.findAll();
        for (Peer peer : peerList)
                System.out.println(peer.toString());
    }
}
