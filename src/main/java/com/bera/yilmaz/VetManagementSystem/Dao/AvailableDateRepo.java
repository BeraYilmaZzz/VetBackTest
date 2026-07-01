    package com.bera.yilmaz.VetManagementSystem.Dao;

    import com.bera.yilmaz.VetManagementSystem.Dto.Response.AvailableDateResponse;
    import com.bera.yilmaz.VetManagementSystem.Entitiy.AvailableDate;
    import com.bera.yilmaz.VetManagementSystem.Entitiy.Doctor;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;

    import java.time.LocalDate;
    import java.util.List;
    import java.util.Optional;

    @Repository

    public interface AvailableDateRepo extends JpaRepository<AvailableDate,Long> {

        AvailableDateResponse save(AvailableDateResponse availableDateResponse);
        @Query("SELECT COUNT(ad) > 0 FROM AvailableDate ad WHERE ad.doctor.id = :doctorId AND ad.available_date = :available_date")

        boolean existsByDoctorIdAndAvailable_date(long doctorId, LocalDate available_date);

        Optional<AvailableDate> findById(long id);

        List<AvailableDate> findByDoctorId(long doctorId);


    }
