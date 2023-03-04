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
    private String payment_method;

    @Column(nullable = false)
    private String status;

    @OneToOne
    @JoinColumn(name = "request_id")
    @JsonIgnore
    private Request request;

    @OneToOne(mappedBy = "rental")
    @JsonIgnore
    private InsuranceClaim insuranceClaim;

    @OneToOne
    @JoinColumn(name = "rating_id")
    @JsonIgnore
    private Rating rating;
}
