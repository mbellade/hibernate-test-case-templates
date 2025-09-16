package org.hibernate.bugs;

import org.hibernate.SessionFactory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@BeforeEach
	void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
		final var entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.persist( new City( "1", "City 1", 100 ) );
		entityManager.persist( new City( "2", "City 2", 200 ) );
		entityManager.persist( new City( "3", "City 3", 300 ) );
		entityManager.persist( new City( "4", "City 4", 400 ) );
		entityManager.persist( new City( "5", "City 5", 500 ) );
		entityManager.persist( new City( "6", "City 6", 600 ) );
		entityManager.persist( new City( "7", "City 7", 700 ) );
		entityManager.persist( new City( "8", "City 8", 800 ) );
		entityManager.persist( new City( "9", "City 9", 900 ) );

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
		final var sf = entityManagerFactory.unwrap( SessionFactory.class );
		final var statelessSession = sf.openStatelessSession();

		PageRequest request = PageRequest.ofSize( 3 );
		Order<City> order = Order.by( _City.population.desc() );

		Cities cities = new Cities_( statelessSession );

//		Page<City> page = cities.findAll( request, order );

		// Page assertions
//		assertEquals( 9, page.totalElements() );
//		assertEquals( 3, page.totalPages() );

		// Order assertion
		Page<City> page;
		do {
			page = cities.findAll( request, order );
			for ( City city : page ) {
				System.out.println( "City: " + city );
			}
		} while ( ( request = page.hasNext() ? page.nextPageRequest() : null ) != null );

		// Completion assertion
//		assertEquals("Incomplete set of results during pagination", 0, expectedPopulationOrder.size());

		statelessSession.close();
	}
}
