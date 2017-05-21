package com.thanwer.PeerDiscover;

/**
 * Created by Thanwer on 18/05/2017.
 */

import rice.p2p.commonapi.*;
import rice.p2p.commonapi.Application;
import rice.p2p.commonapi.CancellableTask;
import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Message;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.RouteMessage;
import rice.p2p.scribe.Scribe;
import rice.p2p.scribe.ScribeClient;
import rice.p2p.scribe.ScribeContent;
import rice.p2p.scribe.ScribeImpl;
import rice.p2p.scribe.Topic;
import rice.pastry.commonapi.PastryIdFactory;

/**
 * We implement the Application interface to receive regular timed messages (see lesson5).
 * We implement the ScribeClient interface to receive scribe messages (called ScribeContent).
 *
 * @author Jeff Hoye
 */
public class DHTPDClient implements ScribeClient, Application {


    int seqNum = 0;
    CancellableTask publishTask;
    Scribe myScribe;
    Topic DiscoverTopic;
    protected Endpoint endpoint;

    public DHTPDClient(Node node) {
        this.endpoint = node.buildEndpoint(this, "PeerDiscover");

        // construct Scribe
        myScribe = new ScribeImpl(node,"PeerDiscover");

        // construct the topic
        DiscoverTopic = new Topic(new PastryIdFactory(node.getEnvironment()), "PeerDiscoverTopic");
        // Start!!
        endpoint.register();
    }

    /**
     * Subscribes to DiscoverTopic.
     */
    public void subscribe() {
        myScribe.subscribe(DiscoverTopic, this);
    }

    /**
     * Starts the publish task.
     */
    public void startPublishTask() {
        publishTask = endpoint.scheduleMessage(new PublishContent(), 6000, 6000);
    }


    /**
     * Part of the Application interface.  Will receive PublishContent every so often.
     */
    public void deliver(Id id, Message message) {
        if (message instanceof PublishContent) {
            sendMulticast();
            sendAnycast();
        }
    }

    /**
     * Sends the multicast message.
     */
    public void sendMulticast() {
        System.out.println("Node "+endpoint.getLocalNodeHandle()+" broadcasting "+seqNum);
        DHTPDAnnounce myMessage = new DHTPDAnnounce(endpoint.getLocalNodeHandle(), seqNum);
        myScribe.publish(DiscoverTopic, myMessage);
        seqNum++;
    }

    /**
     * Called whenever we receive a published message.
     */
    public void deliver(Topic topic, ScribeContent content) {
        System.out.println("DHTPDClient.deliver("+topic+","+content+")");
        /*if (((DHTPDAnnounce)content).from == null) {
            new Exception("Stack Trace").printStackTrace();
        }*/
    }

    /**
     * Sends an anycast message.
     */
    public void sendAnycast() {
        System.out.println("Node "+endpoint.getLocalNodeHandle()+" anycasting "+seqNum);
        DHTPDAnnounce myMessage = new DHTPDAnnounce(endpoint.getLocalNodeHandle(), seqNum);
        myScribe.anycast(DiscoverTopic, myMessage);
        seqNum++;
    }

    /**
     * Called when we receive an anycast.  If we return
     * false, it will be delivered elsewhere.  Returning true
     * stops the message here.
     */
    public boolean anycast(Topic topic, ScribeContent content) {
        boolean returnValue = myScribe.getEnvironment().getRandomSource().nextInt(3) == 0;
        System.out.println("DHTPDClient.anycast("+topic+","+content+"):"+returnValue);
        return returnValue;
    }

    public void childAdded(Topic topic, NodeHandle child) {
//    System.out.println("DHTPDClient.childAdded("+topic+","+child+")");
    }

    public void childRemoved(Topic topic, NodeHandle child) {
//    System.out.println("DHTPDClient.childRemoved("+topic+","+child+")");
    }

    public void subscribeFailed(Topic topic) {
//    System.out.println("DHTPDClient.childFailed("+topic+")");
    }

    public boolean forward(RouteMessage message) {
        return true;
    }


    public void update(NodeHandle handle, boolean joined) {

    }

    class PublishContent implements Message {
        public int getPriority() {
            return MAX_PRIORITY;
        }
    }


    /************ Some passthrough accessors for the myScribe *************/
    public boolean isRoot() {
        return myScribe.isRoot(DiscoverTopic);
    }

    public NodeHandle getParent() {
        // NOTE: Was just added to the Scribe interface.  May need to cast myScribe to a
        // ScribeImpl if using 1.4.1_01 or older.
        return ((ScribeImpl)myScribe).getParent(DiscoverTopic);
        //return myScribe.getParent(DiscoverTopic);
    }

    public NodeHandle[] getChildren() {
        return myScribe.getChildren(DiscoverTopic);
    }

}