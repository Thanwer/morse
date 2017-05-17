package com.thanwer;

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
    public ResponseEntity<Peer> add(@RequestBody Peer peer){
        peerRepository.save(peer);
        //System.out.println(peer.toString());

        return new ResponseEntity<>(peer, HttpStatus.OK);
    }

}

/*
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
        List<Peer> p = peerRepository.findAll();
        for (Peer obj: p){
            System.out.println(obj.toString());
        }


        return p;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity<Peer> add(@RequestBody Peer peer){
        peerRepository.save(peer);
        System.out.println(peer.toString());
        return new ResponseEntity<>(peer, HttpStatus.OK);
    }


    public PeerRepository getPeerRepository() {
        return peerRepository;
    }
}
*/