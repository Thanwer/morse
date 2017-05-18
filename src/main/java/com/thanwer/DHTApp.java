package com.thanwer;

import java.io.IOException;
import java.net.*;
import java.util.*;

import rice.environment.Environment;
import rice.p2p.commonapi.NodeHandle;
import rice.pastry.*;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;
import rice.pastry.transport.TransportPastryNodeFactory;

/**
 * Created by Thanwer on 18/05/2017.
 */
public class DHTApp {

    /**
     * this will keep track of our Scribe applications
     */
    Vector<DHTClient> apps = new Vector<DHTClient>();

    /**
     * Based on the rice.tutorial.lesson4.DistTutorial
     * <p>
     * This constructor launches numNodes PastryNodes. They will bootstrap to an
     * existing ring if one exists at the specified location, otherwise it will
     * start a new ring.
     *
     * @param bindport    the local port to bind to
     * @param bootaddress the IP:port of the node to boot from
     * @param numNodes    the number of nodes to create in this JVM
     * @param env         the Environment
     */
    public DHTApp(int bindport, InetSocketAddress bootaddress,
                  int numNodes, Environment env) throws Exception {

        // Generate the NodeIds Randomly
        NodeIdFactory nidFactory = new RandomNodeIdFactory(env);

        // construct the PastryNodeFactory, this is how we use rice.pastry.socket
        PastryNodeFactory factory = new SocketPastryNodeFactory(nidFactory, bindport, env);

        // loop to construct the nodes/apps
        for (int curNode = 0; curNode < numNodes; curNode++) {
            // construct a new node
            PastryNode node = factory.newNode();

            // construct a new scribe application
            DHTClient app = new DHTClient(node);
            apps.add(app);

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
            }

            System.out.println("Finished creating new node: " + node);
        }

        // for the first app subscribe then start the publishtask
        Iterator<DHTClient> i = apps.iterator();
        DHTClient app = (DHTClient) i.next();
        app.subscribe();
        app.startPublishTask();
        // for all the rest just subscribe
        while (i.hasNext()) {
            app = (DHTClient) i.next();
            app.subscribe();
        }

        // now, print the tree
        env.getTimeSource().sleep(5000);
        printTree(apps);
    }

    /**
     * Note that this function only works because we have global knowledge. Doing
     * this in an actual distributed environment will take some more work.
     *
     * @param apps Vector of the applicatoins.
     */
    public static void printTree(Vector<DHTClient> apps) {
        // build a hashtable of the apps, keyed by nodehandle
        Hashtable<NodeHandle, DHTClient> appTable = new Hashtable<NodeHandle, DHTClient>();
        Iterator<DHTClient> i = apps.iterator();
        while (i.hasNext()) {
            DHTClient app = (DHTClient) i.next();
            appTable.put(app.endpoint.getLocalNodeHandle(), app);
        }
        NodeHandle seed = ((DHTClient) apps.get(0)).endpoint
                .getLocalNodeHandle();

        // get the root
        NodeHandle root = getRoot(seed, appTable);

        // print the tree from the root down
        recursivelyPrintChildren(root, 0, appTable);
    }

    /**
     * Recursively crawl up the tree to find the root.
     */
    public static NodeHandle getRoot(NodeHandle seed, Hashtable<NodeHandle, DHTClient> appTable) {
        DHTClient app = (DHTClient) appTable.get(seed);
        if (app.isRoot())
            return seed;
        NodeHandle nextSeed = app.getParent();
        return getRoot(nextSeed, appTable);
    }

    /**
     * Print's self, then children.
     */
    public static void recursivelyPrintChildren(NodeHandle curNode,
                                                int recursionDepth, Hashtable<NodeHandle, DHTClient> appTable) {
        // print self at appropriate tab level
        String s = "";
        for (int numTabs = 0; numTabs < recursionDepth; numTabs++) {
            s += "  ";
        }
        s += curNode.getId().toString();
        System.out.println(s);

        // recursively print all children
        DHTClient app = (DHTClient) appTable.get(curNode);
        NodeHandle[] children = app.getChildren();
        for (int curChild = 0; curChild < children.length; curChild++) {
            recursivelyPrintChildren(children[curChild], recursionDepth + 1, appTable);
        }
    }
}