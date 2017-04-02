package com.thanwer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Thanwer on 02/04/2017.
 */

@RestController
@RequestMapping(value = "/peer", method = RequestMethod.GET)
public class PeerController {
    private PeerRepository peerRepository;

    @Autowired
    public PeerController(PeerRepository peerRepository){
        this.peerRepository = peerRepository;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<Peer> getAll(){
        return peerRepository.findAll();
    }

    @RequestMapping(value = "/add",method = RequestMethod.PUT)
    public List<Peer> add(@RequestBody Peer peer){
        peerRepository.save(peer);

        return peerRepository.findAll();
    }

}
