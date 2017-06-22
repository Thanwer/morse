package com.thanwer.Peer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Thanwer on 02/04/2017.
 */
@Repository
public interface PeerRepository extends JpaRepository<Peer, Long> {
    List<Peer> findByName(String name);
    //Peer getOneByName(String name);
    boolean existsByName(String name);
}
