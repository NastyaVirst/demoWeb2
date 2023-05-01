package com.example.demoWeb.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Schema(description = "Номенклатура груза")
@Entity
@Table(name = "nomenclatures")
public class EntityNomenclature {

    @Schema(description = "Идентификатор номенклатуры", hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;

    @Schema(description = "Наименование номенклатуры груза")
    @Column(nullable = false)
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
