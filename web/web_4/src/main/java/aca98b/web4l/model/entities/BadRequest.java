package aca98b.web4l.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Table(name="aca98b_badrequests", schema="public")
@Table(name="aca98b_badrequests", schema="s367826")
public class BadRequest {
    @Id
    @GeneratedValue
    @SequenceGenerator(name = "points_id_seq", sequenceName = "points_id_seq", allocationSize = 1)
    private Long id;
    private String time;
    private String status;
    @Column(name = "status_code")
    private Integer statusCode;
    private String message;
    @Column(name = "method_name")
    private String methodName;
    private String username;
}