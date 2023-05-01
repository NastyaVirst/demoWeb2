package com.example.demoWeb.controllers;

import ch.qos.logback.classic.Logger;
import com.example.demoWeb.entities.EntityTrain;
import com.example.demoWeb.entities.EntityWay;
import com.example.demoWeb.services.ServicesTrain;
import com.example.demoWeb.services.ServicesWagon;
import com.example.demoWeb.services.ServicesWay;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@Tag(name = "Задания 1-3", description = "Операции приема, убытия, перемещения вагонов")

public class ControllerTrain {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ControllerNomenclature.class);
    private ServicesTrain servicesTrain;
    private ServicesWay servicesWay;
    private ServicesWagon servicesWagon;

    public ControllerTrain(ServicesTrain servicesTrain, ServicesWay servicesWay, ServicesWagon servicesWagon) {
        this.servicesTrain = servicesTrain;
        this.servicesWay = servicesWay;
        this.servicesWagon = servicesWagon;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/train", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Операция приема вагонов на предприятие")
    public EntityTrain createTrain(@RequestParam List<Long> wagonIds, @RequestParam long wayId) {
        logger.info("Try to enter wagons");
        EntityTrain entityTrain = null;
        for (Long wagonId : wagonIds) {
            Long lastWagonOrderNum = servicesTrain.getLastWagonInTrain(wayId).getOrderNum();
            if (lastWagonOrderNum != null) {
                entityTrain = new EntityTrain();
                entityTrain.setWagon(servicesWagon.readWagonById(wagonId));
                entityTrain.setWay(servicesWay.readWayById(wayId));
                entityTrain.setOrderNum(lastWagonOrderNum + 1);
                servicesTrain.createTrain(entityTrain);
            }
        }
        return entityTrain;
    }

    @ResponseBody
    @PostMapping(value = "/departureWagons", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Операция убытия вагонов")
    private int departureWagons(@RequestParam List<Long> wagonIds) {
        int rez = 0;
        var wayTrains = getWayTrains(wagonIds);
        for (var wayTrain : wayTrains.entrySet()) {
            Long fMin = 1L;
            var wt = wayTrain.getValue();
            Collections.sort(wt, EntityTrain.ByOrd);
            for (var t : wt) {
                if (t.getOrderNum() != fMin)
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Impossible departure move!"
                    );
                fMin++;
            }
        }
        // если не было проблем - все уходят
        for (var wayTrain : wayTrains.entrySet()) {
            var wt = wayTrain.getValue();
            for (var t : wt) {
                servicesTrain.deleteTrain(t.getId());
                rez++;
            }
        }
        return rez;
    }

    @ResponseBody
    @PostMapping(value = "/replaceWagons", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Операция перестановки вагонов внутри станции")
    private int replaceWagons(@RequestParam long wayTo, @RequestParam List<Long> wagonIds) {
        EntityWay entityWayTo = servicesWay.readWayById(wayTo);
        List<EntityTrain> trainsOnWay = servicesTrain.getTrainsOnWay(wayTo);
        int baseSize = trainsOnWay.size();
        Long tMax = servicesTrain.getLastWagonInTrain(wayTo).getOrderNum();
        Long tMin = 1L;

        var wayTrains = getWayTrains(wagonIds);
        for (var wayTrain : wayTrains.entrySet()) {
            Long fMin = 1L;
            Long fMax = servicesTrain.getLastWagonInTrain(wayTrain.getKey()).getOrderNum();
            boolean flag = true;
            while (flag) {
                flag = false;
                var wt = wayTrain.getValue();
                for (int i = 0; i < wt.size(); i++) {
                    var train = wt.get(i);
                    if (train.getOrderNum() == fMin) {
                        flag = true;
                        fMin++;
                        tMin--;
                        train.setOrderNum(tMin);
                        train.setWay(entityWayTo);
                        trainsOnWay.add(train);
                        wt.remove(i);
                        i--;
                    } else if (train.getOrderNum() == fMax) {
                        flag = true;
                        fMax--;
                        tMax++;
                        train.setOrderNum(tMax);
                        train.setWay(entityWayTo);
                        trainsOnWay.add(train);
                        wt.remove(i);
                        i--;
                    }
                }
                if (!flag && wt.size() > 0)
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Impossible station move!"
                    );
            }
        }
        // пересортировка
        for (var t : trainsOnWay)
            t.setOrderNum(t.getOrderNum() - tMin + 1);
        // отправка в БД
        for (var t : trainsOnWay)
            servicesTrain.createTrain(t);

        return trainsOnWay.size() - baseSize;
    }

    /**
     * Вспомогательный метод. Записываем в map id пути и список всех вагонов, стоящих а этом пути
     *
     * @param wagonIds - список вагонов
     * @return <ключ, значение>
     */
    @ResponseBody
    @PostMapping(value = "/getWayTrains", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получить состав, стоящий на пути")
    private Map<Long, List<EntityTrain>> getWayTrains(List<Long> wagonIds) {
        Map<Long, List<EntityTrain>> mapWayWagon = new HashMap<>();
        long stan = 0;
        for (Long wagonId : wagonIds) {
            //получим сам вагон по id
            EntityTrain entityTrain = servicesTrain.getTrainForWagon(wagonId);
            //получим его путь
            EntityWay entityWay = servicesTrain.getWayForWagon(wagonId);
            if (stan != 0 && stan != entityWay.getStation().getId())
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Cross station move!"
                );
            stan = entityWay.getStation().getId();
            Long idWay = entityWay.getId();
            if (!mapWayWagon.containsKey(idWay)) {
                mapWayWagon.put(idWay, new ArrayList<EntityTrain>());
            }
            mapWayWagon.get(idWay).add(entityTrain);
        }
        return mapWayWagon;
    }
}
