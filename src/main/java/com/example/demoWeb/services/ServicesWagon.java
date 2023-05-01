package com.example.demoWeb.services;

import com.example.demoWeb.entities.EntityStation;
import com.example.demoWeb.repository.RepositoryWagon;
import com.example.demoWeb.entities.EntityWagon;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Optional;

@Service
public class ServicesWagon {
    private RepositoryWagon repositoryWagon;

    public ServicesWagon(RepositoryWagon repositoryWagon) {
        this.repositoryWagon = repositoryWagon;
    }

    @Transactional
    public List<EntityWagon> readWagons() {
        return (List<EntityWagon>) repositoryWagon.findAll();
    }

    @Transactional
    public EntityWagon createWagon(EntityWagon entityWagon) {
        return repositoryWagon.save(entityWagon);
    }

    @Transactional
    public EntityWagon updateWagon(Long id, EntityWagon entityWagon) {
        EntityWagon entityWagonFromDB = repositoryWagon.findById(id).get();
        if (entityWagonFromDB == null) {
            throw new RestClientException("Wagon with id= " + entityWagonFromDB.getId() + "not found");
        }
        entityWagonFromDB.setLoadCapacity(entityWagon.getLoadCapacity());
        entityWagonFromDB.setNumber(entityWagon.getNumber());
        entityWagonFromDB.setType(entityWagon.getType());
        entityWagonFromDB.setWeightBrutto(entityWagon.getWeightBrutto());
        return repositoryWagon.save(entityWagonFromDB);
    }

    @Transactional
    public Long deleteWagon(Long id) {
        EntityWagon entityWagon = repositoryWagon.findById(id).get();
        if (entityWagon == null) {
            throw new RestClientException("Wagon with id= " + id + "not found");
        }
        repositoryWagon.deleteById(id);
        return id;
    }

    @Transactional
    public EntityWagon readWagonById(Long id) {
        EntityWagon entityWagon = null;
        Optional<EntityWagon> optionalEntity = repositoryWagon.findById(id);
        if(optionalEntity.isPresent()) {
            entityWagon = optionalEntity.get();
        }
        return entityWagon;
    }
}
