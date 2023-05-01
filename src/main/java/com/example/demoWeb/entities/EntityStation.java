package com.example.demoWeb.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Schema(description = "Станции")
@Entity
@Table (name = "stations")

public class EntityStation {

    @Schema(description = "Идентификатор станции", hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;

    @Schema(description = "Номер станции")
    @Column(nullable = false)
    private String num;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
