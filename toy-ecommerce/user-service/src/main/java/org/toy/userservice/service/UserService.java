package org.toy.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.toy.userservice.entity.UserEntity;
import org.toy.userservice.exception.ResourceNotFoundException;
import org.toy.userservice.repository.UserRepository;
import org.toy.userservice.vo.RequestUser;
import org.toy.userservice.vo.ResponseOrder;
import org.toy.userservice.vo.ResponseUser;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        List<ResponseOrder> orders = new ArrayList<>();
        return ResponseUser.of(userEntity, orders);
    }

    public ResponseUser getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return ResponseUser.of(userEntity);
    }
}
