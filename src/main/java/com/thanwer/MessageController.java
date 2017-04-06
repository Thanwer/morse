package com.thanwer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Thanwer on 05/04/2017.
 */

@RestController
@RequestMapping(value = "/messages", method = RequestMethod.GET)
public class MessageController {
    private MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<Message> getAll(){
        return messageRepository.findAll();
    }

    @RequestMapping(value = "/receive",method = RequestMethod.POST)
    public ResponseEntity<Message> add(@RequestBody Message message){
        messageRepository.save(message);
        System.out.println(message.getText());

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}