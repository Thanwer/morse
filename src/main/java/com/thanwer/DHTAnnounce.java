package com.thanwer;

import rice.p2p.commonapi.NodeHandle;
import rice.p2p.scribe.ScribeContent;

/**
 * Created by Thanwer on 18/05/2017.
 */
public class DHTAnnounce implements ScribeContent {
    /**
     * The source of this content.
     */
    NodeHandle from;

    /**
     * The sequence number of the content.
     */
    int seq;

    /**
     * Simple constructor.  Typically, you would also like some
     * interesting payload for your application.
     *
     * @param from Who sent the message.
     * @param seq the sequence number of this content.
     */
    public DHTAnnounce(NodeHandle from, int seq) {
        this.from = from;
        this.seq = seq;
    }
    public String toString() {
        return "DHTAnnounce #"+seq+" from "+from;
    }
}