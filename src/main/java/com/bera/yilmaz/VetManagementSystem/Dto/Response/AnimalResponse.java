package com.bera.yilmaz.VetManagementSystem.Dto.Response;

import com.bera.yilmaz.VetManagementSystem.Entitiy.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponse { // göstereceklerimiz
    private long id;
    private String name;
    private String species;
    private String breed;
    private String gender;
    private Customer customer;
}
