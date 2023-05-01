package com.example.demoWeb.services;

import com.example.demoWeb.entities.EntityWagon;
import com.example.demoWeb.entities.EntityWay;
import com.example.demoWeb.repository.RepositoryWay;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Optional;

@Service
public class ServicesWay {
    private RepositoryWay repositoryWay;

    public ServicesWay(RepositoryWay repositoryWay) {
        this.repositoryWay = repositoryWay;
    }

    @Transactional
    public List<EntityWay> readWays() {
        return (List<EntityWay>) repositoryWay.findAll();
    }

    @Transactional
    public EntityWay createWay(EntityWay entityWay) {
        return repositoryWay.save(entityWay);
    }

    @Transactional
    public EntityWay updateWay(Long id, String num) {
        EntityWay entityWayFromDB = repositoryWay.findById(id).get();
        if (entityWayFromDB == null) {
            throw new RestClientException("Way with id= " + entityWayFromDB.getId() + "not found");
        }
        entityWayFromDB.setNum(num);
        return repositoryWay.save(entityWayFromDB);
    }

    @Transactional
    public Long deleteWay(Long id) {
        EntityWay entityWay = repositoryWay.findById(id).get();
        if (entityWay == null) {
            throw new RestClientException("Way with id= " + id + "not found");
        }
        repositoryWay.deleteById(id);
        return id;
    }

    @Transactional
    public EntityWay readWayById(Long id) {
        EntityWay entityWay = null;
        Optional<EntityWay> optionalEntity = repositoryWay.findById(id);
        if(optionalEntity.isPresent()) {
            entityWay = optionalEntity.get();
        }
        return entityWay;
    }

}
