package com.parcialspring.parcialspring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "suppliers", uniqueConstraints = @UniqueConstraint(columnNames = {"email", "nit", "supplier_id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_id", nullable = false, unique = true)
    private String supplierId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", nullable = false,  unique = true)
    private String email;

    @Column(name = "city_id",  nullable = false)
    private String cityId;

    @Column(name = "state_id", nullable = false)
    private String stateId;

    @Column(name = "country_id",  nullable = false)
    private String countryId;

    @Column(name = "nit",  nullable = false, unique = true)
    private String nit;

    @Column(name = "address",  nullable = false)
    private String address;
}
