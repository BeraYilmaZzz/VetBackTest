package com.bera.yilmaz.VetManagementSystem.Business.Concrete;

import com.bera.yilmaz.VetManagementSystem.Business.Abs.IAnimalService;
import com.bera.yilmaz.VetManagementSystem.Core.Exception.NotFoundException;
import com.bera.yilmaz.VetManagementSystem.Dao.AnimalRepo;
import com.bera.yilmaz.VetManagementSystem.Dao.CustomerRepo;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.AnimalResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Animal;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimalManager implements IAnimalService {
    private final CustomerRepo customerrepo;
    private final AnimalRepo animalRepo;

    public AnimalManager( CustomerRepo customerrepo, AnimalRepo animalRepo) {
        this.customerrepo = customerrepo;
        this.animalRepo = animalRepo;
    }

    @Override
    public Animal save(Animal animal) {
        return animalRepo.save(animal);
    }

    @Override
    public Animal update(long id, Animal animal) {
        if (animalRepo.existsById(id)) {
            animal.setId(id);
            return animalRepo.save(animal);
        } else {
            throw new NotFoundException("Hayvan bulunamadı!");
        }
    }

    @Override
    public Animal getById(long id) {
        return animalRepo.findById(id).orElseThrow(() -> new NotFoundException("Hayvan bulunamadı!"));
    }

    public Animal getByName(String name) {
        return  animalRepo.findByName(name);
    }

    @Override
    public List<Animal> getAll() {
        return animalRepo.findAll();
    }

    @Override
    public List<AnimalResponse> getAllByCustomerId(long customerId) {
        return animalRepo.findByCustomerId(customerId).stream()
                .map(animal -> {
                    AnimalResponse animalResponse = new AnimalResponse();
                    animalResponse.setId(animal.getId());
                    animalResponse.setName(animal.getName());
                    animalResponse.setSpecies(animal.getSpecies());
                    animalResponse.setBreed(animal.getBreed());
                    animalResponse.setGender(animal.getGender());
                    return animalResponse;
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<Animal> getAllByCustomerName(String customerName) {
        return animalRepo.findByCustomerName(customerName);
    }

    @Override
    public Customer getCustomerByCustomerId(long id) {
        Optional<Customer> customerOptional = customerrepo.findById(id);

        return customerOptional.orElse(null);
    }


    @Override
    public Animal deleteById(long id) {
        if (animalRepo.existsById(id)) {
            animalRepo.deleteById(id);
        } else {
            throw new NotFoundException("Hayvan bulunamadı!");
        }
        return null;
    }

    @Override
    public Optional<Animal> findByNameAndSpeciesAndBreed(String name, String species, String breed) {
        return animalRepo.findByNameAndSpeciesAndBreed(name, species, breed);
    }

    @Override
    public List<Animal> getAnimalByAppointmentId(long reportId) {
        return animalRepo.findByAppointmentId(reportId);
    }


}