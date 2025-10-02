package com.parcialspring.parcialspring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "suppliers", uniqueConstraints = @UniqueConstraint(columnNames = {"email", "nit"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_id", length = 15, nullable = false, unique = true)
    private String supplierId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "city_id", length = 3, nullable = false)
    private String cityId;

    @Column(name = "state_id", length = 3, nullable = false)
    private String stateId;

    @Column(name = "country_id", length = 3, nullable = false)
    private String countryId;

    @Column(name = "nit", length = 10, nullable = false, unique = true)
    private String nit;

    @Column(name = "address", length = 100, nullable = false)
    private String address;
}
