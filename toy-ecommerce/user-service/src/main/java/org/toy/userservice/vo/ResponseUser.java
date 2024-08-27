package org.toy.userservice.vo;

import lombok.Builder;
import lombok.Getter;
import org.toy.userservice.entity.UserEntity;

@Getter
@Builder
public class ResponseUser {
    private String email;
    private String name;
    private String userId;

    public static ResponseUser of(UserEntity userEntity) {
        return ResponseUser.builder()
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .userId(userEntity.getUserId())
                .build();
    }
}
