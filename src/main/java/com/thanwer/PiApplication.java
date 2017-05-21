package com.thanwer;

import com.thanwer.PeerDiscover.DHTPDApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rice.environment.Environment;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;


@SpringBootApplication
public class PiApplication {
    public static String name = "default";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PiApplication.class, args);


        Environment env = new Environment();
        int bindport = 9001;
        env.getParameters().setString("nat_search_policy","always");


        // build the bootaddress from the command line args
        InetAddress addr = null;
        //InetAddress bootaddr = InetAddress.getByName("174.138.48.96");
        InetAddress bootaddr = InetAddress.getByName("ec2-52-14-195-106.us-east-2.compute.amazonaws.com");
        int bootport = 9001;
        InetSocketAddress bootaddress = new InetSocketAddress(bootaddr, bootport);


        // launch our node!
        DHTPDApp dt = new DHTPDApp(bindport, bootaddress, env);

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
