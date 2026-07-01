package com.bera.yilmaz.VetManagementSystem.Entitiy;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name ="available_date")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvailableDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="available_id", columnDefinition = "serial")
    private long id;

    @Column(name="available_dates")
    @NotNull
    private LocalDate available_date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}
