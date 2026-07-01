package com.bera.yilmaz.VetManagementSystem.Business.Abs;

import com.bera.yilmaz.VetManagementSystem.Dto.Response.AnimalResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Animal;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Customer;

import java.util.List;
import java.util.Optional;

public interface IAnimalService {
    Animal save(Animal animal);
    Animal update(long id, Animal animal);
    Animal getById(long id);
    Animal getByName(String name);
    List<Animal> getAll();
    List<AnimalResponse> getAllByCustomerId(long customerId);
    Customer getCustomerByCustomerId(long id);
    Animal deleteById(long id);
    Optional<Animal> findByNameAndSpeciesAndBreed(String name, String species, String breed);


    List<Animal> getAnimalByAppointmentId(long reportId);

    List<Animal> getAllByCustomerName(String customerName);


}