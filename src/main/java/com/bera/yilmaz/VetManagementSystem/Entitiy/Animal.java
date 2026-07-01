package com.bera.yilmaz.VetManagementSystem.Entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name ="animal")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="animal_id", columnDefinition = "serial")
    private Long id;

    @Column(name="name",nullable = false)
    @NotNull
    private String name;

    @Column(name="species",nullable = false)
    @NotNull
    private String species;

    @Column(name = "breed",nullable = false)
    @NotNull
    private String breed;

    @Column(name = "gender",nullable = false)
    @NotNull
    private String gender;

    @Column(name = "colour",nullable = false)
    @NotNull
    private String colour;

    @Column(name = "date_of_birth")
    @NotNull
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "animal", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Vaccine> vaccine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "animal", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Appointment> appointment;
}
