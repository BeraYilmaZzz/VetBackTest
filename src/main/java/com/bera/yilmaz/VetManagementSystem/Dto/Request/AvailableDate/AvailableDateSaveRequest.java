package com.bera.yilmaz.VetManagementSystem.Dto.Request.AvailableDate;

import com.bera.yilmaz.VetManagementSystem.Entitiy.Doctor;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateSaveRequest {
    @NotNull
    private long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate available_date;
    @NotNull
    private Doctor doctor;
}
