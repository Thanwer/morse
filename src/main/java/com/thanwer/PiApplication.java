package com.thanwer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.ResourceAccessException;

import java.util.Scanner;


@SpringBootApplication
public class PiApplication {

	public static void main(String[] args) throws ResourceAccessException {
		SpringApplication.run(PiApplication.class, args);

        (new Thread(new LocalPeerDiscovery())).start();
        (new Thread(new LocalPeerDiscoveryClient())).start();


		String s = "";
		String ip;
		while (!s.equals("OK")) {


			System.out.println("\nIP: ");
			Scanner scan = new Scanner(System.in);
			ip = scan.next();
			System.out.println("Text: ");
			s = scan.next();

            try {
                MessageSender.sendMessage(ip,s);
            } catch (ResourceAccessException e) {
                System.out.println("Host not found.");
            } catch (JsonProcessingException e) {
                e.printStackTrace();

            }

        }
		System.exit (0);
	}


}
