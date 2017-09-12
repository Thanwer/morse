package com.thanwer.Message;

import com.thanwer.Peer.Peer;
import com.thanwer.Peer.PeerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * Created by Thanwer on 05/04/2017.
 */

@RestController
public class MessageController {

    private MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }


    @RequestMapping(method=RequestMethod.GET, value = "/messages")
    public List<Message> getAll(){
        return messageRepository.findAll();
    }


    @RequestMapping(method=RequestMethod.POST, value = "/messages")
    @SendTo("/topic/messages")
    @MessageMapping("/newMessage")
    public Message save(Message message) {
        messageRepository.save(message);
        return new Message(message.toString());
    }

    /*public ResponseEntity<Message> add(@RequestBody Message message, HttpServletRequest request){
        messageRepository.save(message);
        InetAddress ip =null;
        try {
            ip = InetAddress.getByName(request.getRemoteAddr());
        } catch (UnknownHostException e) {

        }
        PeerUtil.sendPeer(new Peer(message.getAuthor(),ip));

        System.out.println(message.toString());

        return new ResponseEntity<>(message, HttpStatus.OK);
    }*/

}