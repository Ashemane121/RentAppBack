package com.RentCars.RentCars.persistances.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_request;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date start_date;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date end_date;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String payment_method;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    @OneToOne(mappedBy = "request")
    @JsonIgnore
    private Rental rental;

}
