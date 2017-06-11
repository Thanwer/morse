package com.thanwer.Peer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Thanwer on 02/04/2017.
 */
@RestController
@RequestMapping(value = "/peer")
public class PeerController {

    private PeerRepository peerRepository;

    @Autowired
    public PeerController(PeerRepository peerRepository){
        this.peerRepository = peerRepository;
    }

    @RequestMapping(method=RequestMethod.GET)
    public List<Peer> getAll(){
        return peerRepository.findAll();
    }


    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Peer> add(@RequestBody List<Peer> peerList){
        for (Peer peer : peerList){
            if(peerRepository.existsByName(peer.getName())){
                peerRepository.save(peer);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}