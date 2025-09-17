package org.hibernate.bugs.domain;

import jakarta.persistence.EntityManager;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.jdom2.Element;

/**
 * Diese abstrakte Klasse stellt den Kind - Klassen Methoden zur registrierung aller Attribute des FO DAO zur Verfügung.
 * Vorraussetzung ist hierfuer, dass die Kind - Klasse eine Datenklasse fuer ein
 * konkretes FO Datenobjekt ist, und per Introspector Zugriff auf die Properties gewaehrt. Die getter und
 * setter der Kind - Klasse muessen daher vom Format get[Name], set[Name] sein(bzw. is[Name]
 * und set[Name] bei boolean). Hierbei ist [name] (beachte Groß-/Kleinschreibung!) der Name des Attributs.
 * Wichtig: Eine Entity muss immer das Id-Attribut namens "oid" haben.
 */
@SuppressWarnings("serial")
public abstract class AEntity extends FODataObject {
	/** Log4J Logger. */
	// private static final Logger LOG = Logger.getLogger(AbstractEntity.class);

	private String instanceId;

	/**
	 * Parameterloser Konstruktor. Erzeugt ein FO mit den simpleAttributeMetainfo.
	 */
	public AEntity() {
//		this(true);
//		initInstanceId();
	}

//	/**
//	 * Kostruktor der ermoeglicht das Objekt zu erzeugen, ohne die Attribute zu registrieren (false)
//	 * bzw mit den kompletten AttributeMetainfos die Attribute zu registrieren.
//	 * @param pRegisterAttributes true = FO erzeugen und Attribute anhand der setter und getter der Entity registrieren;
//	 * false, wenn die Attribut-Metainformationen im Anschluss zur Instanziierung manuell ueber registerAttributes() gesetzt
//	 * werden
//	 */
//	public AEntity(final boolean pRegisterAttributes) {
//		super();
//		setName(getEntityName().toString());
//		if (pRegisterAttributes) {
//			registerAttributes(this.getClass());
//		}
//		initInstanceId();
//	}
//
//	/**
//	 * Konstruktor der aus einem FO-XML wieder ein Entity-Objekt erzeugt.
//	 * @param pXml XML Repraesentation des FODataobjects.
//	 */
//	public AEntity(final String pXml) {
//		super(pXml);
//		setName(getEntityName().toString());
//		// Callback aufrufen, der ggf. von ableitenden Entitaeten ueberschrieben wurde
//		initReverseRelations();
//		initInstanceId();
//	}
//
//	/**
//	 * Konstruktor der aus einem FO-XML wieder ein Entity-Objekt erzeugt.
//	 * @param pXml XML Repraesentation des FODataobjects.
//	 */
//	public AEntity(final byte[] pXml) {
//		super(pXml);
//		setName(getEntityName().toString());
//		// Callback aufrufen, der ggf. von ableitenden Entitaeten ueberschrieben wurde
//		initReverseRelations();
//		initInstanceId();
//	}
//
//	/**
//	 * Erzeugt ein Datenobjekt mit Fachobjektdaten aus seiner (JDom) "fo_dao"-Element Repr&auml;sentation.
//	 * @param pElement JDom Element
//	 */
//	public AEntity(final Element pElement) {
//		super(pElement);
//		setName(getEntityName().toString());
//		// Callback aufrufen, der ggf. von ableitenden Entitaeten ueberschrieben wurde
//		initReverseRelations();
//		initInstanceId();
//	}
//
//	/**
//	 * Liefert den Namen der Entity gemaess der registrierten Metainformationen.
//	 * Die Methode muss von einer konkreten Entity implementiert werden.
//	 * @return Name der Entity / des FO
//	 */
//	protected abstract DivaDataObjectType getEntityName();
//
//	/**
//	 * Callback der nach einer XML-Deserialisierung aufgerufen wird.
//	 * Dieser Callback muss von ableitenden Entities implementiert werden, um sich selbst
//	 * in den in Beziehung stehenden Entities bei Bi-direktionalen Beziehungen bekannt zu geben.
//	 */
//	protected void initReverseRelations() {
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final boolean isNew() {
//		final Object lOid;
//		try {
//			lOid = reflectGetOid();
//		} catch (final NoSuchMethodException e) {
//			// manche Entitaeten haben kein technisches Oid Attribut - fuer die funktioniert dann aber isNew() nicht
//			throw new TechnicalException("Methode isNew() nicht unterstuetzt, da Entity keine technische Oid definiert");
//		}
//		if (lOid == null) {
//			return true;
//		}
//		return lOid instanceof String && ((String) lOid).length() == 0;
//	}
//
//	/**
//	 * Prueft ob die Entity eine Methode 'getOid' besitzt und eine technische Oid definiert.
//	 * @return true, wenn Methode vorhanden ist
//	 */
//	private boolean hasOidMethod() {
//		try {
//			reflectGetOid();
//		} catch (final NoSuchMethodException e) {
//			return false;
//		}
//		return true;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final void mergeRelatedEntities(final EntityManager pEm) {
//		for (final String lRelation : getRelationNames()) {
//			final List<IFODataObject> lList = getRelatedFoDataObjects(lRelation);
//			if (lList.isEmpty()) {
//				continue;
//			}
//			final List<IFODataObject> lMergedList = new ArrayList<>(lList.size());
//			for (final IFODataObject lEntity : lList) {
//				lMergedList.add(pEm.merge(lEntity));
//			}
//			setRelatedFoDataObjects(lRelation, lMergedList);
//		}
//	}
//
//	/**
//	 * Ueberschreiben der Super-Methode um vor einer XML-Serialisierung noch die OID der Entity
//	 * vom JPA-Manager an die Entity zu uebernehmen. Im Fall von eben angelegten Entities ist die
//	 * OID erst nach der Abfrage getOid() verfuegbar. Da nicht jede Entity zwingend eine technische
//	 * OID hat, erfolgt der Aufruf generisch (per Reflection).
//	 * @return Entity als FO-XML
//	 */
//	@Override
//	public final String toXML() {
//		ensureOid();
//		return super.toXML();
//	}
//
//	/**
//	 * Analog toXML().
//	 * @return Entity als FO-XML Zip
//	 */
//	@Override
//	public final byte[] toXMLZip() {
//		ensureOid();
//		return super.toXMLZip();
//	}
//
//	/**
//	 * Ermittelt per Introspector die Attribute des FO Datenobjektes pClass,
//	 * d.h. die properties von pClass, die per getter und setter in pClass definiert sind.
//	 * Die ermittelten Attribute werden am Datenobjekt registriert.
//	 * @param pClass Bean, in der die Attribute des Datenobjektes per getter und setter definiert sind.
//	 */
//	protected final void registerAttributes(final Class<?> pClass) {
//		try {
//			// boolean lHasOidAttribute = false;
//			// holen der BeanInfo der aktuellen klasse.
//			// 2. Parameter verhindert, dass properties der Superklasse aufgelistet werden.
//			final BeanInfo lBeanInfo = Introspector.getBeanInfo(pClass, AEntity.class);
//			final PropertyDescriptor[] lPropertyDescriptors = lBeanInfo.getPropertyDescriptors();
//			final SimpleAttributeMetaInfo[] lSimpleAttributeMetaInfos = new SimpleAttributeMetaInfo[lPropertyDescriptors.length];
//			Integer i = 0;
//
//			for (final PropertyDescriptor lCurrentPropertyDescriptor : lPropertyDescriptors) {
//				final String lName = lCurrentPropertyDescriptor.getName();
//				final Class<?> lType = lCurrentPropertyDescriptor.getPropertyType();
//				final String lTypeName;
//				if (lType.isArray()) {
//					// Sonderbehandlung von Array-Returns
//					final String lCanonicalName = lType.getCanonicalName(); // liefert bspw. "java.lang.Long[]"
//					lTypeName = lCanonicalName.substring(0, lCanonicalName.indexOf('['));
//				} else {
//					lTypeName = lType.getName();
//				}
//				final DivaJavaType lJavaType = getValidPropertyType(lTypeName);
//				// lJavaType kann null sein, wenn die Entity bspw. Beziehungs-Getter mit List-Typen hat
//				if (lJavaType != null) {
//					lSimpleAttributeMetaInfos[i] = new SimpleAttributeMetaInfo(lName, lJavaType, "oid".equals(lName));
//					registerAttribute(lSimpleAttributeMetaInfos[i]);
//					// if (lSimpleAttributeMetaInfos[i].isIdAttribute()) {
//					// lHasOidAttribute = true;
//					// }
//					i++;
//				}
//			}
//
//			// if (!lHasOidAttribute) {
//			// LOG.warn("Entity ohne oid-Attribut: name=[" + getClass().getName() + "]");
//			// }
//		} catch (final IntrospectionException e) {
//			throw new TechnicalException(e.getMessage(), e);
//		}
//	}
//
//	/**
//	 * Liefert den zu pTypeName vom FO-Typsystem unterstuetzten Java-Type oder null, wenn der Typ ein
//	 * nicht unterstuetzter Java-Attributtyp ist.
//	 * @param pTypeName Java-Typname
//	 * @return Java-Type oder null
//	 */
//	private DivaJavaType getValidPropertyType(final String pTypeName) {
//		try {
//			return DivaJavaType.getJavaTypeFor(pTypeName);
//		} catch (final TechnicalException e) {
//			return null;
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final void ensureOid() {
//		// getOid() an dieser Entity aufrufen
//		try {
//			reflectGetOid();
//		} catch (final NoSuchMethodException e) {
//			// Entity ohne getOid() Methode - koennen wir ignorieren, da die Entity einen fachlichen Schluessel als PK hat
//		}
//
//		// abhaengige Entities aller Beziehungen
//		for (final String lRelationName : getRelationNames()) {
//			for (final IFODataObject lFo : getRelatedFoDataObjects(lRelationName)) {
//				if (lFo instanceof IServerEntity) {
//					((IServerEntity) lFo).ensureOid();
//				}
//			}
//		}
//	}
//
//	/**
//	 * Aufruf der Methode getOid() per Reflection.
//	 * @return Oid-Value oder null
//	 * @throws NoSuchMethodException wenn die Methode getOid() nicht existiert
//	 */
//	private Object reflectGetOid() throws NoSuchMethodException {
//		try {
//			final Method lMethod = this.getClass().getMethod("getOid");
//			return lMethod.invoke(this);
//		} catch (final NoSuchMethodException e) {
//			throw e;
//		} catch (final Exception e) {
//			throw new TechnicalException(e.getMessage(), e);
//		}
//	}
//
//	/**
//	 * erzeugt eine Instanz-Id fuer das Objekt.
//	 */
//	private void initInstanceId() {
//		// erzeugt eine Instanz-Id fuer das Objekt
//		this.instanceId = UUID.randomUUID().toString();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final String getInstanceId() {
//		return this.instanceId;
//	}
//
//	/**
//	 * Entfernt ein mit dieser Entity in Beziehung stehende Entity.
//	 * @param pRelationName Name der Beziehung
//	 * @param pInstanceId Instanz-Id der aus der Beziehung zu entfernenden Entity
//	 */
//	private void removeRelatedEntityWithInstanceId(final String pRelationName, final String pInstanceId) {
//		if (containsRelatedEntityWithInstanceId(pRelationName, pInstanceId)) {
//			// getFoRelationMap().get(pRelationName).removeFo(pId);
//			final List<IFODataObject> lRelatedFoDataObjects = getRelatedFoDataObjects(pRelationName);
//			for (int i = 0; i < lRelatedFoDataObjects.size(); i++) {
//				final IFODataObject lFo = lRelatedFoDataObjects.get(i);
//				if (pInstanceId.equals(((AEntity) lFo).getInstanceId())) {
//					lRelatedFoDataObjects.remove(i);
//					break;
//				}
//			}
//		}
//	}
//
//	/**
//	 * Liefert eine Aussage, ob ein Entity fuer eine Beziehung mit dieser Entity in Beziehung steht.
//	 * @param pRelationName Name der Beziehung
//	 * @param pInstanceId Instanz-Id der zu ueberpruefenden Entity
//	 * @return true, wenn die Entity mit dieser Entity fuer die Beziehung pRelationName in Beziehung steht
//	 */
//	private boolean containsRelatedEntityWithInstanceId(final String pRelationName, final String pInstanceId) {
//		if (super.getFoRelationMap().containsKey(pRelationName)) {
//			// return getFoRelationMap().get(pRelationName).containsFo(pInstanceId);
//			final List<IFODataObject> lRelatedFoDataObjects = getRelatedFoDataObjects(pRelationName);
//			for (final IFODataObject lFo : lRelatedFoDataObjects) {
//				if (pInstanceId.equals(((AEntity) lFo).getInstanceId())) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final void removeRelatedEntity(final String pRelationName, final IEntity pEntity) {
//		// Zunaechst pruefen ob die Entity ueberhaupt eine Methode zum Auslesen der Oid besitzt und diese gefuellt ist
//		if (((AEntity) pEntity).hasOidMethod() && !pEntity.isNew()) {
//			// Wenn ja, ueber Oid (Methode FODataObjekt) suchen und entfernen
//			super.removeRelatedFoDataObject(pRelationName, ((IFODataObject) pEntity).getId());
//		}
//		// Wenn die Entity noch keine Oid besitzt bzw. keine Methode zum Auslesen der Oid besitzt,
//		// ueber Instanz-Id suchen und entfernen
//		if (pEntity.getInstanceId() == null || pEntity.getInstanceId().length() < 32) {
//			throw new TechnicalException("Die uebergebene Entity besitzt weder eine gueltige Oid noch Instanz-Id.");
//		}
//		removeRelatedEntityWithInstanceId(pRelationName, pEntity.getInstanceId());
//	}

	// /**
	// * Comment.
	// */
	// @Transient
	// @PrePersist
	// public void createOid() {
	// if (getClass().isAnnotationPresent(PkUuidHex.class)) {
	// if (LOG.isDebugEnabled()) {
	// LOG.debug("mit @PkUuidHex annotierte Entity erkannt: instance-id=[" + getInstanceId() + "]");
	// }
	// try {
	// setAttributeValue("oid", getInstanceId());
	// }
	// catch (TechnicalException e) {
	// LOG.warn("mit @PkUuidHex annotierte Entity kennt Attribut oid nicht: entity=[" + getClass().getName() + "] msg=[" + e.getMessage() +
	// "]");
	// }
	// }
	// }
}