package com.thanwer.PeerDiscover;

import java.io.IOException;

import com.thanwer.Peer.PeerUtil;
import rice.environment.Environment;
import rice.pastry.*;
import rice.pastry.socket.internet.InternetPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;


/**
 * Created by Thanwer on 18/05/2017.
 */
public class DHTPDApp implements Runnable{
    private Environment env;

    public DHTPDApp(Environment env) {
        this.env = env;
    }

    @Override
    public void run() {

        NodeIdFactory nidFactory = new RandomNodeIdFactory(env);
        InternetPastryNodeFactory factory = null;
        try {
            factory = new InternetPastryNodeFactory(nidFactory, 8081, env);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PastryNode node = factory.newNode();

        // construct a new scribe application
        DHTPDClient app = new DHTPDClient(node);
        node.boot(PeerUtil.getAddressList());

        // the node may require sending several messages to fully boot into the ring
        synchronized (node) {
            while (!node.isReady() && !node.joinFailed()) {
                // delay so we don't busy-wait
                try {
                    node.wait(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // abort if can't join
                //System.out.println("DHT OFF! ");
            }
            //System.out.println("DHT OK! ");
        }
        app.subscribe();
        app.startPublishTask();

        try {
            env.getTimeSource().sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}