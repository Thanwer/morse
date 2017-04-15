package com.thanwer;

/**
 * Created by Thanwer on 15/04/2017.
 */
import java.util.List;

public interface ChatRepository {

    List<String> getMessages(int messageIndex);

    void addMessage(String message);

}
