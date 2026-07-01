package com.bera.yilmaz.VetManagementSystem.Dto.Request.Doctor;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSaveRequest {
    @NotNull(message = "Doktor Adı Boş Olamaz")
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;



}
