package com.discovery.repository;

import org.springframework.data.repository.CrudRepository;

import com.discovery.entity.ShortestPath;


/**
 * @author Mahesh.Bonagiri
 */
public interface ShortestDistancePathRepository extends CrudRepository<ShortestPath, Long> {
}
