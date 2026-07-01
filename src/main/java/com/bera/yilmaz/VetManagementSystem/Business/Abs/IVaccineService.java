package com.bera.yilmaz.VetManagementSystem.Business.Abs;

import com.bera.yilmaz.VetManagementSystem.Dto.Request.Vaccine.VaccineSaveRequest;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.AnimalResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Animal;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Vaccine;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IVaccineService {
    Vaccine save(Vaccine vaccine);
    Vaccine get(String name);
    Vaccine update(long id, @Valid VaccineSaveRequest vaccineSaveRequest);
    Vaccine deleteById(long id);
    List<Vaccine> findAll();

    Optional<Vaccine> isProtectionEndDateValid(String name, String code, LocalDate protectionFinishDate);

    boolean existsById(long id);

    List<Vaccine> getVaccinesByAnimalId(long animalId);
    List<Vaccine> getVaccinesWithUpcomingVaccinations(LocalDate startDate, LocalDate endDate);
    Optional<Vaccine> findByNameAndCode(String name, String code);

    List<AnimalResponse> getAnimalsWithUpcomingVaccinations(LocalDate startDate, LocalDate endDate);

    Animal getAnimalByAnimalId(Long id);

    List<Vaccine> getVaccinesByAnimalName(String animalName);
}
