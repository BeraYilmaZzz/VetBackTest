package com.bera.yilmaz.VetManagementSystem.Dao;

import com.bera.yilmaz.VetManagementSystem.Entitiy.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Long> {
    boolean existsByMail(String mail);

    Doctor findByName(String name);
}
