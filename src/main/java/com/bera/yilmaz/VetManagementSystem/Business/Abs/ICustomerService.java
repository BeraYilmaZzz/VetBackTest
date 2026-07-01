package com.bera.yilmaz.VetManagementSystem.Business.Abs;

import com.bera.yilmaz.VetManagementSystem.Dto.Request.Customer.CustomerSaveRequest;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Customer;

import java.util.List;

public interface ICustomerService {
    Customer save(Customer customer);
    Customer get(String name);
    Customer update(long id, CustomerSaveRequest request);

    Customer deleteById(long id);
    List<Customer> findAll();
    boolean existsByMail(String mail);
}
