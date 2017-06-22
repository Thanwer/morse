package com.thanwer.PeerDiscover;

import com.thanwer.Message.MessageQueue;
import com.thanwer.Message.MessageQueueRepository;
import com.thanwer.Message.MessageUtil;
import com.thanwer.Peer.Peer;
import com.thanwer.Peer.PeerRepository;
import com.thanwer.PiApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Thanwer on 15/05/2017.
 */
@Component
public class LocalPeerDiscoveryClient extends TimerTask {


    private static PeerRepository peerRepository;
    private static MessageQueueRepository messageQueueRepository;

    public LocalPeerDiscoveryClient() {}

    @Autowired
    public LocalPeerDiscoveryClient(PeerRepository peerRepository, MessageQueueRepository messageQueueRepository){
        LocalPeerDiscoveryClient.peerRepository = peerRepository;
        LocalPeerDiscoveryClient.messageQueueRepository = messageQueueRepository;
    }

    @Override
    public void run() {
        // Find the server using UDP broadcast
        try {
            //Open a random port to send the package
            DatagramSocket c = new DatagramSocket();
            c.setBroadcast(true);

            byte[] sendData = PiApplication.name.getBytes();

            //Try the 255.255.255.255 first
            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8088);
                c.send(sendPacket);
                //System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
            } catch (Exception e) {
            }

            // Broadcast the message over all the network interfaces
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue; // Don't want to broadcast to the loopback interface
                }

                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    //Skip Loopback
                    if (!interfaceAddress.getAddress().isLoopbackAddress()) {
                        InetAddress broadcast = interfaceAddress.getBroadcast();
                        if (broadcast == null) {
                            continue;
                        }
                        // Send the broadcast package!
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8088);
                        c.send(sendPacket);
                    }
                }
            }

            //Wait for a response
            byte[] recvBuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            c.receive(receivePacket);

            String message = new String(receivePacket.getData()).trim();
            Peer peer = new Peer(message, receivePacket.getAddress());

            if(!peerRepository.existsByName(peer.getName())){
                peerRepository.save(peer);
            }
            if(messageQueueRepository.existsByName(peer.getName())){
                List<MessageQueue> messageQueueList = new ArrayList<>(messageQueueRepository.findByName(peer.getName()));
                for (MessageQueue messageQueue : messageQueueList) {
                    String name = messageQueue.getName();
                    String text = messageQueue.getText();
                    MessageUtil.sendMessage(name, text);
                }
            }


            //Close the port!
            c.close();
        } catch (IOException ex) {
            Logger.getLogger(LocalPeerDiscoveryClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
