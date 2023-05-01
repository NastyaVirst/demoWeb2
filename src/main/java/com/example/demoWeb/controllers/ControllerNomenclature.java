package com.example.demoWeb.controllers;

import ch.qos.logback.classic.Logger;
import com.example.demoWeb.entities.EntityNomenclature;
import com.example.demoWeb.services.ServicesNomenclature;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Tag(name = "Номенклатура", description = "Информация по номенклатуре")
public class ControllerNomenclature {
    private final ServicesNomenclature servicesNomenclature;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ControllerNomenclature.class);

    public ControllerNomenclature(ServicesNomenclature servicesNomenclature) {
        this.servicesNomenclature = servicesNomenclature;
    }

    @GetMapping(value = "/nomenclature", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получить информацию по всей номенклатуре")
    @ResponseBody
    public List<EntityNomenclature> getAllNomenclature() {
        logger.info("Get info about nomenclature");
        List<EntityNomenclature> listNomenclature = servicesNomenclature.readNomenclatures();
        if (listNomenclature.isEmpty()) {
            logger.error("Nomenclature not found");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "nomenclature not found"
            );
        }
        return listNomenclature;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/nomenclature", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавить номенклатуру")
    public EntityNomenclature createNomenclature(@RequestBody EntityNomenclature entityNomenclature) {
        logger.info("Create new nomenclature");
        return servicesNomenclature.createNomenclature(entityNomenclature);
    }

    @ResponseBody
    @PutMapping(value = "/nomenclature/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Обновить информацию по номенклатуре")
    public EntityNomenclature updateWagon(@PathVariable Long id, @RequestBody EntityNomenclature entityNomenclature) {
        logger.info("Update nomenclature with id="+id);
        return servicesNomenclature.updateNomenclature(id, entityNomenclature);
    }
    @ResponseBody
    @DeleteMapping("/nomenclature/{id}")
    @Operation(summary = "Удалить номенклатуру")
  /*  @SecurityRequirement(name="user")*/
    public Long deleteNomenclature(@PathVariable Long id) {
        logger.info("Delete nomenclature with id="+id);
        return servicesNomenclature.deleteNomenclature(id);
    }
}
