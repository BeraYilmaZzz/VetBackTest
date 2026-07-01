package com.bera.yilmaz.VetManagementSystem.Dto.Response;

import com.bera.yilmaz.VetManagementSystem.Entitiy.Animal;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineResponse {
    private long id;
    private String name;
    private String code;
    private LocalDate protectionStratDate;
    private LocalDate protectionFinishDate;
    private Animal animal;
    private ReportResponse reportResponse;
}
