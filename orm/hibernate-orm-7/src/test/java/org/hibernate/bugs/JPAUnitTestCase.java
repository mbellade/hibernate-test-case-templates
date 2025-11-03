package org.hibernate.bugs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Persistence;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@BeforeEach
	void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@AfterEach
	void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	void hhh123Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.createQuery(
				"from Parent p join p.mid.child1 c1 left join p.mid.child2 c2",
				Parent.class
		).getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Entity(name = "Parent")
	public static class Parent {
		@Id
		@GeneratedValue
		private Long id;

		@ManyToOne
		private Mid mid;
	}

	@Entity(name = "Mid")
	public static class Mid {
		@Id
		@GeneratedValue
		private Long id;

		@ManyToOne
		private Child1 child1;

		@ManyToOne
		private Child2 child2;
	}

	@Entity(name = "Child1")
	public static class Child1 {
		@Id
		@GeneratedValue
		private Long id;
	}

	@Entity(name = "Child2")
	public static class Child2 {
		@Id
		@GeneratedValue
		private Long id;
	}
}
