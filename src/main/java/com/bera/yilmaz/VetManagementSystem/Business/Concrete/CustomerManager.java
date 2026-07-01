package com.bera.yilmaz.VetManagementSystem.Business.Concrete;

import com.bera.yilmaz.VetManagementSystem.Business.Abs.ICustomerService;
import com.bera.yilmaz.VetManagementSystem.Core.Exception.NotFoundException;
import com.bera.yilmaz.VetManagementSystem.Core.Utilies.Msg;
import com.bera.yilmaz.VetManagementSystem.Dao.CustomerRepo;
import com.bera.yilmaz.VetManagementSystem.Dto.Request.Customer.CustomerSaveRequest;
import com.bera.yilmaz.VetManagementSystem.Entitiy.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerManager implements ICustomerService {
    private final CustomerRepo customerRepo;

    public CustomerManager(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }
   @Override
    public Customer save(Customer customer) {
        return this.customerRepo.save(customer);
    }

    @Override
    public Customer get(String name) {
        return this.customerRepo.findByName(name);
    }

    public Customer update(long id, CustomerSaveRequest request) {
        Optional<Customer> optionalCustomer = customerRepo.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setName(request.getName());
            customer.setPhone(request.getPhone());
            customer.setMail(request.getMail());
            customer.setAddress(request.getAddress());
            customer.setCity(request.getCity());
            return customerRepo.save(customer);
        } else {
            throw new NotFoundException(Msg.NOT_FOUND);
        }
    }
    public Customer deleteById(long id) {
        Optional<Customer> customerFromDb = customerRepo.findById(id);
        if (customerFromDb.isPresent()) {
            Customer deletedCustomer = customerFromDb.get();
            customerRepo.delete(deletedCustomer);
            return deletedCustomer;
        } else {
            throw new NotFoundException(id + " id'li müşteri sistemde bulunamadı!!!");
        }
    }
    public List<Customer> findAll(){
        return this.customerRepo.findAll();
    }

    @Override
    public boolean existsByMail(String mail) {
        return customerRepo.existsByMail(mail);
    }
}
