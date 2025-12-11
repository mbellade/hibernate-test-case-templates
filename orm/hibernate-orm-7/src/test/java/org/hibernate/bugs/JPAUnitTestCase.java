package org.hibernate.bugs;

import java.util.*;

import org.hibernate.bugs.entity.Length;
import org.hibernate.bugs.entity.Material;
import org.hibernate.bugs.entity.Weight;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@BeforeEach
	void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");

		final var material = new Material();
		material.setId(1L);

		final var weight = new Weight();
		weight.setValue("WeightValue");
		material.setWeight(weight);

		final var length = new Length();
		length.setValue("LengthValue");
		material.setLength(length);

		final var entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(material);
		entityManager.flush();
		entityManager.getTransaction().commit();
	}

	@AfterEach
	void destroy() {
		entityManagerFactory.close();
	}

	@Test
	void testPlainEntitySelect() {
		var result = entityManagerFactory.createEntityManager()
				.createQuery("select m from Material m")
				.getResultList();

		Assertions.assertEquals( 1, result.size());

		var item = (Material)result.get( 0);
		Assertions.assertEquals("WeightValue", item.getWeight().getValue());
		Assertions.assertEquals("LengthValue", item.getLength().getValue());
	}

	@Test
	void testEmbeddedValuesNestedSelect() {
		var result = entityManagerFactory.createEntityManager()
				.createQuery("select q.weight, q.length from (select m.weight as weight, m.length as length from Material m) q")
				.getResultList();

		Assertions.assertEquals(1, result.size());

		var item = (Object[]) result.get(0);
		Assertions.assertEquals("WeightValue", ((Weight)item[0]).getValue());
		Assertions.assertEquals("LengthValue", ((Length)item[1]).getValue()); // <- Fails because this is "WeightValue" for some reason
	}

	@Test
	void testEmbeddedValuesSelect() {
		var result = entityManagerFactory.createEntityManager()
				.createQuery("select m.weight, m.length from Material m")
				.getResultList();

		Assertions.assertEquals(1, result.size());

		var item = (Object[]) result.get(0);
		Assertions.assertEquals("WeightValue", ((Weight)item[0]).getValue());
		Assertions.assertEquals("LengthValue", ((Length)item[1]).getValue()); // <- Fails because this is "WeightValue" for some reason
	}

	@Test
	void testScalarValuesNestedSelect() {
		var result = entityManagerFactory.createEntityManager()
				.createQuery("select q.weight, q.length from (select m.weight.value as weight, m.length.value as length from Material m) q")
				.getResultList();

		Assertions.assertEquals(1, result.size());

		var item = (Object[]) result.get(0);
		Assertions.assertEquals("WeightValue", ((String)item[0]));
		Assertions.assertEquals("LengthValue", ((String)item[1]));
	}
}
