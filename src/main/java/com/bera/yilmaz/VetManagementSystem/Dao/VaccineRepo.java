package com.bera.yilmaz.VetManagementSystem.Dao;

import com.bera.yilmaz.VetManagementSystem.Dto.Response.AnimalResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Animal;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository

public interface VaccineRepo extends JpaRepository<Vaccine,Long> {

    Vaccine findByCodeAndProtectionFinishDateAfter(String code, LocalDate protectionFinishDate);

    Vaccine findByName(String name);

    List<Vaccine> findByAnimalId(long animalId);

    @Query("SELECT v.animal FROM Vaccine v WHERE v.protectionFinishDate BETWEEN :startDate AND :endDate")
    List<Animal> findAnimalsWithUpcomingVaccinations(LocalDate startDate, LocalDate endDate);

    @Query("SELECT v FROM Vaccine v WHERE v.protectionFinishDate BETWEEN :startDate AND :endDate")
    List<Vaccine> findVaccinesWithUpcomingVaccinations(LocalDate startDate, LocalDate endDate);

    Optional<Vaccine> findByNameAndCode(String name, String code);

    Optional<Vaccine> findByNameAndCodeAndProtectionFinishDateAfter(String name, String code, LocalDate protectionFinishDate);

    List<Vaccine> findByAnimalName(String animalName);
}
