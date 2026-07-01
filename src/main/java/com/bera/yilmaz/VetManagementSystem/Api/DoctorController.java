package com.bera.yilmaz.VetManagementSystem.Api;

import com.bera.yilmaz.VetManagementSystem.Business.Abs.IDoctorService;
import com.bera.yilmaz.VetManagementSystem.Core.Config.ModelMapper.IModelMapperService;
import com.bera.yilmaz.VetManagementSystem.Core.Result.ResultData;
import com.bera.yilmaz.VetManagementSystem.Core.Utilies.ResultHelper;
import com.bera.yilmaz.VetManagementSystem.Dto.Request.Doctor.DoctorSaveRequest;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.DoctorResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Doctor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/doctors")
public class DoctorController {
    private final IDoctorService doctorService;
    private final IModelMapperService modelMapper;

    public DoctorController(IDoctorService doctorService, IModelMapperService modelMapper) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<DoctorResponse> save(@Valid @RequestBody DoctorSaveRequest doctorSaveRequest){
        String doctorMail= doctorSaveRequest.getMail();
        if(doctorService.existByMail(doctorMail)){
            return ResultHelper.fail(null);
        }else{
            Doctor saveDoctor = this.modelMapper.forRequest().map(doctorSaveRequest, Doctor.class);
            this.doctorService.save(saveDoctor);
            return ResultHelper.created(this.modelMapper.forResponse().map(saveDoctor, DoctorResponse.class));
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> get(@PathVariable("id") long id){
        Doctor doctor = this.doctorService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(doctor,DoctorResponse.class));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> update(@PathVariable("id") long id, @RequestBody @Valid DoctorSaveRequest doctorSaveRequest) {
        Doctor updatedDoctor = this.doctorService.update(id, doctorSaveRequest);
        DoctorResponse doctorResponse = null;
        if (updatedDoctor != null) {
            doctorResponse = this.modelMapper.forResponse().map(updatedDoctor, DoctorResponse.class);
            return ResultHelper.success(doctorResponse);
        } else {
            return ResultHelper.notFound(doctorResponse);
        }
    }
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> delete(@PathVariable("id") long id) {
        Doctor deleteDoctor = this.doctorService.deleteById(id);
        DoctorResponse doctorResponse = null;
        if (deleteDoctor != null) {
            doctorResponse = this.modelMapper.forResponse().map(deleteDoctor, DoctorResponse.class);
            return ResultHelper.success(doctorResponse);
        } else {
            return ResultHelper.notFound(doctorResponse);
        }
    }
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<DoctorResponse>> findAll() {
        List<Doctor> doctors = doctorService.findAll();
        List<DoctorResponse> doctorResponses = doctors.stream()
                .map(doctor -> modelMapper.forResponse().map(doctor, DoctorResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.success(doctorResponses);
    }

}
