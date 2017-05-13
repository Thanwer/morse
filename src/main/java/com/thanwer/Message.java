package com.thanwer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.util.Date;

/**
 * Created by Thanwer on 05/04/2017.
 */

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String author;
    private Date createDate;
    private String text;

    public Message() {}

    public Message(String text) {
        this.text = text;
        this.createDate = Date.from(Instant.now());
    }

    public Message(String author, String text) {
        this.author = author;
        this.createDate = Date.from(Instant.now());
        this.text = text;
    }

    public Message(String author, Date createDate, String text) {
        this.author = author;
        this.createDate = createDate;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "User= '" + author + '\'' +
                ", Text='" + text + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }
}
