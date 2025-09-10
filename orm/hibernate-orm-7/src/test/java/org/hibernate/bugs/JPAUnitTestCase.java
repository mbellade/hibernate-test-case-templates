package org.hibernate.bugs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Persistence;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaQuery;

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

		final var cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> query = cb.createTupleQuery();
		var root = query.from( Ancestor.class );
		var dscCRoot = cb.treat( root, DescendantTak.class );

		query.select( cb.tuple(
				root.get( JPAUnitTestCase_.Ancestor_.id ).alias( "id" ),
				cb.coalesce(
						dscCRoot.get( JPAUnitTestCase_.DescendantTak_.subtitle ),
						dscCRoot.get( JPAUnitTestCase_.DescendantTak_.title )
				).alias( "description" )
		) ).orderBy(
				cb.asc( root.get( JPAUnitTestCase_.Ancestor_.id ) )
		);

		final var resultList = entityManager.createQuery( query ).getResultList();
		assert resultList != null;

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Entity
	@Table(schema = "public", name = "t_ancestor")
	@Inheritance(strategy = InheritanceType.JOINED)
	@DiscriminatorColumn(name = "def_type_id")
	static abstract class Ancestor {
		@Id
		private Integer id;
	}

	@Entity
	@DiscriminatorValue("A")
	@Table(schema = "public", name = "t_descendant_a")
	@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
	static class DescendantA extends Ancestor {
		private String title;
		private String subtitle;
	}

	@Entity
	@Table(schema = "public", name = "t_descendant_b")
	@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
	static abstract class DescendantB extends Ancestor {
	}

	@Entity
	@DiscriminatorValue("C")
	@Table(schema = "public", name = "t_descendant_c")
	@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
	static class DescendantTak extends DescendantB {
		private String title;
		private String subtitle;
	}

	@Entity
	@DiscriminatorValue("D")
	@Table(schema = "public", name = "t_descendant_d")
	@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
	public class DescendantD extends DescendantB {
		private String subtitle;
	}
}
