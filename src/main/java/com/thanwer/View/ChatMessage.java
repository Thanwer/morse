package com.thanwer.View;

/**
 * Created by Thanwer on 11/09/2017.
 */
public class ChatMessage {
    private MessageType type;
    private String content;
    private String destination;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public ChatMessage() {}

    public ChatMessage(String content, String destination) {
        this.setType(MessageType.CHAT);
        this.content = content;
        this.destination = destination;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}