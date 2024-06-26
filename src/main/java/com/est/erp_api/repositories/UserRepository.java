package com.est.erp_api.repositories;

import com.est.erp_api.exceptions.EtAuthException;
import com.est.erp_api.models.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface UserRepository extends R2dbcRepository<User, Integer>{
    @Query("INSERT INTO users (user_id, username, email, password) VALUES (nextval('users_user_id_seq'), :name, :email, :password) RETURNING user_id")
    Mono<String> create(String name, String email, String password) throws EtAuthException;

    @Query("UPDATE users SET username = :name WHERE user_id = :userId")
    Mono<Void> updateUsername(String userId, String name) throws EtAuthException;

    @Query("UPDATE users SET email = :email WHERE user_id = :userId")
    Mono<Void> updateEmail(String userId, String email) throws EtAuthException;

    @Query("UPDATE users SET password = :password WHERE user_id = :userId")
    Mono<Void> updatePassword(String userId, String password) throws EtAuthException;

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    Mono<User> findByEmailAndPassword(String email, String password) throws EtAuthException;

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    Mono<Integer> getCountByEmail(String email);

    @Query("SELECT * FROM users WHERE user_id = :userId")
    Mono<User> findUserById(String userId);

    @Query("SELECT user_id, username, email, password FROM users WHERE email = :email")
    Mono<User> findByEmail(String email);

    @Query("SELECT * FROM users")
    Flux<User> findAllUsers();


}
