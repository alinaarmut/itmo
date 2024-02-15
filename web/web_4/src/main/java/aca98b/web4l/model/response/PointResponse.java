package aca98b.web4l.model.response;


import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PointResponse extends Response{
    Map<String, ?> pointsData;

    @Builder(builderMethodName = "pointResponseBuilder")
    public PointResponse(LocalDateTime timeStamp, int statusCode, HttpStatus status,
                         String message, String devMessage, Map<String, ?> pointsData, String username) {
        super(timeStamp, statusCode, status, message, devMessage, username);
        this.pointsData = pointsData;
    }

    public static PointResponseBuilder builder() {
        return pointResponseBuilder();
    }
}