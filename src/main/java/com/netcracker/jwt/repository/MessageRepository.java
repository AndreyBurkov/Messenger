package com.netcracker.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.netcracker.jwt.domain.Message;

import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "select * from messages b where ((b.to_user=?1 AND  b.from_user=?2) OR (b.to_user=?2 AND b.from_user=?1)) AND b.id > ?3 ORDER BY b.id LIMIT 10", nativeQuery = true)
    List<Message> getCorrespondence(Long toUser, Long fromUser, Long lastMessageId);
}
