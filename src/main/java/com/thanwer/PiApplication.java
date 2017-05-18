package com.thanwer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.ResourceAccessException;
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

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.Timer;


@SpringBootApplication
public class PiApplication {
    public static String name = "default";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PiApplication.class, args);

        Environment env = new Environment();

        // disable the UPnP setting (in case you are testing this on a NATted LAN)
        env.getParameters().setString("nat_search_policy","never");

        try {
            // the port to use locally
            int bindport = Integer.parseInt("9001");

            // build the bootaddress from the command line args
            InetAddress bootaddr = InetAddress.getByName("10.88.0.18");
            int bootport = Integer.parseInt("9001");
            InetSocketAddress bootaddress = new InetSocketAddress(bootaddr,bootport);

            // launch our node!
            DHTNode dt = new DHTNode(bindport, bootaddress, env);
        } catch (Exception e) {
            // remind user how to use
            System.out.println("Usage:");
            System.out.println("java [-cp FreePastry-<version>.jar] rice.tutorial.lesson3.DHTNode localbindport bootIP bootPort");
            System.out.println("example java rice.tutorial.DHTNode 9001 pokey.cs.almamater.edu 9001");
            throw e;
        }

		/*

        System.out.println("\nName: ");
        Scanner scan = new Scanner(System.in);
        name = scan.next();

		new Thread(new PeerSeeder(name)).start();
        new Thread(new LocalPeerDiscovery()).start();

        Timer timer = new Timer();
        timer.schedule(new LocalPeerDiscoveryClient(), 0, 6000);

		String s = "";
		String id;

		while (!s.equals("OK")) {

            System.out.println("Peer List: ");
            PeerUtil.getPeer();
            System.out.println();
            System.out.println();

            System.out.println("\nName or (R)eload: ");
            Scanner scan1 = new Scanner(System.in);
            id = scan1.next();


            scan1 = new Scanner(System.in);
			System.out.println("Text: ");
			s = scan1.next();

            try {
                MessageUtil.sendMessage(id,s);
            } catch (ResourceAccessException e) {
                System.out.println("Host not found.");
            } catch (JsonProcessingException e) {
                e.printStackTrace();

            }

        }
		System.exit (0);
		*/
	}


}
