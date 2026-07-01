package com.bera.yilmaz.VetManagementSystem.Dto.Request.Vaccine;

import com.bera.yilmaz.VetManagementSystem.Entitiy.Animal;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineSaveRequest {
    private String name;
    private String code;
    private LocalDate protectionStratDate;
    private LocalDate protectionFinishDate;
    private Animal animal;
    @NonNull
    private Report report;
}
