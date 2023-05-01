package com.example.demoWeb.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Optional;

@Schema(description = "Пути")
@Entity
@Table(name = "ways")
public class EntityWay {
    @Schema(description = "Идентификатор пути", hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;

    @Schema(description = "Номер пути")
    @Column(nullable = false)
    private String num;

    @Schema(description = "Идентификатор станции, которой принадлежит путь")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_station")
    private EntityStation station;

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

    public EntityStation getStation() {
        return station;
    }

    public void setStation(EntityStation station) {
        this.station = station;
    }
}
