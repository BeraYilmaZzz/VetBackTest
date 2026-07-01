package com.bera.yilmaz.VetManagementSystem.Business.Concrete;

import com.bera.yilmaz.VetManagementSystem.Business.Abs.IVaccineService;
import com.bera.yilmaz.VetManagementSystem.Core.Exception.NotFoundException;
import com.bera.yilmaz.VetManagementSystem.Core.Utilies.Msg;
import com.bera.yilmaz.VetManagementSystem.Dao.AnimalRepo;
import com.bera.yilmaz.VetManagementSystem.Dao.VaccineRepo;
import com.bera.yilmaz.VetManagementSystem.Dto.Request.Vaccine.VaccineSaveRequest;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.AnimalResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Animal;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Vaccine;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VaccineManager implements IVaccineService {

    private final VaccineRepo vaccineRepo;
    private final AnimalRepo animalRepo;

    public VaccineManager(VaccineRepo vaccineRepo, AnimalRepo animalRepo) {
        this.vaccineRepo = vaccineRepo;
        this.animalRepo = animalRepo;
    }

    @Override
    public Vaccine save(Vaccine vaccine) {
        return this.vaccineRepo.save(vaccine);
    }

    @Override
    public Vaccine get(String name) {
        return this.vaccineRepo.findByName(name);
    }

    @Override
    public Vaccine update(long id, @Valid VaccineSaveRequest update) {
        Optional<Vaccine> optionalVaccine = this.vaccineRepo.findById(id);
        if (optionalVaccine.isPresent()) {
            Vaccine vaccine = optionalVaccine.get();
            vaccine.setName(update.getName());
            vaccine.setCode(update.getCode());
            vaccine.setProtectionStratDate(update.getProtectionStratDate());
            vaccine.setProtectionFinishDate(update.getProtectionFinishDate());
            return vaccineRepo.save(vaccine);
        } else {
            throw new NotFoundException(Msg.NOT_FOUND);
        }
    }

    @Override
    public Vaccine deleteById(long id) {
        Optional<Vaccine> optionalVaccine = this.vaccineRepo.findById(id);
        if (optionalVaccine.isPresent()) {
            Vaccine deleteVaccine = optionalVaccine.get();
            vaccineRepo.delete(deleteVaccine);
            return deleteVaccine;
        } else {
            throw new NotFoundException(id + " id'li ilaç sistemde bulunamadı!!!");
        }
    }

    @Override
    public List<Vaccine> findAll() {
        return this.vaccineRepo.findAll();
    }

    @Override
    public Optional<Vaccine> isProtectionEndDateValid(String name, String code, LocalDate protectionFinishDate) {
        return vaccineRepo.findByNameAndCodeAndProtectionFinishDateAfter(name, code, protectionFinishDate);
    }

    @Override
    public boolean existsById(long id) {
        return vaccineRepo.existsById(id);
    }

    @Override
    public List<Vaccine> getVaccinesByAnimalId(long animalId) {
        return vaccineRepo.findByAnimalId(animalId);
    }

    @Override
    public List<Vaccine> getVaccinesWithUpcomingVaccinations(LocalDate startDate, LocalDate endDate) {
        return vaccineRepo.findVaccinesWithUpcomingVaccinations(startDate, endDate);
    }

    @Override
    public Optional<Vaccine> findByNameAndCode(String name, String code) {
        return vaccineRepo.findByNameAndCode(name, code);

    }

    @Override
    public List<AnimalResponse> getAnimalsWithUpcomingVaccinations(LocalDate startDate, LocalDate endDate) {
        List<Animal> animals = vaccineRepo.findAnimalsWithUpcomingVaccinations(startDate, endDate);
        return animals.stream()
                .map(animal -> {
                    AnimalResponse animalResponse = new AnimalResponse();
                    animalResponse.getId();
                    animalResponse.getName();
                    animalResponse.getSpecies();
                    animalResponse.getBreed();
                    animalResponse.getGender();
                    return animalResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Animal getAnimalByAnimalId(Long id) {
        Optional<Animal> animalResponseOptional = animalRepo.findById(id);

        return animalResponseOptional.orElse(null);
    }

    @Override
    public List<Vaccine> getVaccinesByAnimalName(String animalName) {
        return vaccineRepo.findByAnimalName(animalName);
    }
}
