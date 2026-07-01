package com.bera.yilmaz.VetManagementSystem.Business.Concrete;

import com.bera.yilmaz.VetManagementSystem.Business.Abs.IDoctorService;
import com.bera.yilmaz.VetManagementSystem.Core.Exception.NotFoundException;
import com.bera.yilmaz.VetManagementSystem.Core.Utilies.Msg;
import com.bera.yilmaz.VetManagementSystem.Dao.DoctorRepo;
import com.bera.yilmaz.VetManagementSystem.Dto.Request.Doctor.DoctorSaveRequest;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Appointment;
import com.bera.yilmaz.VetManagementSystem.Entitiy.AvailableDate;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Doctor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorManager implements IDoctorService {
    private final DoctorRepo doctorRepo;

    public DoctorManager(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }
    @Override
    public Doctor save(Doctor doctor) {
        return this.doctorRepo.save(doctor);
    }

    @Override
    public Doctor get(long id) {
        return this.doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Doctor get(String name) {
        return this.doctorRepo.findByName(name);
    }

    public Doctor update(long id, DoctorSaveRequest request) {
        Optional<Doctor> optionalDoctor = doctorRepo.findById(id);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setName(request.getName());
            doctor.setPhone(request.getPhone());
            doctor.setMail(request.getMail());
            doctor.setAddress(request.getAddress());
            doctor.setCity(request.getCity());
            return doctorRepo.save(doctor);
        } else {
            throw new NotFoundException(Msg.NOT_FOUND);
        }
    }
    public Doctor deleteById(long id) {
        Optional<Doctor> doctorFromDb = doctorRepo.findById(id);
        if (doctorFromDb.isPresent()) {
            Doctor deletedDoctor = doctorFromDb.get();
            doctorRepo.delete(deletedDoctor);
            return deletedDoctor;
        } else {
            throw new NotFoundException(id + " id'li doktor sistemde bulunamadı!!!");
        }
    }
    public List<Doctor> findAll(){
        return this.doctorRepo.findAll();
    }

    @Override
    public boolean existByMail(String mail) {
        return doctorRepo.existsByMail(mail);
    }
    public boolean isDoctorAvailable(Long doctorId, LocalDateTime appointmentDateTime) {
        Optional<Doctor> optionalDoctor = doctorRepo.findById(doctorId);

        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            List<AvailableDate> availableDates = doctor.getDoctorAvailableDate();

            // Randevu tarihi ile doktorun uygun olduğu tarihleri kontrol et
            for (AvailableDate availableDate : availableDates) {
                LocalDate availableDateOnly = availableDate.getAvailable_date();
                if (appointmentDateTime.toLocalDate().isEqual(availableDateOnly)) {
                    // Randevu tarihi ile doktorun uygun olduğu tarih eşleştiğinde, saat kontrolü yap
                    if (!isAppointmentTimeAvailable(doctor, appointmentDateTime)) {
                        // Belirtilen saatte doktorun uygun olmadığını göstermek için false döndürün
                        return false;
                    }
                    // Doktor belirtilen saatte uygun olduğu için true döndürün
                    return true;
                }
            }
            // Belirtilen tarihte doktorun uygun olmadığını göstermek için false döndürün
            return false;
        } else {
            // Belirtilen ID ile doktor bulunamadığında bir NotFound istisnası fırlatın
            throw new NotFoundException("Doktor bulunamadı!");
        }
    }
   /*
    public boolean isDoctorAvailable(Long doctorId, LocalDateTime appointmentDateTime) {
        Optional<Doctor> optionalDoctor = doctorRepo.findById(doctorId);

        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            List<AvailableDate> availableDates = doctor.getDoctorAvailableDate();
            for (AvailableDate availableDate : availableDates) {
                LocalDate availableDateOnly = availableDate.getAvailable_date();
                if (appointmentDateTime.toLocalDate().isEqual(availableDateOnly)) {
                    if (!isAppointmentTimeAvailable(doctor, appointmentDateTime.toLocalTime())) {
                        return false;
                    }
                    return true;
                }
            }
            // Belirtilen tarihte doktorun uygun olmadığını göstermek için false döndürün
            return false;
        } else {
            throw new NotFoundException("Doktor bulunamadı!");
        }
    }
    */

    /*
    private boolean isAppointmentTimeAvailable(Doctor doctor, LocalTime appointmentTime) {
        // Doktorun randevu saatleri
        List<Appointment> doctorAppointments = doctor.getAppointment();
        for (Appointment appointment : doctorAppointments) {
            LocalDateTime existingAppointmentDateTime = appointment.getAppointmentDate();
            // Sadece saat kontrolü yapılır
            if (!existingAppointmentDateTime.toLocalTime().equals(appointmentTime)) {
                System.out.println("Doktorun bu saatte başka bir randevusu yok");
                return false;
            }
        }
        System.out.println("Doktorun bu saatte başka bir randevusu var");
        return true;
    }
     */
    private boolean isAppointmentTimeAvailable(Doctor doctor, LocalDateTime appointmentDateTime) {
        // Doktorun randevu saatleri
        List<Appointment> doctorAppointments = doctor.getAppointment();
        for (Appointment appointment : doctorAppointments) {
            LocalDateTime existingAppointmentDateTime = appointment.getAppointmentDate();
            // Doktorun mevcut randevusu ile verilen randevu zamanını karşılaştır
            if (existingAppointmentDateTime.toLocalTime().equals(appointmentDateTime.toLocalTime()) ||
                    existingAppointmentDateTime.toLocalTime().plusHours(1).equals(appointmentDateTime.toLocalTime())) {
                // Eğer verilen saatte veya bir saat sonrasında randevu varsa, false döndürün
                System.out.println("Doktorun bu saatte veya bir saat sonrasında başka bir randevusu var");
                return false;
            }
        }
        // Eğer verilen saatte veya bir saat sonrasında randevu yoksa, true döndürün
        System.out.println("Doktorun bu saatte veya bir saat sonrasında başka bir randevusu yok");
        return true;
    }
}
