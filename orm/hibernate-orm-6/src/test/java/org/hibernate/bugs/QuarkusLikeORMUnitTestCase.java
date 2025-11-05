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

import java.util.Date;
import java.util.List;

import org.hibernate.bugs.domain.NiBeleg;
import org.hibernate.bugs.domain.NiNutzungsinformation;
import org.hibernate.bugs.domain.NiOrtungsvorgang;
import org.hibernate.bugs.domain.NpAufenthaltsabschnittOV;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.query.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;

import org.hibernate.testing.bytecode.enhancement.CustomEnhancementContext;
import org.hibernate.testing.bytecode.enhancement.extension.BytecodeEnhanced;
import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.ServiceRegistry;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.hibernate.testing.orm.junit.Setting;
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
				// @Setting( name = AvailableSettings.GENERATE_STATISTICS, value = "true" ),

				// Other settings that will make your test case run under similar configuration that Quarkus is using by default:
//				@Setting(name = AvailableSettings.PREFERRED_POOLED_OPTIMIZER, value = "pooled-lo"),
//				@Setting(name = AvailableSettings.DEFAULT_BATCH_FETCH_SIZE, value = "16"),
//				@Setting(name = AvailableSettings.BATCH_FETCH_STYLE, value = "PADDED"),
//				@Setting(name = AvailableSettings.QUERY_PLAN_CACHE_MAX_SIZE, value = "2048"),
//				@Setting(name = AvailableSettings.DEFAULT_NULL_ORDERING, value = "none"),
//				@Setting(name = AvailableSettings.IN_CLAUSE_PARAMETER_PADDING, value = "true"),
//				@Setting(name = AvailableSettings.SEQUENCE_INCREMENT_SIZE_MISMATCH_STRATEGY, value = "none"),
//				@Setting(name = AvailableSettings.ORDER_UPDATES, value = "true"),

				// Add your own settings that are a part of your quarkus configuration:
				// @Setting( name = AvailableSettings.SOME_CONFIGURATION_PROPERTY, value = "SOME_VALUE" ),
		})
@SessionFactory
@BytecodeEnhanced(runNotEnhancedAsWell = true)
@CustomEnhancementContext(QuarkusLikeEnhancementContext.class)
class QuarkusLikeORMUnitTestCase {

	// Add your tests, using standard JUnit.
	@Test
	void hhh123Test(SessionFactoryScope scope) throws Exception {
		scope.inTransaction( session -> {
			String sqlString = "SELECT distinct n FROM NpAufenthaltsabschnittOV n "
					+ "LEFT OUTER JOIN FETCH n.ortungsvorgang ov "
					+ "LEFT OUTER JOIN FETCH ov.nutzungsinformation ni "
					+ "LEFT OUTER JOIN FETCH ni.niBelege be WHERE n.tens = :tens "
					+ "AND n.zeitpunktBeginn < :zeitpunktEnde AND n.zeitpunktEnde > :zeitpunktBeginn "
					+ "ORDER BY n.zeitpunktBeginn ASC, n.zeitpunktEnde ASC, n.id ASC";

			TypedQuery<NpAufenthaltsabschnittOV> query = session.createQuery(
					sqlString,
					NpAufenthaltsabschnittOV.class
			);
			query.setParameter( "tens", "test" );
			query.setParameter(
					"zeitpunktBeginn",
					new TypedParameterValue<Date>( StandardBasicTypes.TIMESTAMP, new Date() )
			);
			query.setParameter(
					"zeitpunktEnde",
					new TypedParameterValue<Date>( StandardBasicTypes.TIMESTAMP, new Date() )
			);

			List<NpAufenthaltsabschnittOV> resultList = query.getResultList();
		} );
	}
}
