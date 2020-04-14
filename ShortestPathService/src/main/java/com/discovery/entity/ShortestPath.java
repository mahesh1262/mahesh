package com.discovery.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;


/**
 * @author Mahesh.Bonagiri
 */
@Entity
@Data
public class ShortestPath {
    
    @Id
    private Long id;
	private String planetNode;
	private String planetName;
	private String path;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlanetNode() {
		return planetNode;
	}

	public void setPlanetNode(String planetNode) {
		this.planetNode = planetNode;
	}

	public String getPlanetName() {
		return planetName;
	}

	public void setPlanetName(String planetName) {
		this.planetName = planetName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}






}
