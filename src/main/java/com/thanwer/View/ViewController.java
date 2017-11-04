package com.thanwer.View;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Thanwer on 23/08/2017.
 */
@Controller
public class ViewController {
    /*@RequestMapping("/")
    public String login(){
        return "login";
    }*/

    @RequestMapping("/")
    public String chat() {
        return "chat";
    }
}
