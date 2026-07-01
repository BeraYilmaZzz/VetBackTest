package com.bera.yilmaz.VetManagementSystem.Business.Abs;

import com.bera.yilmaz.VetManagementSystem.Dto.Response.AppointmentResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Animal;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Appointment;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Doctor;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
    Appointment save(Appointment appointment);
    Appointment update(long id, Appointment appointment);
    Appointment delete(long id);
    List<Appointment> getAll();
    Doctor getDoctorById(long id);

    List<Appointment> getAppointmentsByDateAndDoctor(LocalDateTime appointmentDate, LocalDateTime endOfDay, String doctorName);

    List<AppointmentResponse> getAppointmentsByDateAndAnimalId(LocalDateTime dateTime, LocalDateTime endOfDay, Long animalId);
    List<Appointment> getAppointmentsByDateAndAnimal(LocalDateTime appointmentDate, LocalDateTime endOfDay, String animalName);


    Doctor getDoctorByDoctorId(Long id);

    Animal getAnimalByAnimalId(Long id);

    List<Appointment> getDateByDoctorId(long doctorId);

    List<Appointment> getAppointmentByReportId(long appointmentId);

    List<Appointment> getAppointmentsByDoctorIdAndDate(Long doctorId, LocalDateTime localDate);
}
