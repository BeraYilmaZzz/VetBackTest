package com.bera.yilmaz.VetManagementSystem.Dto.Request.Vaccine;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineDeleteRequest {
    @NotNull
    private long id;
}
