package com.example.demoWeb.services;

import com.example.demoWeb.entities.EntityStation;
import com.example.demoWeb.entities.EntityTrain;
import com.example.demoWeb.entities.EntityWagon;
import com.example.demoWeb.entities.EntityWay;
import com.example.demoWeb.repository.RepositoryTrain;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ServicesTrain {
    private RepositoryTrain repositoryTrain;

    public ServicesTrain(RepositoryTrain repositoryTrain) {
        this.repositoryTrain = repositoryTrain;
    }

    @Transactional
    public EntityTrain createTrain(EntityTrain entityTrain) {
        return repositoryTrain.save(entityTrain);
    }

    @Transactional
    public List<EntityTrain> getTrainsOnWay(long wId) {
        List<EntityTrain> entityTrains = (List<EntityTrain>) repositoryTrain.findAll();
        if (!entityTrains.isEmpty()) {
            return new ArrayList(entityTrains.stream()
                    .filter((entityTrain) -> entityTrain.getWay().getId() == wId).toList());

        }
        return new ArrayList(entityTrains);
    }
    @Transactional
    public EntityTrain getLastWagonInTrain(long wId) {
        EntityTrain lastWagon = null;
        List<EntityTrain> entityTrains = (List<EntityTrain>) repositoryTrain.findAll();
        if (!entityTrains.isEmpty()) {
            lastWagon = (EntityTrain) entityTrains.stream()
                    .filter((entityTrain) -> entityTrain.getWay().getId() == wId)
                    .max(Comparator.comparing(i -> i.getOrderNum()))
                    .get();

        }
        return lastWagon;
    }


    @Transactional
    public EntityTrain getFirstWagonInTrain(long wId) {
        EntityTrain firstWagon = null;
        List<EntityTrain> entityTrains = (List<EntityTrain>) repositoryTrain.findAll();
        if (!entityTrains.isEmpty()) {
            firstWagon = (EntityTrain) entityTrains.stream()
                    .filter((entityTrain) -> entityTrain.getWay().getId() == wId)
                    .min(Comparator.comparing(i -> i.getOrderNum()))
                    .get();

        }
        return firstWagon;
    }

    @Transactional
    public EntityTrain getTrainForWagon(long wagonId) {
        EntityTrain entityTr = null;
        List<EntityTrain> entityTrains = (List<EntityTrain>) repositoryTrain.findAll();
        if (!entityTrains.isEmpty()) {
            entityTr = (EntityTrain) entityTrains.stream()
                    .filter((entityTrain) -> entityTrain.getWagon().getId() == wagonId)
                    .findFirst()
                    .get();
        }
        return entityTr;
    }

    @Transactional
    public EntityWay getWayForWagon(long wagonId) {
        EntityWay entityWay = null;
        List<EntityTrain> entityTrains = (List<EntityTrain>) repositoryTrain.findAll();
        if (!entityTrains.isEmpty()) {
            entityWay = (EntityWay) entityTrains.stream()
                    .filter((entityTrain) -> entityTrain.getWagon().getId() == wagonId)
                    .findFirst()
                    .get()
                    .getWay();
        }
        return entityWay;
    }
    @Transactional
    public Long deleteTrain(Long id) {
        EntityTrain entityTrain = repositoryTrain.findById(id).get();
        if (entityTrain == null) {
            throw new RestClientException("Train with id= " + id + "not found");
        }
        repositoryTrain.deleteById(id);
        return id;
    }
}
