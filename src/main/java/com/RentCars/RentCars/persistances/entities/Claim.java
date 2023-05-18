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
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_claim;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String status;

    @OneToOne
    @JoinColumn(name = "rental_id")
    @JsonIgnore
    private Rental rental;

}