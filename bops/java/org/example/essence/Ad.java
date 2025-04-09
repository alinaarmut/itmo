package org.example.essence;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;



@Data
@Entity
@Table(name = "advertaisment")
@NoArgsConstructor
@AllArgsConstructor
public class Ad {

    @Id
    private Long id;
    @Column(name = "заголовок")
    private String title;
    @Column(name = "описание")
    private String description;
    @Column(name = "цена_за_ночь")
    private Double pricePerNight;

}
