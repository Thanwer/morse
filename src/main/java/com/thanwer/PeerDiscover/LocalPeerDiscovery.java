package com.thanwer.PeerDiscover;

import com.thanwer.Peer.Peer;
import com.thanwer.Peer.PeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Thanwer on 15/05/2017.
 */
@Service
public class LocalPeerDiscovery implements Runnable {

    private DatagramSocket socket;

    private static PeerRepository peerRepository;

    public LocalPeerDiscovery() {}

    @Autowired
    public LocalPeerDiscovery(PeerRepository peerRepository){
        LocalPeerDiscovery.peerRepository = peerRepository;
    }

    @Override
    public void run() {
        try {
            //Keep a socket open to listen to all the UDP trafic that is destined for this port
            socket = new DatagramSocket(8088, InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);

            while (true) {
                //Receive a packet
                byte[] recvBuf = new byte[15000];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);

                String message = new String(packet.getData()).trim();
                try {
                    Peer peer = new Peer(message, packet.getAddress());
                    if (!peerRepository.existsByName(peer.getName())){
                        peerRepository.save(peer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(LocalPeerDiscovery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
