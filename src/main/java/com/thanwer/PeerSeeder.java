package com.thanwer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thanwer on 02/04/2017.
 */

@Component
public class PeerSeeder implements CommandLineRunner{
    private PeerRepository peerRepository;
    private InetAddress ipWAN;
    private InetAddress ipLAN;


    @Autowired
    public PeerSeeder(PeerRepository peerRepository){
        this.peerRepository = peerRepository;
    }

    @Override
    public void run(String... strings) throws Exception{
        List<Peer> peers = new ArrayList<>();

        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));
        ipWAN = InetAddress.getByName(in.readLine());

        InetAddress addr = InetAddress.getLocalHost();
        ipLAN = InetAddress.getByName(addr.getHostAddress());


        peers.add(new Peer("local", ipWAN, ipLAN, 8080));

        peerRepository.save(peers);
    }

}
