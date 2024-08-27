package org.toy.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.toy.userservice.entity.UserEntity;
import org.toy.userservice.repository.UserRepository;
import org.toy.userservice.vo.RequestUser;
import org.toy.userservice.vo.ResponseUser;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseUser createUser(RequestUser requestUser) {
        String encryptedPassword = passwordEncoder.encode(requestUser.getPassword());
        UserEntity userEntity = UserEntity.of(requestUser, encryptedPassword);
        return ResponseUser.of(userRepository.save(userEntity));
    }
}
