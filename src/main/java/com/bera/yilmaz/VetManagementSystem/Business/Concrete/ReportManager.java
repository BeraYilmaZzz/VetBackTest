package com.bera.yilmaz.VetManagementSystem.Business.Concrete;

import com.bera.yilmaz.VetManagementSystem.Business.Abs.IReportService;
import com.bera.yilmaz.VetManagementSystem.Core.Exception.NotFoundException;
import com.bera.yilmaz.VetManagementSystem.Core.Utilies.Msg;
import com.bera.yilmaz.VetManagementSystem.Dao.AppointmentRepo;
import com.bera.yilmaz.VetManagementSystem.Dao.ReportRepo;
import com.bera.yilmaz.VetManagementSystem.Dto.Request.Report.ReportSaveRequest;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.ReportResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Appointment;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Report;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ReportManager implements IReportService {

    private final ReportRepo reportRepo;
    private final AppointmentRepo appointmentRepo;

    public ReportManager(ReportRepo reportRepo, AppointmentRepo appointmentRepo) {
        this.reportRepo = reportRepo;
        this.appointmentRepo = appointmentRepo;
    }
    @Override
    public Report save(Report report){
        return this.reportRepo.save(report);
    }

    @Override
    public Report get(String title) {
        return this.reportRepo.findByTitle(title);
    }
    @Override
    public Report update(long id, @Valid ReportSaveRequest update) {
        Optional<Report> optionalReport = this.reportRepo.findById(id);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            report.setTitle(update.getTitle());
            report.setDiagnosis(update.getDiagnosis());
            report.setPrice(update.getPrice());
            return reportRepo.save(report);
        } else {
            throw new NotFoundException(Msg.NOT_FOUND);
        }
    }
    @Override
    public Report deleteById(long id) {
        Optional<Report> optionalReport = this.reportRepo.findById(id);
        if (optionalReport.isPresent()) {
            Report deleteReport = optionalReport.get();
            reportRepo.delete(deleteReport);
            return deleteReport;
        } else {
            throw new NotFoundException(id + " id'li rapor sistemde kayıtlı değildir!!!");
        }
    }

    @Override
    public List<Report> findAll() {
        return this.reportRepo.findAll();
    }

    @Override
    public List<ReportResponse> getAllByAppointmentId(long appointmentId) {
        return reportRepo.findByAppointmentId(appointmentId).stream()
                .map(report -> {
                    ReportResponse reportResponse = new ReportResponse();
                    reportResponse.setTitle(report.getTitle());
                    reportResponse.setDiagnosis(report.getDiagnosis());
                    reportResponse.setPrice(report.getPrice());
                    reportResponse.setAppointmentResponse( reportResponse.getAppointmentResponse());
                    return reportResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Appointment getAppointmentByAppointmentId(Long id) {
        Optional<Appointment> appointmentOptional = appointmentRepo.findById(id);

        return appointmentOptional.orElse(null);
    }

    @Override
    public Report getById(long id) {
        return this.reportRepo.getById(id);
    }
}
