package aca98b.web4l.model.response;


import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AuthResponse extends Response {
    private String jwt;

    @Builder(builderMethodName = "authResponseBuilder")
    public AuthResponse(LocalDateTime timeStamp, int statusCode, HttpStatus status,
                        String message, String devMessage, String jwt, String username) {
        super(timeStamp, statusCode, status, message, devMessage, username);
        this.jwt = jwt;
    }

    public static AuthResponseBuilder builder() {
        return authResponseBuilder();
    }
}
