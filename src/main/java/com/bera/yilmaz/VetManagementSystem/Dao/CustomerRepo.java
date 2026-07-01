package com.bera.yilmaz.VetManagementSystem.Dao;

import com.bera.yilmaz.VetManagementSystem.Entitiy.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CustomerRepo extends JpaRepository<Customer,Long> {
    Customer findByName(String name);

    boolean existsByMail(String mail);
}
