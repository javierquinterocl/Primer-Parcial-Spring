package com.parcialspring.parcialspring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "goats", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"goat_id"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoatModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "goat_id", unique = true)
    private String goatId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "birth_date", nullable = false)
    private String birthDate;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "goat_type")
    private String goatType = "LEVANTE";

    @Column(name = "weight")
    private Double weight = 0d;

    @Column(name = "milk_production")
    private Double milkProduction = 0d;

    @Column(name = "food_consumption")
    private Double foodConsumption = 0d;

    @Column(name = "vaccinations_count")
    private Integer vaccinationsCount = 0;

    @Column(name = "heat_periods")
    private Integer heatPeriods = 0;

    @Column(name = "offspring_count")
    private Integer offspringCount = 0;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "status")
    private String status = "ACTIVE";

    @Column(name = "notes")
    private String notes;
}
