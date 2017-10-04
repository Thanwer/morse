package com.thanwer.View;

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

    @RequestMapping("/chat")
    public String chat() {
        return "chat";
    }
}
