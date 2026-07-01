package com.bera.yilmaz.VetManagementSystem.Dao;

import com.bera.yilmaz.VetManagementSystem.Entitiy.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepo  extends JpaRepository<Report, Long> {
    Report findByTitle(String title);

    List<Report> findByAppointmentId(long appointmentId);

    List<Report> findByVaccinesId(long vaccinetId);
}
