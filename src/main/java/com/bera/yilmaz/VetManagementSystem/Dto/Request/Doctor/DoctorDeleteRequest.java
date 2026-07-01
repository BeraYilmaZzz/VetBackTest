package com.bera.yilmaz.VetManagementSystem.Dto.Request.Doctor;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDeleteRequest {
    @NotNull
    private long id;
}
