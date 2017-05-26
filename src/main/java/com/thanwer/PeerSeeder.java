package com.thanwer;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.thanwer.PeerUtil.sendPeer;

/**
 * Created by Thanwer on 02/04/2017.
 */

public class PeerSeeder implements Runnable {
    //private PeerRepository peerRepository;
    //private InetAddress ipWAN;
    private InetAddress ipLAN;
    private String name;

    public PeerSeeder(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("google.com", 80));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ipLAN=socket.getLocalAddress();
        sendPeer(new Peer(name, ipLAN));
    }
        /*
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

    }*/
}