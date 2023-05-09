package org.hibernate.envers.bugs;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.Audited;

import org.junit.Test;
import jakarta.persistence.*;

/**
 * This template demonstrates how to develop a test case for Hibernate Envers, using
 * its built-in unit test framework.
 */
public class EnversUnitTestCase extends AbstractEnversTestCase {

	// Add your entities here.
	@Override
	protected Class[] getAnnotatedClasses() {
		return new Class[] {
				EntityA.class,
				EntityB.class,
				EntityC.class
		};
	}

	// If you use *.hbm.xml mappings, instead of annotations, add the mappings here.
	@Override
	protected String[] getMappings() {
		return new String[] {
//				"Foo.hbm.xml",
//				"Bar.hbm.xml"
		};
	}
	// If those mappings reside somewhere other than resources/org/hibernate/test, change this.
	@Override
	protected String getBaseForMappings() {
		return "org/hibernate/test/";
	}

	// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
	@Override
	protected void configure(Configuration configuration) {
		super.configure( configuration );

		configuration.setProperty( AvailableSettings.SHOW_SQL, Boolean.TRUE.toString() );
		configuration.setProperty( AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString() );
		//configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );
	}

	// Add your tests, using standard JUnit.
	@Test
	public void hhh123Test() throws Exception {
		final var sf = sessionFactory();

		sf.inTransaction( session -> {
			session.persist( new EntityA() );
		} );

		sf.inStatelessTransaction( session -> {
			final var entityA = session.createQuery( "from EntityA left join fetch children", EntityA.class ).getSingleResult();
			final var entityB = new EntityB();
			session.insert( entityB );
			entityA.children.add( new EntityB() );
			session.update( entityA );
		} );
	}

	@Entity(name = "EntityA")
	@Table(name = "ENTITY_A")
	static class EntityA {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "ID")
		Integer id;
		@OneToMany
		@JoinColumn(name = "ENTITY_A")
		Collection<EntityB> children = new ArrayList<>();
	}
	@Entity
	@Table(name = "ENTITY_B")
	static class EntityB {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "ID")
		Integer id;
	}
	@Entity
	@Audited
	@Table(name = "ENTITY_C")
	static class EntityC {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "ID")
		Integer id;
	}
}
