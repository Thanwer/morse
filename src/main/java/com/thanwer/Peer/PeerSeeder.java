package com.thanwer.Peer;

import org.springframework.dao.DataIntegrityViolationException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Thanwer on 02/04/2017.
 */

public class PeerSeeder implements Runnable {
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
        InetAddress ipLAN = socket.getLocalAddress();
        try {
            PeerUtil.sendPeer(new Peer(name, ipLAN));
        } catch (DataIntegrityViolationException e) {

        }
    }
}