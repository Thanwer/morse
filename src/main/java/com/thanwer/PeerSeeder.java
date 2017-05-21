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
        sendPeer(new Peer(name, ipLAN));

    }
}
