package com.bera.yilmaz.VetManagementSystem.Api;

import com.bera.yilmaz.VetManagementSystem.Business.Abs.IReportService;
import com.bera.yilmaz.VetManagementSystem.Core.Config.ModelMapper.IModelMapperService;
import com.bera.yilmaz.VetManagementSystem.Core.Result.ResultData;
import com.bera.yilmaz.VetManagementSystem.Core.Utilies.ResultHelper;
import com.bera.yilmaz.VetManagementSystem.Dto.Request.Appointment.AppointmentSaveRequest;
import com.bera.yilmaz.VetManagementSystem.Dto.Request.Doctor.DoctorSaveRequest;
import com.bera.yilmaz.VetManagementSystem.Dto.Request.Report.ReportSaveRequest;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.AppointmentResponse;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.DoctorResponse;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.ReportResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/reports")
public class ReportController {

    private final IReportService reportService;
    private final IModelMapperService modelMapper;

    public ReportController(IReportService reportService, IModelMapperService modelMapper) {
        this.reportService = reportService;
        this.modelMapper = modelMapper;
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<ReportResponse> save(@Valid @RequestBody ReportSaveRequest request){

        Appointment appointment=reportService.getAppointmentByAppointmentId(request.getAppointment().getId());
        Report saveReport= this.modelMapper.forRequest().map(request, Report.class);
        this.reportService.save(saveReport);
        saveReport.setAppointment(appointment);
        ReportResponse reportResponse =this.modelMapper.forResponse().map(saveReport,ReportResponse.class);

        return ResultHelper.created(reportResponse);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<ReportResponse>> findAll() {
        List<Report> reports = reportService.findAll();
        List<ReportResponse> reportResponses = reports.stream()
                .map(report -> modelMapper.forResponse().map(report, ReportResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(reportResponses);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<ReportResponse> get(@PathVariable("id") long id){
        Report report = this.reportService.getById(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(report,ReportResponse.class));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<ReportResponse> update(@PathVariable("id") long id, @RequestBody @Valid ReportSaveRequest reportSaveRequest) {
        Report updatedReport = this.reportService.update(id, reportSaveRequest);
        ReportResponse reportResponse = null;
        if (updatedReport != null) {
            reportResponse = this.modelMapper.forResponse().map(updatedReport, ReportResponse.class);
            return ResultHelper.success(reportResponse);
        } else {
            return ResultHelper.notFound(reportResponse);
        }
    }
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<ReportResponse> delete(@PathVariable("id") long id) {
        Report deleteReport = this.reportService.deleteById(id);
        ReportResponse reportResponse = null;
        if (deleteReport != null) {
            reportResponse = this.modelMapper.forResponse().map(deleteReport, ReportResponse.class);
            return ResultHelper.success(reportResponse);
        } else {
            return ResultHelper.notFound(reportResponse);
        }
    }

    @GetMapping("/vaccines/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<Vaccine>> getVaccinesByReportId(@PathVariable("id") long id) {
        Report report = this.reportService.getById(id);
        if (report != null) {
            List<Vaccine> vaccines = report.getVaccines();
            return ResultHelper.success(vaccines);
        } else {
            return ResultHelper.notFound(null);
        }
    }
}
