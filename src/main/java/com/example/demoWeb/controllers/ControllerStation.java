package com.example.demoWeb.controllers;

import ch.qos.logback.classic.Logger;
import com.example.demoWeb.entities.EntityNomenclature;
import com.example.demoWeb.entities.EntityStation;
import com.example.demoWeb.services.ServicesNomenclature;
import com.example.demoWeb.services.ServicesStation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Tag(name = "Станции", description = "Информация по станциям")
public class ControllerStation {
    private final ServicesStation servicesStation;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ControllerStation.class);

    public ControllerStation(ServicesStation servicesStation) {
        this.servicesStation = servicesStation;
    }

    @GetMapping(value = "/station", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получить информацию по всем станциям")
    @ResponseBody
    public List<EntityStation> getAllStations() {
        logger.info("Get info about stations");
        List<EntityStation> listStation = servicesStation.readStations();
        if (listStation.isEmpty()) {
            logger.error("Stations not found");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "station not found"
            );
        }
        return listStation;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/station", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавить станцию")
    public EntityStation createStation (@RequestBody EntityStation entityStation) {
        logger.info("Create new station");
        return servicesStation.createStation(entityStation);
    }

    @ResponseBody
    @PutMapping(value = "/station/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновить информацию по станции")
    public EntityStation updateStation(@PathVariable Long id, @RequestBody EntityStation entityStation) {
        logger.info("Update station with id="+id);
        return servicesStation.updateStation(id, entityStation);
    }

    @ResponseBody
    @DeleteMapping("/station/{id}")
    @Operation(summary = "Удалить станцию")
    public Long deleteStation (@PathVariable Long id) {
        logger.info("Delete station with id="+id);
        return servicesStation.deleteStation(id);
    }
}
