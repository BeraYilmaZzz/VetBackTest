package com.bera.yilmaz.VetManagementSystem.Api;

import com.bera.yilmaz.VetManagementSystem.Business.Abs.IAvailableDateService;
import com.bera.yilmaz.VetManagementSystem.Core.Config.ModelMapper.IModelMapperService;
import com.bera.yilmaz.VetManagementSystem.Core.Result.ResultData;
import com.bera.yilmaz.VetManagementSystem.Core.Utilies.ResultHelper;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.AvailableDateResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.AvailableDate;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Doctor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/available_dates")
public class AvailableDateController {
    private final IAvailableDateService availableDateService;
    private final IModelMapperService modelMapper;

    public AvailableDateController(IAvailableDateService availableDateService, IModelMapperService modelMapper) {
        this.availableDateService = availableDateService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDate> save(@Valid @RequestBody AvailableDate availableDate) {
        // Aynı doktora ve aynı tarih için kontrol
        if (availableDateService.existsByDoctorIdAndAvailable_date(availableDate.getId(), availableDate.getAvailable_date())) {
            return ResultHelper.failWithData(null);
        }
        // Aynı doktora ve aynı tarihli kayıt yoksa eklemeyi gerçekleştir
        Doctor doctor = availableDateService.getDoctorByDoctorId(availableDate.getDoctor().getId());
        AvailableDate saveDate= availableDateService.save(availableDate);
        saveDate.setDoctor(doctor);

        return ResultHelper.created(saveDate);
    }

    @PutMapping("/update/{id}") //id ye göre animal update etme
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDate> update(@PathVariable long id, @RequestBody @Valid AvailableDate availableDate) {
        AvailableDate updateDate = availableDateService.update(id, availableDate);
        return ResultHelper.success(updateDate);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> delete(@PathVariable("id") AvailableDate id) {
        AvailableDate deleteDate = this.availableDateService.deletebyDateId(id);
        AvailableDateResponse dateResponse = null;
        if (deleteDate != null) {
            dateResponse = this.modelMapper.forResponse().map(deleteDate, AvailableDateResponse.class);
            return ResultHelper.success(dateResponse);
        } else {
            return ResultHelper.notFound(dateResponse);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> get(@PathVariable("id") long id) {
        AvailableDate availableDate = this.availableDateService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(availableDate, AvailableDateResponse.class));
    }

    @GetMapping("/doctor/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AvailableDate>> getByDoctorId(@PathVariable("doctorId") long doctorId) {
        List<AvailableDate> availableDates = this.availableDateService.getByDoctorId(doctorId);
        return ResultHelper.success(availableDates);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AvailableDate>> findAll() {
        List<AvailableDate> availableDates = this.availableDateService.findAll();
        return ResultHelper.success(availableDates);
    }
}
