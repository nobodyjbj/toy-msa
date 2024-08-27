package org.toy.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.toy.userservice.vo.RequestUser;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, unique = true)
    private String encryptedPassword;

    public static UserEntity of(RequestUser requestUser, String encryptedPassword) {
        return UserEntity.builder()
                .email(requestUser.getEmail())
                .name(requestUser.getName())
                .userId(UUID.randomUUID().toString())
                .encryptedPassword(encryptedPassword)
                .build();
    }
}
