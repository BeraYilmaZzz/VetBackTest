package com.bera.yilmaz.VetManagementSystem.Dto.Request.Animal;

import com.bera.yilmaz.VetManagementSystem.Entitiy.Customer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSaveRequest { // alacaklarımız
    @NotNull
    private String name;
    @NotNull
    private String species;

    @NotNull
    private String breed;

    @NotNull
    private String gender;

    @NotNull
    private String colour;

    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    private Customer customer;
}
