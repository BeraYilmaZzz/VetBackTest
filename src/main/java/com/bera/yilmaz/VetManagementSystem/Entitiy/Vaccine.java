package com.bera.yilmaz.VetManagementSystem.Entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name ="vaccine")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vaccine_id", columnDefinition = "serial")
    private Long id;

    @Column(name="name",nullable = false)
    @NotNull
    private String name;

    @Column(name="code",nullable = false)
    @NotNull
    private String code;

    @Column(name="protection_start_date",nullable = false)
    @NotNull
    private LocalDate protectionStratDate;

    @Column(name="proteciton_finish_date",nullable = false)
    @NotNull
    private LocalDate protectionFinishDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @ManyToOne
    @JsonIgnore
    private Report report;
}
