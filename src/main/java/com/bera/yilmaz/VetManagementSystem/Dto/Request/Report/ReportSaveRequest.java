package com.bera.yilmaz.VetManagementSystem.Dto.Request.Report;

import com.bera.yilmaz.VetManagementSystem.Entitiy.Appointment;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Vaccine;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportSaveRequest {
    @NotNull
    private String title;

    @NotNull
    private String diagnosis;
    @NotNull
    @PositiveOrZero
    private double price;

    @NotNull
    private Appointment appointment;

    @NotNull
    private Vaccine vaccine;
}
