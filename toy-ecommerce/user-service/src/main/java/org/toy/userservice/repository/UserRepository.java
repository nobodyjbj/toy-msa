package org.toy.userservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.toy.userservice.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserId(String userId);
    Optional<UserEntity> findByEmail(String email);
}
