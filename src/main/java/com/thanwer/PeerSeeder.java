package com.thanwer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.activation.CommandObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thanwer on 02/04/2017.
 */

@Component
public class PeerSeeder implements CommandLineRunner{
    private PeerRepository peerRepository;

    @Autowired
    public PeerSeeder(PeerRepository peerRepository){
        this.peerRepository = peerRepository;
    }

    @Override
    public void run(String... strings) throws Exception{
        List<Peer> peers = new ArrayList<>();

        peers.add(new Peer("Thanwer", "127.0.0.1", 8000));

        peerRepository.save(peers);
    }

}
