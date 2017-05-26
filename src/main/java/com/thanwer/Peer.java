package com.thanwer;

import rice.p2p.commonapi.NodeHandle;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.net.InetAddress;

/**
 * Created by Thanwer on 02/04/2017.
 */

@Entity
public class Peer {

    @Id
    private InetAddress ipLAN;
    private String name;
    public Peer() {}

    public Peer(String name, InetAddress ipLAN) {
        this.name = name;
        this.ipLAN = ipLAN;
    }

    public Peer(String name, InetAddress ipLAN, InetAddress id) {
        this.name = name;
        this.ipLAN = ipLAN;
    }

    public String getName() {
        return name;
    }

    public InetAddress getIpLAN() { return ipLAN; }

    public InetAddress getId() {
        return ipLAN;
    }

    @Override
    public String toString() {
        return "Name=" + name + " IP=" + ipLAN;
    }

}
