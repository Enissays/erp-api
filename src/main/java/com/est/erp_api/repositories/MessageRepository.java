package com.est.erp_api.repositories;

import com.est.erp_api.models.Message;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface MessageRepository extends R2dbcRepository<Message, Long> {

    @Query("SELECT * FROM message WHERE receiver_id = :receiverId")
    Flux<Message> findAllByReceiverId(String receiverId);

    @Query("SELECT * FROM message WHERE sender_id = :senderId")
    Flux<Message> findAllBySenderId(String senderId);

    @Query("SELECT * FROM message WHERE (receiver_id = :receiverId AND sender_id = :senderId) OR (receiver_id = :senderId AND sender_id = :receiverId)")
    Flux<Message> findAllByReceiverIdAndSenderId(String receiverId, String senderId);
}
