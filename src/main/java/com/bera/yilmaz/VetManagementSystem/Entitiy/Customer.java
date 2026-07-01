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
@Table(name ="customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id", columnDefinition = "serial")
    private Long id;

    @Column(name="name",nullable = false)
    @NotNull
    private String name;

    @Column(name="phone",nullable = false)
    @NotNull
    private String phone;

    @Column(name="mail",nullable = false)
    @NotNull
    private String mail;

    @Column(name="address",nullable = false)
    @NotNull
    private String address;

    @Column(name="city",nullable = false)
    @NotNull
    private String city;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Animal> animal;
}
/*
ekleme http://localhost:8081/v1/customers
silme http://localhost:8081/v1/customers/delete/6
tümü http://localhost:8081/v1/customers/search
güncelle http://localhost:8081/v1/customers/update/5
x'i getir http://localhost:8081/v1/customers/John Doe
 */
