package com.thanwer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Thanwer on 02/04/2017.
 */

@Entity
public class Peer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;
    private String ipWAN;
    private String ipLAN;
    private int port;

    public Peer(){}

    public Peer(String name, String ipWAN, String ipLAN, int port) {
        this.name = name;
        this.ipWAN = ipWAN;
        this.ipLAN = ipLAN;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getIpLAN() { return ipLAN; }

    public String getIpWAN() {
        return ipWAN;
    }

    public int getPort() {
        return port;
    }

    public long getId() {
        return id;
    }


}
