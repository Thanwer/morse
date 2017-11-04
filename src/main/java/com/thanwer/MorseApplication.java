package com.thanwer;

import com.thanwer.Message.MessageUtil;
import com.thanwer.Peer.PeerSeeder;
import com.thanwer.Peer.PeerUtil;
import com.thanwer.PeerDiscover.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rice.environment.Environment;

import java.util.Scanner;
import java.util.Timer;


@SpringBootApplication
public class MorseApplication {
    public static String name = "default";
    //public static String bootIP="10.88.0.18";
    public static String bootIP="165.227.184.133";

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MorseApplication.class, args);
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

        /*String s = "";
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
