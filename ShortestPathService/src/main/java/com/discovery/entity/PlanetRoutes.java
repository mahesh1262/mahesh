package com.discovery.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author Mahesh.Bonagiri
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class PlanetRoutes {

    @Id
    private long Id;
    private String planetSource;
    private String planetDestination;
    private Float distance;
    public long getId() {
        return Id;
    }
    public void setId(long id) {
        Id = id;
    }
    public String getPlanetSource() {
        return planetSource;
    }
    public void setPlanetSource(String planetSource) {
        this.planetSource = planetSource;
    }
    public String getPlanetDestination() {
        return planetDestination;
    }
    public void setPlanetDestination(String planetDestination) {
        this.planetDestination = planetDestination;
    }
    public Float getDistance() {
        return distance;
    }
    public void setDistance(Float distance) {
        this.distance = distance;
    }



}
