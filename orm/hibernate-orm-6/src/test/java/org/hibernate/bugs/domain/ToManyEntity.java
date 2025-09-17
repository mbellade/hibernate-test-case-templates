package org.hibernate.bugs.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ToManyEntity implements IToManyEntity {
	@Id
	Long id;

	String name;

	@ManyToOne
	BatchAuftragEntity batchAuftrag;
}
