package com.example.demoWeb.services;

import com.example.demoWeb.entities.EntityReceptWagon;
import com.example.demoWeb.repository.RepositoryReceptWagon;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
public class ServicesReceptWagon {
    private RepositoryReceptWagon repositoryReceptWagon;

    public ServicesReceptWagon(RepositoryReceptWagon repositoryReceptWagon) {
        this.repositoryReceptWagon = repositoryReceptWagon;
    }

    @Transactional
    public List<EntityReceptWagon> readReceptWagons() {
        return (List<EntityReceptWagon>) repositoryReceptWagon.findAll();
    }

    @Transactional
    public EntityReceptWagon createReceptWagon(EntityReceptWagon entityReceptWagon) {
        return repositoryReceptWagon.save(entityReceptWagon);
    }

    @Transactional
    public EntityReceptWagon updateReceptWagon(Long id, EntityReceptWagon entityReceptWagon) {
        EntityReceptWagon entityReceptWagonFromDB = repositoryReceptWagon.findById(id).get();
        if (entityReceptWagonFromDB == null) {
            throw new RestClientException("ReceptWagon with id= " + entityReceptWagonFromDB.getId() + "not found");
        }
        entityReceptWagonFromDB.setOrderNum(entityReceptWagon.getOrderNum());
        entityReceptWagonFromDB.setNum(entityReceptWagon.getNum());
        entityReceptWagonFromDB.setNomenclature(entityReceptWagon.getNomenclature());
        entityReceptWagonFromDB.setCargoWeight(entityReceptWagon.getCargoWeight());
        entityReceptWagonFromDB.setWagonWeight(entityReceptWagon.getWagonWeight());
        return repositoryReceptWagon.save(entityReceptWagonFromDB);
    }

    @Transactional
    public Long deleteReceptWagon(Long id) {
        EntityReceptWagon entityReceptWagon = repositoryReceptWagon.findById(id).get();
        if (entityReceptWagon == null) {
            throw new RestClientException("ReceptWagon with id= " + id + "not found");
        }
        repositoryReceptWagon.deleteById(id);
        return id;
    }
}
