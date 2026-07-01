package com.bera.yilmaz.VetManagementSystem.Entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name ="doctor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="doctor_id", columnDefinition = "serial")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="phone")
    private String phone;

    @Column(name="mail")
    private String mail;

    @Column(name="address")
    private String address;

    @Column(name="city")
    private String city;

    //Eğer bir Book silindiğinde ona bağlı tüm BookBorrowing'lerin de silinmesini istiyorsak
    //cascade = {CascadeType.REMOVE} bu işlevi yerine getirecektir.
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<AvailableDate> doctorAvailableDate;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Appointment> appointment;
}
