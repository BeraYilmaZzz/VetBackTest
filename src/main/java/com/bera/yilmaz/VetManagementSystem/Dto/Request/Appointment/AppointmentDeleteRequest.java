package com.bera.yilmaz.VetManagementSystem.Dto.Request.Appointment;

import com.bera.yilmaz.VetManagementSystem.Entitiy.Animal;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDeleteRequest {
    private Long id;
    private LocalDate appointmentDate;
    private Animal animal;
    private Doctor doctor;
}
