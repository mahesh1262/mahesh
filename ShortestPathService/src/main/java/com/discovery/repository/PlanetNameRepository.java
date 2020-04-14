package com.discovery.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.discovery.entity.PlanetNames;


/**
 * @author Mahesh.Bonagiri
 */
@Repository
public interface PlanetNameRepository extends CrudRepository<PlanetNames, Long> {
}
