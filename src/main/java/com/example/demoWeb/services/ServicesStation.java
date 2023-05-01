package com.example.demoWeb.services;

import com.example.demoWeb.entities.EntityStation;
import com.example.demoWeb.repository.RepositoryStation;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Optional;

@Service
public class ServicesStation {
    private RepositoryStation repositoryStation;

    public ServicesStation(RepositoryStation repositoryStation) {
        this.repositoryStation = repositoryStation;
    }

    @Transactional
    public List<EntityStation> readStations() {
        return (List<EntityStation>) repositoryStation.findAll();
    }

    @Transactional
    public EntityStation createStation(EntityStation entityStation) {
        return repositoryStation.save(entityStation);
    }

    @Transactional
    public EntityStation updateStation(Long id, EntityStation entityStation) {
        EntityStation entityStationFromDB = repositoryStation.findById(id).get();
        if (entityStationFromDB == null) {
            throw new RestClientException("Station with id= " + entityStationFromDB.getId() + "not found");
        }
        entityStationFromDB.setNum(entityStation.getNum());
        return repositoryStation.save(entityStationFromDB);
    }

    @Transactional
    public EntityStation readStationById(Long id) {
        EntityStation entityStation = null;
        Optional<EntityStation> optionalEntity = repositoryStation.findById(id);
        if(optionalEntity.isPresent()) {
           entityStation = optionalEntity.get();
        }
        return entityStation;
    }

    @Transactional
    public Long deleteStation(Long id) {
        EntityStation entityStation = repositoryStation.findById(id).get();
        if (entityStation == null) {
            throw new RestClientException("Station with id= " + id + "not found");
        }
        repositoryStation.deleteById(id);
        return id;
    }
}
