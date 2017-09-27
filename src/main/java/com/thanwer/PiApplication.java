package com.thanwer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thanwer.Message.MessageUtil;
import com.thanwer.Peer.PeerSeeder;
import com.thanwer.Peer.PeerUtil;
import com.thanwer.PeerDiscover.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.ResourceAccessException;
import rice.environment.Environment;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.Timer;


@SpringBootApplication
public class PiApplication {
    public static String name = "default";
    //static String bootIP="10.88.0.229";
    public static String bootIP="174.138.48.96";

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PiApplication.class, args);
        Environment env = new Environment();

        /*System.out.println("\nName: ");
        Scanner scan = new Scanner(System.in);
        name = scan.next();*/

        new Thread(new DHTPDApp(env)).start();
        new Thread(new PeerSeeder(name)).start();
        new Thread(new LocalPeerDiscovery()).start();


        Timer timer_lpd = new Timer();
        timer_lpd.schedule(new LocalPeerDiscoveryClient(), 2000, 60000);

        Timer timer_pex = new Timer();
        timer_pex.schedule(new PeerExchange(), 6000, 300000);
        /*
        String s = "";
        String id;

        while (!s.equals("OK")) {
            Scanner scan1;
            scan1 = new Scanner(System.in);

            do {
                System.out.println("Peer List: [R]efresh ");
                PeerUtil.getPeer();
                System.out.println();
                System.out.println();
                System.out.println("\nName: ");
                id = scan1.next();
            } while (id.equals("r"));

            System.out.println("\n[E] to escape: ");

            do {
                System.out.print("["+id+"] : ");
                s = scan1.next();
                MessageUtil.sendMessage(id, s);
            } while (!s.equals("E"));

        }
        System.exit(0);*/
    }
}
