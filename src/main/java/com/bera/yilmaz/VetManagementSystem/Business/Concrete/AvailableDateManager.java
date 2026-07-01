package com.bera.yilmaz.VetManagementSystem.Business.Concrete;

import com.bera.yilmaz.VetManagementSystem.Business.Abs.IAvailableDateService;
import com.bera.yilmaz.VetManagementSystem.Core.Exception.NotFoundException;
import com.bera.yilmaz.VetManagementSystem.Dao.AvailableDateRepo;
import com.bera.yilmaz.VetManagementSystem.Dao.DoctorRepo;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Appointment;
import com.bera.yilmaz.VetManagementSystem.Entitiy.AvailableDate;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Doctor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AvailableDateManager implements IAvailableDateService {
    private final DoctorRepo doctorRepo;

    private final AvailableDateRepo availableDateRepo;

    public AvailableDateManager(DoctorRepo doctorRepo, AvailableDateRepo availableDateRepo) {
        this.doctorRepo = doctorRepo;
        this.availableDateRepo = availableDateRepo;
    }

    @Override
    public boolean existsByDoctorIdAndAvailable_date(long doctorId, LocalDate available_date) {
        return availableDateRepo.existsByDoctorIdAndAvailable_date(doctorId, available_date);
    }

    @Override
    public AvailableDate deletebyDateId(AvailableDate availableDate) {
        Optional<AvailableDate> dateFromDb = availableDateRepo.findById(availableDate.getId());
        if (dateFromDb.isPresent()) {
            AvailableDate deleteDate = dateFromDb.get();
            availableDateRepo.delete(deleteDate);
            return deleteDate;
        } else {
            throw new NotFoundException(availableDate.getId() + " id'li doktor sistemde bulunamadı!!!");
        }
    }

    @Override
    public List<AvailableDate> getByDoctorId(long doctorId) {
        return availableDateRepo.findByDoctorId(doctorId);
    }

    @Override
    public List<AvailableDate> findAll() {
        return availableDateRepo.findAll();
    }

    @Override
    public AvailableDate get(long id) {
        return availableDateRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id + " id'li müsaitlik kaydı bulunamadı"));
    }

    @Override
    public AvailableDate save(AvailableDate saveDate) {
        return this.availableDateRepo.save(saveDate);
    }

    @Override
    public AvailableDate update(long id, AvailableDate availableDate) {
        if (availableDateRepo.existsById(id)) {
            availableDate.setId(id);
            return availableDateRepo.save(availableDate);
        } else {
            throw new NotFoundException("Hayvan bulunamadı!");
        }
    }

    @Override
    public Doctor getDoctorByDoctorId(Long id) {
        Optional<Doctor> doctorOptional = doctorRepo.findById(id);
        return doctorOptional.orElse(null);
    }
}