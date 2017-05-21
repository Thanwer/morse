package com.thanwer.PeerDiscover;

import com.thanwer.Peer;
import com.thanwer.PeerRepository;
import com.thanwer.PiApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Thanwer on 15/05/2017.
 */
@Component
public class LocalPeerDiscoveryClient extends TimerTask {


    DatagramSocket c;
    private static PeerRepository peerRepository;

    public LocalPeerDiscoveryClient() {}

    @Autowired
    public LocalPeerDiscoveryClient(PeerRepository peerRepository){
        LocalPeerDiscoveryClient.peerRepository = peerRepository;
    }

    @Override
    public void run() {
        // Find the server using UDP broadcast
        try {
            //Open a random port to send the package
            c = new DatagramSocket();
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
                    if (interfaceAddress.getAddress().isLoopbackAddress()) {
                        continue;
                    } else {
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
            peerRepository.save(new Peer(message, receivePacket.getAddress()));
            //}

            //Close the port!
            c.close();
        } catch (IOException ex) {
            Logger.getLogger(LocalPeerDiscoveryClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
