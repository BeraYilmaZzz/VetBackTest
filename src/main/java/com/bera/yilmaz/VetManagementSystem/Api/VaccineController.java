package com.bera.yilmaz.VetManagementSystem.Api;

import com.bera.yilmaz.VetManagementSystem.Business.Abs.IAnimalService;
import com.bera.yilmaz.VetManagementSystem.Business.Abs.IAppointmentService;
import com.bera.yilmaz.VetManagementSystem.Business.Abs.IVaccineService;
import com.bera.yilmaz.VetManagementSystem.Core.Config.ModelMapper.IModelMapperService;
import com.bera.yilmaz.VetManagementSystem.Core.Result.ResultData;
import com.bera.yilmaz.VetManagementSystem.Core.Utilies.ResultHelper;
import com.bera.yilmaz.VetManagementSystem.Dto.Request.Vaccine.VaccineSaveRequest;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.AnimalResponse;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.VaccineResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Animal;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Appointment;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Vaccine;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {
    private final IAnimalService animalService;
    private final IVaccineService vaccineService;
    private final IModelMapperService modelMapper;
    private final IAppointmentService appointmentService;

    public VaccineController(IAnimalService animalService, IVaccineService vaccineService, IModelMapperService modelMapper, IAppointmentService appointmentService) {
        this.animalService = animalService;
        this.vaccineService = vaccineService;
        this.modelMapper = modelMapper;
        this.appointmentService = appointmentService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@RequestBody @Valid VaccineSaveRequest request) {
        Optional<Vaccine> vaccineOptional=vaccineService.findByNameAndCode(request.getName(),request.getCode());
        VaccineResponse savedVac = this.modelMapper.forResponse().map(request, VaccineResponse.class);
        if (vaccineOptional.isPresent()) {
            return ResultHelper.failWithData(savedVac);
        } else {
            Animal animal=vaccineService.getAnimalByAnimalId(request.getAnimal().getId());
            Vaccine saveVaccine = this.modelMapper.forRequest().map(request, Vaccine.class);
            this.vaccineService.save(saveVaccine);
            VaccineResponse saved = this.modelMapper.forResponse().map(saveVaccine, VaccineResponse.class);
            return ResultHelper.created(saved);
        }
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@PathVariable("id") long id, @RequestBody @Valid VaccineSaveRequest vaccineSaveRequest) {
        VaccineResponse vaccineResponse = null;
            Vaccine updateVaccine = this.vaccineService.update(id, vaccineSaveRequest);
            vaccineResponse = this.modelMapper.forResponse().map(updateVaccine, VaccineResponse.class);
            return ResultHelper.success(vaccineResponse);
    }


    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> delete(@PathVariable("id") long id) {
        Vaccine deleteVaccine = this.vaccineService.deleteById(id);
        VaccineResponse vaccineResponse = null;
        if (deleteVaccine==null) {
            vaccineResponse = this.modelMapper.forResponse().map(deleteVaccine, VaccineResponse.class);
            return ResultHelper.notFound(vaccineResponse);
        } else {
            return ResultHelper.success(vaccineResponse);
        }
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> findAll() {
        List<Vaccine> vaccines = vaccineService.findAll();
        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(vaccineResponses);
    }

    @GetMapping("/animal/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccinesByAnimalId(@PathVariable("animalId") long animalId) {
        List<Vaccine> vaccines = vaccineService.getVaccinesByAnimalId(animalId);
        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(vaccineResponses);
    }
    @GetMapping("/animal/name/{animalName}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccinesByAnimalName(@PathVariable("animalName") String animalName) {
        List<Vaccine> vaccines = vaccineService.getVaccinesByAnimalName(animalName);
        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(vaccineResponses);
    }

    @GetMapping("/upcoming/{protectionStratDate}/{protectionFinishDate}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getUpcomingVaccinations(
            @PathVariable("protectionStratDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @PathVariable("protectionFinishDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<Vaccine> vaccines = vaccineService.getVaccinesWithUpcomingVaccinations(startDate, endDate);
        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(vaccineResponses);
    }

    @GetMapping("/get/animal/{reportId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<Animal>> findAnimalByReportId(@PathVariable long reportId){
        List<Animal> animalList = animalService.getAnimalByAppointmentId(reportId);
        return ResultHelper.success(animalList);
    }

    @GetMapping("/get/appointment/{appointmentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<Appointment>> findAppointmentByReportId(@PathVariable long appointmentId){
        List<Appointment> appointments = appointmentService.getAppointmentByReportId(appointmentId);
        return ResultHelper.success(appointments);
    }
}

