package com.example.demoWeb.controllers;

import ch.qos.logback.classic.Logger;
import com.example.demoWeb.entities.EntityStation;
import com.example.demoWeb.entities.EntityWagon;
import com.example.demoWeb.services.ServicesWagon;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Вагоны", description = "Информация по вагонам")

public class ControllerWagon {
    private final ServicesWagon servicesWagon;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ControllerWagon.class);

    public ControllerWagon(ServicesWagon servicesWagon) {
        this.servicesWagon = servicesWagon;
    }

    @ResponseBody
    @GetMapping(value = "/wagons", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получить информацию по всем вагонам")
    public List<EntityWagon> getAllWagon() {
        logger.info("Get info about wagons");
        List<EntityWagon> listWagons = servicesWagon.readWagons();
        if (listWagons.isEmpty()) {
            logger.error("Wagons not found");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "wagons not found"
            );
        }
        return listWagons;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/wagons", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавить вагон")
    public EntityWagon createWagon(@RequestBody EntityWagon entityWagon) {
        logger.info("Create new wagon");
        return servicesWagon.createWagon(entityWagon);

    }

    @ResponseBody
    @PutMapping(value = "/wagons/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновить информацию по вагону")
    public EntityWagon updateWagon(@PathVariable Long id, @RequestBody EntityWagon entityWagon) {
        logger.info("Update wagon with id="+id);
        return servicesWagon.updateWagon(id, entityWagon);
    }

    @ResponseBody
    @DeleteMapping("/wagons/{id}")
    @Operation(summary = "Удалить вагон")
    public Long deleteWagon(@PathVariable Long id) {
        return servicesWagon.deleteWagon(id);
    }

}
