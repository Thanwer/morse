package com.thanwer.PeerDiscover;

import java.io.IOException;
import java.net.*;
import java.util.*;

import com.thanwer.PeerUtil;
import org.mpisws.p2p.transport.multiaddress.MultiInetSocketAddress;
import rice.environment.Environment;
import rice.pastry.*;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.socket.internet.InternetPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;

import static com.thanwer.PeerUtil.*;

/**
 * Created by Thanwer on 18/05/2017.
 */
public class DHTPDApp {

    public DHTPDApp(int bindport, InetSocketAddress bootaddress, Environment env) throws Exception {

        // Generate the NodeIds Randomly
        NodeIdFactory nidFactory = new RandomNodeIdFactory(env);

        // construct the PastryNodeFactory
        //PastryNodeFactory factory = new SocketPastryNodeFactory(nidFactory, bindport, env);
        //InternetPastryNodeFactory  factory = new InternetPastryNodeFactory(nidFactory, null, bindport, env, null, probeAddresses, null);
        InternetPastryNodeFactory factory = new InternetPastryNodeFactory(nidFactory, bindport, env);
        // construct a new node
        //MultiInetSocketAddress pAddress = new MultiInetSocketAddress(new InetSocketAddress(getWanIP(),bindport),new InetSocketAddress(getLanIP(),bindport));
        //PastryNode node = factory.newNode(null,pAddress);
        //NodeHandle bootHandle = ((SocketPastryNodeFactory)factory).getNodeHandle(bootaddress);
        PastryNode node = factory.newNode();

        // construct a new scribe application
        DHTPDClient app = new DHTPDClient(node);
        node.boot(bootaddress);

        // the node may require sending several messages to fully boot into the ring
        synchronized (node) {
            while (!node.isReady() && !node.joinFailed()) {
                // delay so we don't busy-wait
                node.wait(500);

                // abort if can't join
                if (node.joinFailed()) {
                    throw new IOException("Could not join the FreePastry ring.  Reason:" + node.joinFailedReason());
                }
            }

            System.out.println("DHT OK! " + node);
        }
        app.subscribe();
        app.startPublishTask();

        env.getTimeSource().sleep(5000);
        //printTree(apps);
    }

}