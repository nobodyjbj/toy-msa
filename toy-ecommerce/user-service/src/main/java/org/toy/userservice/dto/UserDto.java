package org.toy.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDto {
    private String email;
    private String name;
    private String password;
    private String userId;
    private Date createAt;

    private String encryptedPassword;
}
