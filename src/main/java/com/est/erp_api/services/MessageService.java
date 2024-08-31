package com.est.erp_api.services;

import com.est.erp_api.models.Message;
import com.est.erp_api.models.MessageUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageService {
    Mono<Message> sendMessage(String content, String receiverId, String token);
    Flux<MessageUser> getMessagesForUser(String userId);
    Flux<MessageUser> getSentMessagesForUser(String userId);
    Flux<MessageUser> getAllFromTo(String from, String to);
}