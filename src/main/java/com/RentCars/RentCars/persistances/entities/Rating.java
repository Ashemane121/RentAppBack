package com.RentCars.RentCars.persistances.entities;

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

    @Column
    private String comment;

    @OneToOne
    @JoinColumn(name = "rental_id")
    @JsonIgnore
    private Rental rental;

}
