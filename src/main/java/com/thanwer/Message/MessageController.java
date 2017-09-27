package com.thanwer.Message;

import com.thanwer.PiApplication;
import com.thanwer.View.ChatMessage;
import com.thanwer.View.PeerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Created by Thanwer on 05/04/2017.
 */

@RestController
public class MessageController {

    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    public MessageController(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }


    @RequestMapping(method=RequestMethod.GET, value = "/messages/all")
    public List<Message> getAll(){
        return messageRepository.findAll();
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/channel/local")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        if (Objects.equals(chatMessage.getDestination(),PiApplication.name)){
            messageRepository.save(new Message(chatMessage.getDestination(),chatMessage.getContent()));
        return chatMessage;
        } else {
            MessageUtil.sendMessage(chatMessage.getDestination(),chatMessage.getContent());
            return chatMessage;
        }
    }

    @RequestMapping(method=RequestMethod.POST, value = "/messages")
    //@MessageMapping("/chat.sendMessage")
    //@SendTo("/channel/local")
    public Message save(@RequestBody Message message) {
        if (Objects.equals(message.getPeer(), PiApplication.name)){
            messageRepository.save(message);
            template.convertAndSend("/channel/local", new ChatMessage (message.getText(),message.getAuthor()));
            return message;
        } else {
            MessageUtil.sendMessage(message.getPeer(),message.getText());
            return message;
        }
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/channel/local")
    public PeerMessage addUser(@Payload PeerMessage peerMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", peerMessage.getName());
        PiApplication.name=peerMessage.getName();
        return peerMessage;
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