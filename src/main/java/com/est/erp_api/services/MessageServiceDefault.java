package com.est.erp_api.services;

import com.est.erp_api.models.Message;
import com.est.erp_api.models.MessageUser;
import com.est.erp_api.repositories.MessageRepository;
import com.est.erp_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MessageServiceDefault implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<Message> sendMessage(String content, String receiverId, String sendId) {
            Message message = new Message();
            message.setContent(content);
            message.setSenderId(sendId);
            message.setReceiverId(receiverId);
            return messageRepository.save(message);
    }

    @Override
    public Flux<MessageUser> getMessagesForUser(String userId) {
        return messageRepository.findAllByReceiverId(userId)
                .flatMap(message -> userRepository.findUserById(message.getSenderId())
                        .map(user -> new MessageUser(message, user.getUsername(), user.getEmail(), user.getUser_id())));
    }

    @Override
    public Flux<MessageUser> getSentMessagesForUser(String userId) {
        return messageRepository.findAllBySenderId(userId)
                .flatMap(message -> userRepository.findUserById(message.getReceiverId())
                        .map(user -> new MessageUser(message, user.getUsername(), user.getEmail(), user.getUser_id())));
    }

    @Override
    public Flux<MessageUser> getAllFromTo(String from, String to) {
        return messageRepository.findAllByReceiverIdAndSenderId(to, from)
                .flatMap(message -> userRepository.findUserById(message.getReceiverId())
                        .map(user -> new MessageUser(message, user.getUsername(), user.getEmail(), user.getUser_id())));
    }

    private Mono<String> extractUserIdFromToken(String token) {
        try {
            return Mono.just(jwtService.parse(token));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
