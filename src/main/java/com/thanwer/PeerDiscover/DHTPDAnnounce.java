package com.thanwer.PeerDiscover;

import rice.p2p.commonapi.NodeHandle;
import rice.p2p.scribe.ScribeContent;

import java.net.InetAddress;

/**
 * Created by Thanwer on 18/05/2017.
 */
public class DHTPDAnnounce implements ScribeContent {
    private final String name;
    NodeHandle from;
    InetAddress ip;

    public DHTPDAnnounce(NodeHandle from, String name, InetAddress ip) {
        this.from = from;
        this.name = name;
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public NodeHandle getFrom() {
        return from;
    }

    public InetAddress getIP() {
        return ip;
    }

    public String toString() {
        return "DHTPDAnnounce #"+name+" from "+from+"IP: "+ip;}
}