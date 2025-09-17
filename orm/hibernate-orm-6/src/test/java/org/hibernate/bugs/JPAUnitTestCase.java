package org.hibernate.bugs;

import org.hibernate.bugs.domain.BatchAuftragEntity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.Path;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@BeforeEach
	void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.getTransaction().commit();
		entityManager.close();
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

		final var cb = entityManager.getCriteriaBuilder();
		final var query = cb.createQuery( BatchAuftragEntity.class );


		final var parameter = cb.parameter( String.class, "param" );

		final var root = query.from( BatchAuftragEntity.class );
		query.select( root );
		final Path<Object> benutzerId = root.get( "benutzerId" );
		query.where( cb.like( benutzerId.as( String.class ), parameter ) );
		query.orderBy( cb.desc( root.get( "anlageZeitpunkt" ) ) );
		final var results = entityManager
				.createQuery( query )
				.setParameter( "param", "1" )
				.getResultList();

		// This is apparently NOT the code that triggers the timeout:
//		final var lQuery = entityManager
//				.createQuery(
//						"SELECT baa FROM BatchAuftragEntity baa WHERE baa.benutzerId LIKE ?1 ORDER BY baa.anlageZeitpunkt DESC",
//						BatchAuftragEntity.class
//				);
//		lQuery.setMaxResults( 1000 );
//		lQuery.setParameter( 1, "1" );
//		final var lList = lQuery.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
