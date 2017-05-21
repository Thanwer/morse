package com.thanwer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rice.p2p.commonapi.Id;

/**
 * Created by Thanwer on 02/04/2017.
 */
@Repository
public interface PeerRepository extends JpaRepository<Peer, Long> {
    Peer findByName(String name);
    boolean existsByName(String name);
}
