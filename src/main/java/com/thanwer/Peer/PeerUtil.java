package com.thanwer.Peer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.thanwer.PiApplication.bootIP;

/*
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.client.ResourceAccessException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
*/
/**
 * Created by Thanwer on 15/05/2017.
 */
@Service
public class PeerUtil {

    private static PeerRepository peerRepository;

    @Autowired
    public PeerUtil(PeerRepository peerRepository){
        PeerUtil.peerRepository = peerRepository;
    }

    public static void sendPeer (Peer peer) {
        if (!peerRepository.existsByName(peer.getName())){
            peerRepository.save(peer);
        }
    }

    public static List<InetSocketAddress> getAddressList (){
        List<Peer> peerList = peerRepository.findAll();
        List<InetSocketAddress> addressList = new ArrayList<>();
        try {
            addressList.add(new InetSocketAddress((InetAddress.getByName(bootIP)),8081));
        } catch (UnknownHostException e) {
            System.err.print("Unknown bootIP host.");
        }
        for (Peer peer : peerList)
            addressList.add(new InetSocketAddress((peer.getIpLAN()),8081));
        return addressList;
    }

    public static void getPeer(){
        List<Peer> peerList = peerRepository.findAll();
        for (Peer peer : peerList)
                System.out.println(peer.toString());
    }

    public static InetAddress getLanIP() throws IOException {
        Socket s = null;
        try {
            s = new Socket(bootIP, 80);
        } catch (ConnectException e) {
            return InetAddress.getByName(bootIP);
        }
        InetAddress ip = s.getLocalAddress();
        s.close();
        return ip;
    }

    /*
    public static  InetAddress getWanIP() throws IOException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
        } catch (IOException e) {
            System.err.println("Get WAN Address... Timeout...");
        }
        return InetAddress.getByName(in.readLine());
    }*/


}
