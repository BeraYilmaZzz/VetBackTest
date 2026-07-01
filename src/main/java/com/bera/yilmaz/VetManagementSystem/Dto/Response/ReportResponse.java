package com.bera.yilmaz.VetManagementSystem.Dto.Response;

import com.bera.yilmaz.VetManagementSystem.Entitiy.Appointment;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    private long id;
    private String title;
    private String diagnosis;
    private double price;
    private AppointmentResponse appointmentResponse;

}
