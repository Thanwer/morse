package com.thanwer;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.thanwer.PeerUtil.sendPeer;

/**
 * Created by Thanwer on 02/04/2017.
 */

public class PeerSeeder implements Runnable{
    //private PeerRepository peerRepository;
    //private InetAddress ipWAN;
    private InetAddress ipLAN;
    private String name;
    public PeerSeeder(String name){
        this.name=name;
    }

    /*@Autowired
    public PeerSeeder(PeerRepository peerRepository){
        this.peerRepository = peerRepository;
    }

    @Override
    public void run(String... strings) throws Exception{
        List<Peer> peers = new ArrayList<>();

        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
        } catch (IOException e) {
            System.err.println("Get WAN Address... Timeout...");
        }
        ipWAN = InetAddress.getByName(in.readLine());

        InetAddress addr = InetAddress.getLocalHost();
        ipLAN = InetAddress.getByName(addr.getHostAddress());


        peers.add(new Peer("local", ipLAN));

        peerRepository.save(peers);
    }*/

    @Override
    public void run() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            ipLAN = InetAddress.getByName(addr.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            sendPeer(new Peer(name, ipLAN));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
