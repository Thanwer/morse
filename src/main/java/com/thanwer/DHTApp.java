package com.thanwer;

import rice.p2p.commonapi.Application;
import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Message;
import rice.p2p.commonapi.Node;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.RouteMessage;

/**
 * Created by Thanwer on 17/05/2017.
 */
public class DHTApp implements Application{
    protected Endpoint endpoint;

    public DHTApp(Node node) {
        // We are only going to use one instance of this application on each PastryNode
        this.endpoint = node.buildEndpoint(this, "myinstance");

        // the rest of the initialization code could go here

        // now we can receive messages
        this.endpoint.register();
    }

    /**
     * Called to route a message to the id
     */
    public void routeDHTMsg(Id id) {
        System.out.println(this+" sending to "+id);
        Message msg = new DHTMsg(endpoint.getId(), id);
        endpoint.route(id, msg, null);
    }

    /**
     * Called to directly send a message to the nh
     */
    public void routeDHTMsgDirect(NodeHandle nh) {
        System.out.println(this+" sending direct to "+nh);
        Message msg = new DHTMsg(endpoint.getId(), nh.getId());
        endpoint.route(null, msg, nh);
    }

    /**
     * Called when we receive a message.
     */
    public void deliver(Id id, Message message) {
        System.out.println(this+" received "+message);
    }

    /**
     * Called when you hear about a new neighbor.
     * Don't worry about this method for now.
     */
    public void update(NodeHandle handle, boolean joined) {
    }

    /**
     * Called a message travels along your path.
     * Don't worry about this method for now.
     */
    public boolean forward(RouteMessage message) {
        return true;
    }

    public String toString() {
        return "DHTApp "+endpoint.getId();
    }
}
