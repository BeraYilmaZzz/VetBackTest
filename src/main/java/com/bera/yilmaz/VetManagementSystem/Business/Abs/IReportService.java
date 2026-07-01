package com.bera.yilmaz.VetManagementSystem.Business.Abs;

import com.bera.yilmaz.VetManagementSystem.Dto.Request.Report.ReportSaveRequest;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.ReportResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Appointment;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Report;
import jakarta.validation.Valid;

import java.util.List;

public interface IReportService {
    Report save(Report report);

    Report get(String title);

    Report update(long id, @Valid ReportSaveRequest update);

    Report deleteById(long id);

    List<Report> findAll();

    List<ReportResponse> getAllByAppointmentId(long appointmentId);

    Appointment getAppointmentByAppointmentId(Long id);

    Report getById(long id);
}
