package com.example.demoWeb.controllers;

import ch.qos.logback.classic.Logger;
import com.example.demoWeb.entities.EntityNomenclature;
import com.example.demoWeb.entities.EntityReceptWagon;
import com.example.demoWeb.entities.EntityWay;
import com.example.demoWeb.services.ServicesNomenclature;
import com.example.demoWeb.services.ServicesReceptWagon;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Tag(name = "Лист приема вагонов", description = "Информация по натурным листам")
public class ControllerReceptWagon {
    private ServicesReceptWagon servicesReceptWagon;
    private ServicesNomenclature servicesNomenclature;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ControllerReceptWagon.class);

    public ControllerReceptWagon(ServicesReceptWagon servicesReceptWagon, ServicesNomenclature servicesNomenclature) {
        this.servicesReceptWagon = servicesReceptWagon;
        this.servicesNomenclature = servicesNomenclature;
    }

    @GetMapping(value = "/receipts", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получить информацию по всем натурным листам")
    @ResponseBody
    public List<EntityReceptWagon> getAllReceptWagon() {
        logger.info("Get info about recept wagon");
        List<EntityReceptWagon> listReceptWagon = servicesReceptWagon.readReceptWagons();
        if (listReceptWagon.isEmpty()) {
            logger.error("ReceptWagons not found");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "receptWagon not found"
            );
        }
        return listReceptWagon;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/receipts", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавить натурный лист")
    public EntityReceptWagon createReceptWagon(@RequestBody EntityReceptWagon entityReceptWagon, @RequestParam Long idNomenclature) {
        logger.info("Create new ReceptWagon");
        var entityReceptWagonN = new EntityReceptWagon();
        entityReceptWagonN.setOrderNum(entityReceptWagon.getOrderNum());
        entityReceptWagonN.setNum(entityReceptWagon.getNum());
        entityReceptWagonN.setCargoWeight(entityReceptWagon.getCargoWeight());
        entityReceptWagonN.setWagonWeight(entityReceptWagon.getWagonWeight());
        entityReceptWagonN.setNomenclature(servicesNomenclature.readNomenclatureById(idNomenclature));
        return servicesReceptWagon.createReceptWagon(entityReceptWagon);
    }

    @ResponseBody
    @PutMapping(value = "/receipts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновить информацию по натурному листу")
    public EntityReceptWagon updateReceptWagon(@PathVariable Long id, @RequestBody EntityReceptWagon entityReceptWagon) {
        logger.info("Update receptWagon with id=" + id);
        return servicesReceptWagon.updateReceptWagon(id, entityReceptWagon);
    }

    @ResponseBody
    @DeleteMapping("/receipts/{id}")
    @Operation(summary = "Удалить натурный лист")
    public Long deleteReceptWagon(@PathVariable Long id) {
        logger.info("Delete receptWagon with id=" + id);
        return servicesReceptWagon.deleteReceptWagon(id);
    }
}
