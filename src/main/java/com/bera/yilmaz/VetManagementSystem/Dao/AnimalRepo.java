package com.bera.yilmaz.VetManagementSystem.Dao;

import com.bera.yilmaz.VetManagementSystem.Entitiy.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {
    List<Animal> findByCustomerId(long customerId);
    Animal findByName(String name);

    boolean existsById(long id);

    void deleteById(long id);
    Optional<Animal> findByNameAndSpeciesAndBreed(String name, String species, String breed);

    List<Animal> findByAppointmentId(long reportId);

    List<Animal> findByCustomerName(String customerName);

}