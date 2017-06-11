package com.thanwer.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Thanwer on 10/06/2017.
 */
@Repository
public interface MessageQueueRepository extends JpaRepository<MessageQueue, Long> {
    MessageQueue findByName(String name);
    boolean existsByName(String name);
}