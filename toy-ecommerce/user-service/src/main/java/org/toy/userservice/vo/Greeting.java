package org.toy.userservice.vo;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Greeting {
    @Value("${greeting.message}")
    private String message;
}
