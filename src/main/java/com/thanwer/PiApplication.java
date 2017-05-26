package com.thanwer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thanwer.PeerDiscover.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.ResourceAccessException;
import rice.environment.Environment;
import rice.pastry.NodeHandle;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.Timer;


@SpringBootApplication
public class PiApplication {
    public static String name = "default";
    //public static String bootIP="10.88.0.229";
    public static String bootIP="174.138.48.96";

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PiApplication.class, args);


        Environment env = new Environment();
        int bindport = 8081;

        InetAddress bootaddr = InetAddress.getByName(bootIP);
        int bootport = 8081;
        InetSocketAddress bootaddress = new InetSocketAddress(bootaddr, bootport);

        System.out.println("\nName: ");
        Scanner scan = new Scanner(System.in);
        name = scan.next();

        new Thread(new DHTPDApp(bindport, bootaddress, env)).start();
        new Thread(new PeerSeeder(name)).start();
        new Thread(new LocalPeerDiscovery()).start();

        Timer timer_lpd = new Timer();
        timer_lpd.schedule(new LocalPeerDiscoveryClient(), 2000, 60000);

        Timer timer_pex = new Timer();
        timer_pex.schedule(new PeerExchange(), 6000, 300000);

        String s = "";
        String id;

        while (!s.equals("OK")) {

            System.out.println("Peer List: ");
            PeerUtil.getPeer();
            System.out.println();
            System.out.println();

            System.out.println("\nName: ");
            Scanner scan1 = new Scanner(System.in);
            id = scan1.next();


            scan1 = new Scanner(System.in);
            System.out.println("Text: ");
            s = scan1.next();

            try {
                MessageUtil.sendMessage(id, s);
            } catch (ResourceAccessException e) {
                System.out.println("Host not found.");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
        System.exit(0);
    }
}
