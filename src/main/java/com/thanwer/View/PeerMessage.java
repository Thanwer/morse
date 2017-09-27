package com.thanwer.View;

/**
 * Created by Thanwer on 25/09/2017.
 */
public class PeerMessage {
    private MessageType type;
    private String name;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public PeerMessage() {
    }

    public PeerMessage(MessageType type, String name) {
        this.type = type;
        this.name = name;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
