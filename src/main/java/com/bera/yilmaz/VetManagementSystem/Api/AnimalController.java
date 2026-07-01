package com.bera.yilmaz.VetManagementSystem.Api;

import com.bera.yilmaz.VetManagementSystem.Business.Abs.IAnimalService;
import com.bera.yilmaz.VetManagementSystem.Core.Config.ModelMapper.IModelMapperService;
import com.bera.yilmaz.VetManagementSystem.Core.Result.ResultData;
import com.bera.yilmaz.VetManagementSystem.Core.Utilies.ResultHelper;
import com.bera.yilmaz.VetManagementSystem.Dto.Response.AnimalResponse;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Animal;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Customer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/animals")
public class AnimalController {

    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;

    public AnimalController(IAnimalService animalService, IModelMapperService modelMapper) {
        this.animalService = animalService;
        this.modelMapper = modelMapper;
    }
    @PostMapping // Animal kaydetme
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<Animal> save(@RequestBody @Valid Animal animal) {
        Optional<Animal> isAnimalExist = animalService.findByNameAndSpeciesAndBreed(animal.getName(), animal.getSpecies(), animal.getBreed());
        if (isAnimalExist.isPresent()) {
            return ResultHelper.failWithData(animal);
        } else {
            Customer customer = animalService.getCustomerByCustomerId(animal.getCustomer().getId());
            Animal savedAnimal = animalService.save(animal);
            savedAnimal.setCustomer(customer);
            return ResultHelper.created(savedAnimal);
        }
    }

    @PutMapping("/update/{id}") //id ye göre animal update etme
    @ResponseStatus(HttpStatus.OK)
    public ResultData<Animal> update(@PathVariable long id, @RequestBody @Valid Animal animal) {
        Animal updatedAnimal = animalService.update(id, animal);
        return ResultHelper.success(updatedAnimal);
    }

    @GetMapping("/get/{id}") // id ye göre animal listeleme Hayvanları kaydetme, bilgilerini güncelleme, görüntüleme ve silme
    @ResponseStatus(HttpStatus.OK)
    public ResultData<Animal> getById(@PathVariable long id) {
        Animal animal = animalService.getById(id);
        return ResultHelper.success(animal);
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getByName(@PathVariable String name) {
        Animal animals = animalService.getByName(name);
        List<AnimalResponse> animalResponses = Collections.singletonList(this.modelMapper.forResponse().map(animals, AnimalResponse.class));
        return ResultHelper.success(animalResponses);
    }

    @GetMapping("/all") // tüm hayvanları listeleme
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<Animal>> getAll() {
        List<Animal> animals = animalService.getAll();
        return ResultHelper.success(animals);
    }

        @GetMapping("/customer/{customerId}") // Hayvan sahibinin sistemde kayıtlı tüm hayvanlarını görüntülemek için API end point'ini oluşturmak.
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAllByCustomerId(@PathVariable long customerId) {
        List<AnimalResponse> animals = animalService.getAllByCustomerId(customerId);
        return ResultHelper.success(animals);
    }
    @GetMapping("/customer/name/{customerName}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAllByCustomerName(@PathVariable String customerName) {
        List<Animal> animals = animalService.getAllByCustomerName(customerName);
        List<AnimalResponse> response = animals.stream()
                .map(animal -> modelMapper.forResponse().map(animal, AnimalResponse.class))
                .collect(Collectors.toList());
        return ResultHelper.success(response);
    }

    @DeleteMapping("/delete/{id}") // id ye göre animal silme
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> deleteById(@PathVariable ("id") long id) {
        Animal deleteAnimal = animalService.deleteById(id);
        AnimalResponse animalResponse = null;
        if (deleteAnimal != null) {
            animalResponse = this.modelMapper.forResponse().map(deleteAnimal, AnimalResponse.class);
            return ResultHelper.success(animalResponse);
        } else {
            return ResultHelper.deleted(animalResponse);
        }
    }
}