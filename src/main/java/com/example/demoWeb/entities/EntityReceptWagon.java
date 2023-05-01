package com.example.demoWeb.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Set;

@Schema(description = "Лист приема вагонов")
@Entity
@Table(name = "recept_wagons")
public class EntityReceptWagon {
    @Schema(description = "Идентификатор записи", hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;

    @Schema(description = "Порядковый номер вагона")
    @Column(name = "order_num", nullable = false)
    private Long orderNum;

    @Schema(description = "Номер вагона")
    @Column(nullable = false)
    private String num;

    @Schema(description = "Идентификатор номенклатуры", hidden = true)
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "id_nomenclature")
    private EntityNomenclature nomenclature;

    @Schema(description = "Вес груза в вагоне")
    @Column(name = "cargo_weight")
    private Long cargoWeight;

    @Schema(description = "Вес вагона")
    @Column(name = "wagon_weight", nullable = false)
    private Long wagonWeight;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public EntityNomenclature getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(EntityNomenclature nomenclature) {
        this.nomenclature = nomenclature;
    }

    public Long getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(Long cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public Long getWagonWeight() {
        return wagonWeight;
    }

    public void setWagonWeight(Long wagonWeight) {
        this.wagonWeight = wagonWeight;
    }
}


