package com.example.demoWeb.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Schema (description = "Вагоны")
@Entity
@Table(name = "wagons")
public class EntityWagon {
    @Schema (description = "Идентификатор вагона", hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;

    @Schema (description = "Номер вагона")
    @Column(nullable = false)
    private String number;

    @Schema (description = "Тип вагона")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WagonType type;

    @Schema (description = "Вес тары")
    @Column(name = "weight_brutto", nullable = false)
    private BigDecimal weightBrutto;

    @Schema (description = "Грузоподъемность")
    @Column(name = "load_capacity", nullable = false)
    private BigDecimal loadCapacity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public WagonType getType() {
        return type;
    }

    public void setType(WagonType type) {
        this.type = type;
    }

    public BigDecimal getWeightBrutto() {
        return weightBrutto;
    }

    public void setWeightBrutto(BigDecimal weightBrutto) {
        this.weightBrutto = weightBrutto;
    }

    public BigDecimal getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(BigDecimal loadCapacity) {
        this.loadCapacity = loadCapacity;
    }
}
