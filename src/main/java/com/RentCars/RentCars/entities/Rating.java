package com.RentCars.RentCars.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_rating;

    @Column(nullable = false)
    private Integer stars;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String type;

    @OneToOne(mappedBy = "rating")
    @JsonIgnore
    private Rental rental;

}
