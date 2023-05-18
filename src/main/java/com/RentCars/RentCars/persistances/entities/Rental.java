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
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_rental;

    @Column(nullable = false)
    private String status;

    @OneToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @OneToOne(mappedBy = "rental")
    private Rating rating;
}
