package com.thanwer.PeerDiscover;

/**
 * Created by Thanwer on 18/05/2017.
 */

import com.thanwer.Peer.Peer;
import com.thanwer.Peer.PeerRepository;
import com.thanwer.Peer.PeerUtil;
import com.thanwer.PiApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class DHTPDClient implements ScribeClient, Application {


    private static PeerRepository peerRepository;

    public DHTPDClient() { }

    @Autowired
    public DHTPDClient(PeerRepository peerRepository){
        DHTPDClient.peerRepository = peerRepository;
    }

    int seqNum = 0;
    private Scribe myScribe;
    private Topic DiscoverTopic;

    private Endpoint endpoint;

    DHTPDClient(Node node) {
        this.endpoint = node.buildEndpoint(this, "PeerDiscover");
        myScribe = new ScribeImpl(node,"PeerDiscover");
        DiscoverTopic = new Topic(new PastryIdFactory(node.getEnvironment()), "PeerDiscoverTopic");
        endpoint.register();
    }

    public void subscribe() {
        myScribe.subscribe(DiscoverTopic, this);
    }

    void startPublishTask() {
        CancellableTask publishTask = endpoint.scheduleMessage(new PublishContent(), 1000, 60000);
    }


    public void deliver(Id id, Message message) {
        if (message instanceof PublishContent) {
            try {
                sendMulticast();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMulticast() throws IOException {
        DHTPDAnnounce myMessage = new DHTPDAnnounce(endpoint.getId(), PiApplication.name, PeerUtil.getLanIP());
        myScribe.publish(DiscoverTopic, myMessage);
    }

    public void deliver(Topic topic, ScribeContent content) {
        String c= content.toString();
        String[] parts = c.split("/");
        parts[1] = parts[1].substring(0, parts[1].indexOf(" "));
        String name = parts[1];
        String ip =parts[2];

        DHTPDAnnounce p = null;
        try {
            p = new DHTPDAnnounce(name, InetAddress.getByName(ip));
        } catch (UnknownHostException e) {
        }

        if (!peerRepository.existsByName(p.getName()))
            peerRepository.save(new Peer(p.getName(), p.getIP()));

    }

    public boolean anycast(Topic topic, ScribeContent content) {
        boolean returnValue = myScribe.getEnvironment().getRandomSource().nextInt(3) == 0;
        System.out.println("DHTPDClient.anycast("+topic+","+ content +"):"+returnValue);
        return returnValue;
    }

    public void childAdded(Topic topic, NodeHandle child) {
    }

    public void childRemoved(Topic topic, NodeHandle child) {
    }

    public void subscribeFailed(Topic topic) {
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