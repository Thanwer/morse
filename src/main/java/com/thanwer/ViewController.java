package com.thanwer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Thanwer on 23/08/2017.
 */
@Controller
public class ViewController {

    @RequestMapping("/")
    public String login(){
        return "login";
    }
    /*@MessageMapping("/chat.sendMessage")
    @SendTo("/channel/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }*/
    /*@MessageMapping("/chat.addUser")
    @SendTo("/channel/local")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }*/

    @RequestMapping("/chat")
    public String chat() {
        return "chat";
    }
}
