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
    InetAddress ipLan;

    public DHTPDAnnounce(NodeHandle from, String name, InetAddress ipLan) {
        this.from = from;
        this.name = name;
        this.ipLan = ipLan;
    }

    public String getName() {
        return name;
    }

    public NodeHandle getFrom() {
        return from;
    }

    public InetAddress getIpLan() {
        return ipLan;
    }

    public String toString() {
        return "DHTPDAnnounce #"+name+" from "+from+"IP: "+ipLan;}
}