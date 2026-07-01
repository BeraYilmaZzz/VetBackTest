package com.bera.yilmaz.VetManagementSystem.Api;

import com.bera.yilmaz.VetManagementSystem.Business.Abs.IAppointmentService;
import com.bera.yilmaz.VetManagementSystem.Business.Abs.IDoctorService;
import com.bera.yilmaz.VetManagementSystem.Core.Config.ModelMapper.IModelMapperService;
import com.bera.yilmaz.VetManagementSystem.Core.Result.ResultData;
import com.bera.yilmaz.VetManagementSystem.Core.Utilies.ResultHelper;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.AppointmentResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Animal;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Appointment;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Doctor;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController {
    private final IDoctorService doctorService;
    private final IAppointmentService appointmentService;
    private final IModelMapperService modelMapper;

    public AppointmentController(IDoctorService doctorService, IAppointmentService appointmentService, IModelMapperService modelMapper) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@RequestBody @Valid Appointment appointment) {
        // Randevu için gelen talep verileri
        LocalDateTime appointmentDate = appointment.getAppointmentDate();
        Long doctorId = appointment.getDoctor().getId(); // veya direkt olarak Doctor nesnesini kullanabilirsiniz
        // Doktorun uygun olduğu tarihleri kontrol et
        if (!doctorService.isDoctorAvailable(doctorId, appointmentDate)) {
            AppointmentResponse appointmentResponse = this.modelMapper.forResponse().map(appointment,AppointmentResponse.class);
            System.out.println("Doktorun müsait günü değil");
            return ResultHelper.fail(appointmentResponse);

        } else {
            // Doktor uygunsa işlemi devam ettir
            Doctor doctor = appointmentService.getDoctorByDoctorId(appointment.getDoctor().getId());
            Animal animal = appointmentService.getAnimalByAnimalId(appointment.getAnimal().getId());

            Appointment saveAppointment = appointmentService.save(appointment);
            saveAppointment.setDoctor(doctor);
            saveAppointment.setAnimal(animal);
            AppointmentResponse appointmentResponse=this.modelMapper.forResponse().map(saveAppointment,AppointmentResponse.class);
            return ResultHelper.created(appointmentResponse);
        }
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<Appointment> update(@PathVariable long id , @RequestBody @Valid Appointment appointment) {
        Appointment updateApp = appointmentService.update(id,appointment);
        return ResultHelper.success(updateApp);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<Appointment>> findAll() {
        List<Appointment> appointments = appointmentService.getAll();
        return ResultHelper.success(appointments);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<Appointment> delete(@PathVariable long id) {
        Appointment deletedAppointment = appointmentService.delete(id);
        Appointment response = modelMapper.forResponse().map(deletedAppointment, Appointment.class);
        return ResultHelper.success(response);
    }
    @GetMapping("/get/doctor-date/{doctorname}/{date}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> findAllByDoctorAndDate(
            @PathVariable String doctorname,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        List<AppointmentResponse> responseList = appointmentService.getAppointmentsByDateAndDoctor( startOfDay, endOfDay, doctorname)
                .stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(responseList);
    }
    @GetMapping("/filter_date_and_animal/{animalName}/{date}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse >> findAllByAnimalAndDate(
            @PathVariable String animalName,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        List<AppointmentResponse> responseList = appointmentService.getAppointmentsByDateAndAnimal(startOfDay,endOfDay,animalName)
                .stream()
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(responseList);
    }
    @GetMapping("/get/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<Appointment>> findDateByDoctorId(@PathVariable long doctorId){
        List<Appointment> appointmentList = appointmentService.getDateByDoctorId(doctorId);
        return ResultHelper.success(appointmentList);
    }


}