package com.thanwer.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Thanwer on 10/06/2017.
 */
@Repository
public interface MessageQueueRepository extends JpaRepository<MessageQueue, Long> {
    List <MessageQueue> findByName(String name);
    boolean existsByName(String name);
}