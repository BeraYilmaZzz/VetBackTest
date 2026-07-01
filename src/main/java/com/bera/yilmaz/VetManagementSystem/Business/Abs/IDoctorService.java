package com.bera.yilmaz.VetManagementSystem.Business.Abs;

import com.bera.yilmaz.VetManagementSystem.Dto.Request.Doctor.DoctorSaveRequest;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Doctor;

import java.time.LocalDateTime;
import java.util.List;


public interface IDoctorService {
    Doctor save(Doctor doctor);
    Doctor get(long id);
    Doctor get(String name);
    Doctor update(long id, DoctorSaveRequest request);

    Doctor deleteById(long id);
    List<Doctor> findAll();

    boolean existByMail(String doctorMail);

    boolean isDoctorAvailable(Long doctorId, LocalDateTime appointmentDate);
}

