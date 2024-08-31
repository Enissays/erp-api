package com.est.erp_api.controller;

import com.est.erp_api.configurations.AuthConverter;
import com.est.erp_api.models.Message;
import com.est.erp_api.models.MessageUser;
import com.est.erp_api.services.JWTService;
import com.est.erp_api.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/messages")
public class MessageController {


    @Autowired
    private MessageService messageService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/send")
    public Mono<Message> sendMessage(@RequestParam String content,
                                     @RequestParam String receiverId,
                                     ServerWebExchange exchange) {
        return extractUserIdFromToken(exchange).flatMap(token ->
                messageService.sendMessage(content, receiverId, token)
        );
    }

    @GetMapping("/received")
    public Flux<MessageUser> getMessagesForUser(ServerWebExchange exchange) {
        return extractUserIdFromToken(exchange)
                .flatMapMany(messageService::getMessagesForUser);
    }

    @GetMapping("/sent")
    public Flux<MessageUser> getSentMessagesForUser(ServerWebExchange exchange) {
        return extractUserIdFromToken(exchange)
                .flatMapMany(messageService::getSentMessagesForUser);
    }

    @GetMapping("/all")
    public Flux<MessageUser> getAllFromTo(
            @RequestParam String to,
                                         ServerWebExchange exchange) {
        return extractUserIdFromToken(exchange)
                .flatMapMany(token -> messageService.getAllFromTo(token, to));
    }

    private Mono<String> extractUserIdFromToken(ServerWebExchange exchange) {
        return new AuthConverter().convert(exchange)
                .flatMap(token -> {
                    try {
                        return Mono.just(jwtService.parse(token.getPrincipal().toString()));
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }
}