package com.bera.yilmaz.VetManagementSystem.Dao;

import com.bera.yilmaz.VetManagementSystem.Entitiy.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentDate BETWEEN :startDate AND :endDate")
    List<Appointment> findByDoctorIdAndDateTimeBetween(
            @Param("doctorId") long doctorId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT a FROM Appointment a WHERE a.animal.id = :animalId AND a.appointmentDate BETWEEN :startDate AND :endDate")
    List<Appointment> findByAnimalIdAndDateTimeBetween(
            @Param("animalId") long animalId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentDate = :dateTime")
    List<Appointment> findConflictingAppointmentsForDoctor(
            @Param("doctorId") long doctorId,
            @Param("dateTime") LocalDateTime dateTime
    );

    List<Appointment> findByDoctorId(long doctorId);

    List<Appointment> findByReportId(Long reportId);

    List<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDateTime localDate);

    List<Appointment> findByAnimalNameAndAppointmentDateBetween(String animalName, LocalDateTime startDate, LocalDateTime endDate);
}
