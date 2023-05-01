package com.example.demoWeb.repository;

import com.example.demoWeb.entities.EntityStation;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryStation extends CrudRepository<EntityStation, Long> {
}
