package com.thanwer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import rice.environment.Environment;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.NodeHandleSet;
import rice.pastry.NodeHandle;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.PastryNodeFactory;
import rice.pastry.leafset.LeafSet;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;


/**
 * Created by Thanwer on 17/05/2017.
 */
public class DHTNode {
    /**
     * This constructor sets up a PastryNode.  It will bootstrap to an
     * existing ring if it can find one at the specified location, otherwise
     * it will start a new ring.
     *
     * @param bindport the local port to bind to
     * @param bootaddress the IP:port of the node to boot from
     * @param env the environment for these nodes
     */
    public DHTNode(int bindport, InetSocketAddress bootaddress, Environment env) throws Exception {

        // Generate the NodeIds Randomly
        NodeIdFactory nidFactory = new RandomNodeIdFactory(env);

        // construct the PastryNodeFactory, this is how we use rice.pastry.socket
        PastryNodeFactory factory = new SocketPastryNodeFactory(nidFactory, bindport, env);

        // construct a node
        PastryNode node = factory.newNode();

        // construct a new DHTApp
        DHTApp app = new DHTApp(node);

        node.boot(bootaddress);

        // the node may require sending several messages to fully boot into the ring
        synchronized(node) {
            while(!node.isReady() && !node.joinFailed()) {
                // delay so we don't busy-wait
                node.wait(500);

                // abort if can't join
                if (node.joinFailed()) {
                    throw new IOException("Could not join the FreePastry ring.  Reason:"+node.joinFailedReason());
                }
            }
        }

        System.out.println("Finished creating new node "+node);




        // wait 10 seconds
        env.getTimeSource().sleep(10000);


        // route 10 messages
        for (int i = 0; i < 10; i++) {
            // pick a key at random
            Id randId = nidFactory.generateNodeId();

            // send to that key
            app.routeDHTMsg(randId);

            // wait a sec
            env.getTimeSource().sleep(1000);
        }

        // wait 10 seconds
        env.getTimeSource().sleep(10000);

        // send directly to my leafset
        LeafSet leafSet = node.getLeafSet();

        // this is a typical loop to cover your leafset.  Note that if the leafset
        // overlaps, then duplicate nodes will be sent to twice
        for (int i=-leafSet.ccwSize(); i<=leafSet.cwSize(); i++) {
            if (i != 0) { // don't send to self
                // select the item
                NodeHandle nh = leafSet.get(i);

                // send the message directly to the node
                app.routeDHTMsgDirect(nh);

                // wait a sec
                env.getTimeSource().sleep(1000);
            }
        }
    }
}
