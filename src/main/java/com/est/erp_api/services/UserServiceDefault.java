package com.est.erp_api.services;

import com.est.erp_api.exceptions.EtAuthException;
import com.est.erp_api.models.User;
import com.est.erp_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;


@Service
@Transactional
public class UserServiceDefault implements UserService {

    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Mono<User> validateUser(String email, String password) throws EtAuthException {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new EtAuthException("Invalid email")))
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    @Override
    public Mono<User> registerUser(String name, String email, String password) throws EtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if (email != null) email = email.toLowerCase();
        assert email != null;
        if (!pattern.matcher(email).matches())
            throw new EtAuthException("Invalid email format");
        String finalEmail = email;
        return userRepository.getCountByEmail(email)
                .flatMap(count -> {
                    if (count > 0) throw new EtAuthException("Email already in use");
                    return userRepository.create(name, finalEmail, passwordEncoder.encode(password));
                })
                .flatMap(userId -> userRepository.findUserById(userId));
    }
}
