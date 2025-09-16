package org.hibernate.bugs;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class City {
	@Id String name;
	String state;
	int population;

	public City() {
	}

	public City(String name, String state, int population) {
		this.name = name;
		this.state = state;
		this.population = population;
	}
}
