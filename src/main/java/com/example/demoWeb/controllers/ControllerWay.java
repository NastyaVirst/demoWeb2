package com.example.demoWeb.controllers;

import ch.qos.logback.classic.Logger;
import com.example.demoWeb.entities.EntityWay;
import com.example.demoWeb.services.ServicesStation;
import com.example.demoWeb.services.ServicesWay;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Tag(name = "Пути", description = "Информация по путям станций")
public class ControllerWay {
    private ServicesWay servicesWay;
    private ServicesStation servicesStation;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ControllerWay.class);

    public ControllerWay(ServicesWay servicesWay, ServicesStation servicesStation) {
        this.servicesWay = servicesWay;
        this.servicesStation = servicesStation;
    }

    @ResponseBody
    @GetMapping(value = "/ways", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получить информацию по путям")
    public List<EntityWay> getAllWay() {
        logger.info("Get info about ways");
        List<EntityWay> listWays = servicesWay.readWays();
        if (listWays.isEmpty()) {
            logger.error("Ways not found");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "ways not found"
            );
        }
        return listWays;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/ways", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавить путь")
    public EntityWay createWay(String num, long stId) {
        var entityWay = new EntityWay();
        entityWay.setNum(num);
        entityWay.setStation(servicesStation.readStationById(stId));
        logger.info("Create new way");
        return servicesWay.createWay(entityWay);
    }

    @ResponseBody
    @PutMapping(value = "/ways/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновить информацию по пути")
    public EntityWay updateWay(@PathVariable Long id, String num) {
        logger.info("Update way with id=" + id);
        return servicesWay.updateWay(id, num);
    }

    @ResponseBody
    @DeleteMapping("/ways/{id}")
    @Operation(summary = "Удалить путь")
    public Long deleteWay(@PathVariable Long id) {
        logger.info("Delete way with id=" + id);
        return servicesWay.deleteWay(id);
    }
}
