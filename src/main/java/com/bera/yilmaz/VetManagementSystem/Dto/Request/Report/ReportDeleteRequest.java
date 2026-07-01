package com.bera.yilmaz.VetManagementSystem.Dto.Request.Report;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReportDeleteRequest {
    @NotNull
    private Long id;
}
