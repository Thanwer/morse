package com.thanwer.Message;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Thanwer on 05/04/2017.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByOrderByCreateDateAsc();
}
