package com.bera.yilmaz.VetManagementSystem.Business.Concrete;

import com.bera.yilmaz.VetManagementSystem.Business.Abs.IAnimalService;
import com.bera.yilmaz.VetManagementSystem.Business.Abs.IAppointmentService;
import com.bera.yilmaz.VetManagementSystem.Business.Abs.IDoctorService;
import com.bera.yilmaz.VetManagementSystem.Core.Config.ModelMapper.IModelMapperService;
import com.bera.yilmaz.VetManagementSystem.Core.Exception.NotFoundException;
import com.bera.yilmaz.VetManagementSystem.Dao.AnimalRepo;
import com.bera.yilmaz.VetManagementSystem.Dao.AppointmentRepo;
import com.bera.yilmaz.VetManagementSystem.Dao.DoctorRepo;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.AppointmentResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Animal;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Appointment;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Doctor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentManager implements IAppointmentService {
    private final IModelMapperService modelMapper;
    private final DoctorRepo doctorRepo;
    private final AnimalRepo animalRepo;
    private final AppointmentRepo appointmentRepo;
    private final IDoctorService doctorService;
    private final IAnimalService animalService;

    public AppointmentManager(IModelMapperService modelMapper, DoctorRepo doctorRepo, AnimalRepo animalRepo, AppointmentRepo appointmentRepo, IDoctorService doctorService, IAnimalService animalService) {
        this.modelMapper = modelMapper;
        this.doctorRepo = doctorRepo;
        this.animalRepo = animalRepo;
        this.appointmentRepo = appointmentRepo;
        this.doctorService = doctorService;
        this.animalService = animalService;
    }

    @Override
    public Appointment save(Appointment appointment) {
        // Randevu eklemeden önce çakışan randevuları kontrol et
        checkConflictingAppointments(appointment);

        // Eğer doktor null değilse randevuyu kaydet
        if (appointment.getDoctor() != null) {
            return appointmentRepo.save(appointment);
        } else {
            throw new RuntimeException("Doktor bilgisi boş olamaz!");
        }
    }

    @Override
    public Appointment update(long id, Appointment appointment) {
        return appointmentRepo.save(appointment);
    }

    @Override
    public Appointment delete(long id) {
        Optional<Appointment> appointmentFromDb = appointmentRepo.findById(id);
        if (appointmentFromDb.isPresent()) {
            Appointment deletedAppointment = appointmentFromDb.get();
            appointmentRepo.delete(deletedAppointment);
            return deletedAppointment;
        } else {
            throw new NotFoundException(id + " id'li randevu sistemde bulunamadı!!!");
        }
    }

    @Override
    public List<Appointment> getAll() {
        return appointmentRepo.findAll();
    }

    @Override
    public Doctor getDoctorById(long id) {
        return doctorService.get(id);
    }

    @Override
    public List<Appointment> getAppointmentsByDateAndDoctor(LocalDateTime startDate, LocalDateTime endDate, String doctorName) {
        Doctor doctor = doctorService.get(doctorName);
        if (doctor == null) {
            throw new RuntimeException("Doktor bulunamadı!");
        }

        return appointmentRepo.findByDoctorIdAndDateTimeBetween(
                doctor.getId(),
                startDate,
                endDate
        );
    }
    @Override
    public List<Appointment> getAppointmentsByDateAndAnimal(LocalDateTime startDate, LocalDateTime endDate, String animalName) {
        Animal animal = animalService.getByName(animalName);
        if(animal == null){
            throw new RuntimeException("Hayvan bulunamadı!");
        }
        return appointmentRepo.findByAnimalIdAndDateTimeBetween(
                animal.getId(),
                startDate,
                endDate
        );
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByDateAndAnimalId(LocalDateTime startOfDay, LocalDateTime endOfDay, Long animalId) {
        List<Appointment> appointments = appointmentRepo.findByAnimalIdAndDateTimeBetween(
                animalId,
                startOfDay,
                endOfDay
        );

        return appointments.stream()
                .filter(appointment -> startOfDay.isBefore(appointment.getAppointmentDate()) && endOfDay.isAfter(appointment.getAppointmentDate()))
                .map(appointment -> modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
    }



    @Override
    public Doctor getDoctorByDoctorId(Long id) {
        Optional<Doctor> doctorOptional = doctorRepo.findById(id);
        return doctorOptional.orElse(null);
    }

    @Override
    public Animal getAnimalByAnimalId(Long id) {
        Optional<Animal> animalOptional = animalRepo.findById(id);
        return animalOptional.orElse(null);
    }

    @Override
    public List<Appointment> getDateByDoctorId(long doctorId) {
        return appointmentRepo.findByDoctorId(doctorId); // Örnek bir metot adı ve arama yapılacak kriterlere göre ayarlanmalıdır
    }

    @Override
    public List<Appointment> getAppointmentByReportId(long reportId) {
        // Belirli bir rapor ID'sine ait tüm randevuları getir
        List<Appointment> allAppointments = appointmentRepo.findByReportId(reportId);

        // Sadece belirli bir rapor ID'sine ait hayvanların randevularını filtrele
        List<Appointment> filteredAppointments = allAppointments.stream()
                .filter(appointment -> appointment.getReport().getId() == reportId)
                .collect(Collectors.toList());

        return filteredAppointments;
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorIdAndDate(Long doctorId, LocalDateTime localDateTime) {
        return appointmentRepo.findByDoctorIdAndAppointmentDate(doctorId, localDateTime);
    }

    private void checkConflictingAppointments(Appointment newAppointment) {
        // Doktor null değilse çakışan randevuları kontrol et
        if (newAppointment.getDoctor() != null) {
            List<Appointment> conflictingAppointments = appointmentRepo.findConflictingAppointmentsForDoctor(
                    newAppointment.getDoctor().getId(),
                    newAppointment.getAppointmentDate()
            );

            if (!conflictingAppointments.isEmpty()) {
                throw new RuntimeException("Doktorun bu saatte başka bir randevusu bulunmaktadır!");
            }
        } else {
            throw new RuntimeException("Randevu oluşturulurken doktor bilgisi belirtilmelidir!");
        }
    }

}