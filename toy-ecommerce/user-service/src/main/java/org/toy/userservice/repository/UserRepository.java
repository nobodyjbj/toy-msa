package org.toy.userservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.toy.userservice.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
