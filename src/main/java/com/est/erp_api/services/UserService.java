package com.est.erp_api.services;

import com.est.erp_api.exceptions.EtAuthException;
import com.est.erp_api.models.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> validateUser(String email, String password) throws EtAuthException;
    Mono<User> registerUser(String name, String email, String password) throws EtAuthException;

}
