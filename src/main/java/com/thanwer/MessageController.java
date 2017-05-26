package com.thanwer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by Thanwer on 05/04/2017.
 */

@RestController
@RequestMapping(value = "/messages")
public class MessageController {

    private MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    @RequestMapping(method=RequestMethod.GET)
    public List<Message> getAll(){
        return messageRepository.findAll();
    }


    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Message> add(@RequestBody Message message, HttpServletRequest request){
        messageRepository.save(message);
        InetAddress ip =null;
        try {
            ip = InetAddress.getByName(request.getRemoteAddr());
        } catch (UnknownHostException e) {

        }
        PeerUtil.sendPeer(new Peer(message.getAuthor(),ip));

        System.out.println(message.toString());

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}