package com.RentCars.RentCars.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class InsuranceClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_insurance;

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    private String description;

    @Column(nullable = false)
    private String amount;

    @OneToOne
    @JoinColumn(name = "rental_id")
    @JsonIgnore
    private Rental rental;



}
