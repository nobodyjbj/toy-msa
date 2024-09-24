package org.toy.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.toy.userservice.entity.UserEntity;
import org.toy.userservice.exception.ResourceNotFoundException;
import org.toy.userservice.feign.client.OrderServiceClient;
import org.toy.userservice.repository.UserRepository;
import org.toy.userservice.vo.RequestUser;
import org.toy.userservice.vo.ResponseOrder;
import org.toy.userservice.vo.ResponseUser;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderServiceClient orderServiceClient;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + ": not found"));

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                true, true, true, true,
                new ArrayList<>());
    }

    @Transactional
    public ResponseUser createUser(RequestUser requestUser) {
        String encryptedPassword = passwordEncoder.encode(requestUser.getPassword());
        UserEntity userEntity = UserEntity.of(requestUser, encryptedPassword);
        return ResponseUser.of(userRepository.save(userEntity));
    }

    public Iterable<ResponseUser> findUsers() {
        Iterable<UserEntity> users = userRepository.findAll();
        return ResponseUser.of(users);
    }

    public ResponseUser getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

//        List<ResponseOrder> orders = null;
//        try {
//            orders = orderServiceClient.getOrders(userId);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }

//        List<ResponseOrder> orders = orderServiceClient.getOrders(userId);
        log.info("Before call orders microservice");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
        List<ResponseOrder> orders = circuitBreaker.run(() ->
                orderServiceClient.getOrders(userId), throwable -> new ArrayList<>()
        );

        return ResponseUser.of(userEntity, orders);
    }

    public ResponseUser getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return ResponseUser.of(userEntity);
    }
}
