package org.hibernate.bugs.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ToOneEntity {
	@Id
	Long id;

	String name;
}
