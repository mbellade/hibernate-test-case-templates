/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.bugs;

import java.util.List;

import org.hibernate.bugs.domain.NiBeleg;
import org.hibernate.bugs.domain.NiNutzungsinformation;
import org.hibernate.bugs.domain.NiOrtungsvorgang;
import org.hibernate.bugs.domain.NpAufenthaltsabschnittOV;
import org.hibernate.cfg.AvailableSettings;

import org.hibernate.testing.bytecode.enhancement.CustomEnhancementContext;
import org.hibernate.testing.bytecode.enhancement.extension.BytecodeEnhanced;
import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.ServiceRegistry;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.hibernate.testing.orm.junit.Setting;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import jakarta.persistence.TypedQuery;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using its built-in unit test framework.
 * <p>
 * What's even better?  Fork hibernate-orm itself, add your test case directly to a module's unit tests, then
 * submit it as a PR!
 */
@DomainModel(annotatedClasses = {
		NiBeleg.class,
		NiNutzungsinformation.class,
		NiOrtungsvorgang.class,
		NpAufenthaltsabschnittOV.class
})
@ServiceRegistry(
		// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
		settings = {
				// For your own convenience to see generated queries:
				@Setting(name = AvailableSettings.SHOW_SQL, value = "true"),
				@Setting(name = AvailableSettings.FORMAT_SQL, value = "true"),

				@Setting(name = AvailableSettings.USE_SECOND_LEVEL_CACHE, value = "true"),
				@Setting(name = AvailableSettings.USE_MINIMAL_PUTS, value = "false"),

				@Setting(name = AvailableSettings.USE_QUERY_CACHE, value = "true"),
				@Setting(name = AvailableSettings.STATEMENT_BATCH_SIZE, value = "100"),
				@Setting(name = AvailableSettings.BATCH_VERSIONED_DATA, value = "true"),
				@Setting(name = AvailableSettings.STATEMENT_FETCH_SIZE, value = "50"),
				@Setting(name = AvailableSettings.JPA_PROXY_COMPLIANCE, value = "true"),
		})
@SessionFactory
@BytecodeEnhanced//(runNotEnhancedAsWell = true)
@CustomEnhancementContext(QuarkusLikeEnhancementContext.class)
class EAPSUP2014TestCase {

	@AfterAll
	public void cleanUp(SessionFactoryScope scope) {
		scope.getSessionFactory().getSchemaManager().truncateMappedObjects();
	}

	// Add your tests, using standard JUnit.
	@Test
	void hhh123Test(SessionFactoryScope scope) throws Exception {
//		final var niort1 = scope.fromTransaction( session -> {
//			return session.find( NiOrtungsvorgang.class, 1L );
//		} );
		scope.inTransaction( session -> {
			final var niort1 = session.find( NiOrtungsvorgang.class, 1L );
			final var niort2 = session.find( NiOrtungsvorgang.class, 2L );
			final var niort3 = session.find( NiOrtungsvorgang.class, 3L );

			final var e1 = new NpAufenthaltsabschnittOV();
//			e1.setOrtungsvorgang( session.merge( niort1 ) );
			e1.setOrtungsvorgang( niort1 );
			session.persist( e1 );

			final var e2 = new NpAufenthaltsabschnittOV();
			e2.setOrtungsvorgang( session.merge( niort2 ) );
			session.persist( e2 );

			final var e3 = new NpAufenthaltsabschnittOV();
			e3.setOrtungsvorgang( session.merge( niort3 ) );
			session.persist( e3 );

			session.flush();
			session.clear();

			final var npAufenthaltsabschnittOV = session.find( NpAufenthaltsabschnittOV.class, e1.getId() );

			String sqlString = "SELECT distinct n FROM NpAufenthaltsabschnittOV n "
					+ "LEFT OUTER JOIN FETCH n.ortungsvorgang ov "
					+ "LEFT OUTER JOIN FETCH ov.nutzungsinformation ni "
					+ "LEFT OUTER JOIN FETCH ni.niBelege be "
//					" WHERE n.tens = :tens "
//					+ "AND n.zeitpunktBeginn < :zeitpunktEnde AND n.zeitpunktEnde > :zeitpunktBeginn "
					+ "ORDER BY n.zeitpunktBeginn ASC, n.zeitpunktEnde ASC, n.id ASC";

			final var query = session.createQuery(
					sqlString,
					NpAufenthaltsabschnittOV.class
			);

			final var resultList = query.getResultList();

			System.out.println( "Found " + resultList.size() + " entries" );
		} );
	}
}
