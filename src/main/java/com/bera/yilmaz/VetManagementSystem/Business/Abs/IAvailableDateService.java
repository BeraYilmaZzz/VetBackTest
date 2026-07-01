package com.bera.yilmaz.VetManagementSystem.Business.Abs;


import com.bera.yilmaz.VetManagementSystem.Entitiy.AvailableDate;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Doctor;

import java.time.LocalDate;
import java.util.List;

public interface IAvailableDateService {
    boolean existsByDoctorIdAndAvailable_date(long doctorId, LocalDate availableDate);

    AvailableDate deletebyDateId(AvailableDate date);

    List<AvailableDate> getByDoctorId(long doctorId);

    List<AvailableDate> findAll();

    AvailableDate get(long id);

    AvailableDate save(AvailableDate saveDate);

    AvailableDate update(long id, AvailableDate availableDate);

    Doctor getDoctorByDoctorId(Long id);
}

