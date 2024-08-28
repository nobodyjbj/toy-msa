package org.toy.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.toy.userservice.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {
    private String email;
    private String name;
    private String userId;
    private List<ResponseOrder> orders;

    public static ResponseUser of(UserEntity userEntity) {
        return ResponseUser.builder()
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .userId(userEntity.getUserId())
                .build();
    }

    public static Iterable<ResponseUser> of(Iterable<UserEntity> users) {
        List<ResponseUser> responseUsers = new ArrayList<>();
        users.forEach(u -> responseUsers.add(ResponseUser.of(u)));
        return responseUsers;
    }

    public static ResponseUser of(UserEntity userEntity, List<ResponseOrder> orders) {
        return ResponseUser.builder()
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .userId(userEntity.getUserId())
                .orders(orders)
                .build();
    }
}
