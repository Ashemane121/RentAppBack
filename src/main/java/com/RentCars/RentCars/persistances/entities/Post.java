package com.RentCars.RentCars.persistances.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_post;

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private Integer mileage;

    @Column
    private Integer year;

    @Column
    private String description;

    @Column
    private String gearbox;

    @Column
    private String fuel;

    @Column
    private Double price;

    @Column
    @ElementCollection
    private List<String> images;

    @Column
    private Boolean availability;

    @Column
    @Temporal(TemporalType.DATE)
    private Date start_date;

    @Column
    @Temporal(TemporalType.DATE)
    private Date end_date;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<Request> requests;


}
