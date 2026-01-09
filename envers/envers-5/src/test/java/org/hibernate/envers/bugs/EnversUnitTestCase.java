/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.envers.bugs;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This template demonstrates how to develop a test case for Hibernate Envers, using
 * its built-in unit test framework.
 */
public class EnversUnitTestCase extends AbstractEnversTestCase {

	// Add your entities here.
	@Override
	protected Class[] getAnnotatedClasses() {
		return new Class[] {
				Parent.class,
				Child.class,
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


	@Entity(name = "Child")
	@Audited
	public static class Child {
		@Id
		@GeneratedValue
		private Long id;

		private String name;

		public Child() {
		}

		public Child(String name) {
			this.name = name;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	@Entity(name = "Parent")
	@Audited
	public static class Parent {
		@Id
		@GeneratedValue
		private Long id;

		private String content;

		@ManyToOne(fetch = FetchType.LAZY)
		@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
		private Child child;

		public Parent() {
		}

		public Parent(String content, Child child) {
			this.content = content;
			this.child = child;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public Child getChild() {
			return child;
		}

		public void setChild(Child child) {
			this.child = child;
		}
	}

	private Long childId;
	private Long parentId;

	@Before
	public void initData() {
		// Revision 1: Create child and parent
		inTransaction( em -> {
			final Child child = new Child( "Child 1" );
			em.persist( child );

			final Parent parent = new Parent( "Initial content", child );
			em.persist( parent );


			this.childId = child.getId();
			this.parentId = parent.getId();
		} );

		// Revision 2: Update parent content
		inTransaction( em -> {
			final Parent parent = em.find( Parent.class, this.parentId );
			parent.setContent( "Updated content" );
		} );

		// Revision 3: Update child name (should not create audit record for parent)
		inTransaction( em -> {
			final Child child = em.find( Child.class, this.childId );
			child.setName( "Child 1 Updated" );
		} );
	}

	@Test
	public void testLoadParentAtRevision1() {
		inTransaction( session -> {
			final Parent parent = AuditReaderFactory.get( session ).find( Parent.class, this.parentId, 1 );

			assertNotNull( parent );
			assertEquals( "Initial content", parent.getContent() );
			assertNotNull( parent.getChild() );
			assertEquals( this.childId, parent.getChild().getId() );
			// Child should be loaded from current table, so it should have the updated name
			assertEquals( "Child 1 Updated", parent.getChild().getName() );
		} );
	}

	@Test
	public void testLoadParentAtRevision2() {
		inTransaction( session -> {
			final Parent parent = AuditReaderFactory.get( session ).find( Parent.class, this.parentId, 2 );

			assertNotNull( parent );
			assertEquals( "Updated content", parent.getContent() );
			assertNotNull( parent.getChild() );
			assertEquals( childId, parent.getChild().getId() );
			// Child should be loaded from current table
			assertEquals( "Child 1 Updated", parent.getChild().getName() );
		} );
	}

	@Test
	public void testQueryParentRevisions() {
		inTransaction( session -> {
			final List<Number> revisions = AuditReaderFactory.get( session ).getRevisions(
					Parent.class,
					this.parentId
			);

			// Parent should have 2 revisions (creation and update)
			assertEquals( 2, revisions.size() );
		} );
	}

	@Test
	public void testQueryChildRevisions() {
		inTransaction( session -> {
			final List<Number> revisions = AuditReaderFactory.get( session ).getRevisions( Child.class, this.childId );

			// Child should have 2 revisions (creation and update)
			assertEquals( 2, revisions.size() );
		} );
	}
}

