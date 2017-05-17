package com.thanwer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.ResourceAccessException;

import java.util.Scanner;
import java.util.Timer;


@SpringBootApplication
public class PiApplication {
    public static String name = "default";

	public static void main(String[] args) throws ResourceAccessException {
		SpringApplication.run(PiApplication.class, args);

        //(new Thread(new LocalPeerDiscoveryClient())).start();
        System.out.println("\nName: ");
        Scanner scan = new Scanner(System.in);
        name = scan.next();

		new Thread(new PeerSeeder(name)).start();
        new Thread(new LocalPeerDiscovery(name)).start();

        Timer timer = new Timer();
        timer.schedule(new LocalPeerDiscoveryClient(), 0, 60000);

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
	}


}
