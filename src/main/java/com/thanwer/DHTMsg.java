package com.thanwer;

import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Message;

/**
 * Created by Thanwer on 17/05/2017.
 */
public class DHTMsg implements Message {
    /**
     * Where the Message came from.
     */
    Id from;
    /**
     * Where the Message is going.
     */
    Id to;

    /**
     * Constructor.
     */
    public DHTMsg(Id from, Id to) {
        this.from = from;
        this.to = to;
    }

    public String toString() {
        return "DHTMsg from "+from+" to "+to;
    }

    /**
     * Use low priority to prevent interference with overlay maintenance traffic.
     */
    public int getPriority() {
        return Message.LOW_PRIORITY;
    }
}
