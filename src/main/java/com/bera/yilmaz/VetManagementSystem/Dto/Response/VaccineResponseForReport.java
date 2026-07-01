package com.bera.yilmaz.VetManagementSystem.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineResponseForReport {
    private String name;
    private String code;
    private LocalDate protectionStratDate;
    private LocalDate protectionFinishDate;
}