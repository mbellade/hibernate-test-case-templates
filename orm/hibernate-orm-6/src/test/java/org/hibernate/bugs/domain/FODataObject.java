package org.hibernate.bugs.domain;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.Namespace;

/**
 * Implementiert ein Fachdatenobjekt.
 * <p>
 * Die Daten werden in einem JDOM-Document vorgehalten und ausschliesslich dort verwaltet.
 * </p>
 */
@SuppressWarnings("serial")
public class FODataObject extends ATransferDataObject {
	/**Fehler-Meldung */
	private static final String EXCEPTION_MESSAGE_ATTR_NAME = "Fuer diesen Attributnamen konnte kein Attribut ermittelt werden: ";
	/**Fehler-Meldung Java Typ*/
	private static final String EXCEPTION_MESSAGE_JAVA_TYPE = "pValue ist keine Instanz des in AttributeMetaInfo gesetzten Java-Typen: ";
	/**XML-Element*/
	private static final String XML_ELEMENT_ROOT = "fo_dao";
	/**XML-Element*/
	private static final String XML_ELEMENT_NAME = "name";
	/**XML-Element*/
	private static final String XML_ELEMENT_ATTRIBUTES = "attributes";
	/**XML-Element*/
	private static final String XML_ELEMENT_ATTRIBUTE = "attribute";
	/**XML-Element*/
	private static final String XML_ELEMENT_VALUES = "values";
	/**XML-Element*/
	private static final String XML_ELEMENT_VALUE = "value";
	/**XML-Element*/
	private static final String XML_ELEMENT_META = "meta";
	/**XML-Element*/
	private static final String XML_ELEMENT_SUPERIOR = "superior";
	/**XML-Element*/
	private static final String XML_ELEMENT_ATTR_NAME = "attr_name";
	/**XML-Element*/
	private static final String XML_ELEMENT_MV_ATTR_NAME = "mv_attr_name";
	/**XML-Element*/
	private static final String XML_ELEMENT_TYPE = "type";
	/**XML-Element*/
	private static final String XML_ELEMENT_SCHLVERZNAME = "schlverz_name";
	/**XML-Element*/
	private static final String XML_ELEMENT_TITLE = "title";
	/**XML-Element*/
	private static final String XML_ELEMENT_RVARCHIV = "rvarchiv";
	/**XML-Element*/
	private static final String XML_ELEMENT_NULLABLE = "nullable";
	/**XML-Element*/
	private static final String XML_ELEMENT_READONLY = "readonly";
	/**XML-Element*/
	private static final String XML_ELEMENT_MINLEN = "minlen";
	/**XML-Element*/
	private static final String XML_ELEMENT_MAXLEN = "maxlen";
	/**XML-Element*/
	private static final String XML_ELEMENT_UPPERCASE = "uppercase";
	/**XML-Element*/
	private static final String XML_ELEMENT_LOWERCASE = "lowercase";
	/**XML-Element*/
	private static final String XML_ELEMENT_ATTR_DEFAULT = "default";
	/**XML-Element*/
	private static final String XML_ATTRIBUTE_TYPE = "type";
	/**XML-Element*/
	private static final String XML_ELEMENT_PROPERTIES = "properties";
	/**XML-Element*/
	private static final String XML_ELEMENT_PROPERTY = "property";
	/**XML-Element*/
	private static final String XML_ATTRIBUTE_KEY = "key";
	/**XML-Element*/
	private static final String XML_ELEM_RELATION_LIST = "relations";
	/**XML-Element*/
	private static final String XML_ELEM_RELATION = "relation";
	/**XML-Element*/
	private static final String XML_ELEM_REL_FO_IMPL_CLASS = "related_fo_impl_class";
	/**XML-Element*/
	private static final String XML_ELEM_RELATED_FO_LIST = "related_fo_list";
	/**XML-Element*/
	private static final String XML_ELEM_RELATED_FO = "related_fo_dao";

	/** Darstellung der FO-Attributdaten als Properties-Objekt (lazy initialized). */
	private Properties attributeValueProps;
	/** interner Basiszaehler fuer Veraenderungen (durch Verwendung von System.currentTimeMillis() wird ein vom
	 * ueber den Service neu geladenes RS als geaendert erkannt). */
	private final long modVersionBase = System.currentTimeMillis();
	/** interner Zaehler fuer Veraenderungen. */
	private long modVersion;
	/** Map mit Beziehungsinfos aller in Beziehung stehenden FO
	 * (lazy initialized) (key=Beziehungsname, value=Beziehungsinfo (innere Klasse). */
//	private Map<String, FoRelationInfo> foRelationMap;
	/** ObjectFactory zur Erzeugung in Beziehung stehender FOs (lazy initialized). */
//	private transient GenericObjectFactory genericObjectFactoy;

	/** Map zum Cachen der Dom-Objekte von Attributwerten. */
	private Map<String, Element> domValueMap;

	/** Cache für die ID */
	private Object id;

	/**
	 * Erzeugt ein leeres Datenobjekt zur Aufnahme von Fachobjektdaten.
	 */
	public FODataObject() {
		super();
		initEmptyDocument(XML_ELEMENT_ROOT);
	}

	/**
	 * Erzeugt ein Datenobjekt mit Fachobjektdaten aus einem XML-String.
	 * @param pXML XML-String
	 */
	public FODataObject(final String pXML) {
		super(pXML);
//		initRelatedFoDataObjectsFromDocument();
	}
//
//	/**
//	 * Erzeugt ein Datenobjekt mit Fachobjektdaten aus einem XML-Stream.
//	 * @param pXML XML-Stream
//	 */
//	public FODataObject(final InputStream pXML) {
//		super(pXML);
//		initRelatedFoDataObjectsFromDocument();
//	}
//
//	/**
//	 * Erzeugt ein Datenobjekt mit Fachobjektdaten aus einem ZIP-komprimierten XML-String.
//	 * @param pXMLZip komprimierter XML-String
//	 */
//	public FODataObject(final byte[] pXMLZip) {
//		super(pXMLZip);
//		initRelatedFoDataObjectsFromDocument();
//	}
//
//	/**
//	 * Erzeugt ein Datenobjekt mit Fachobjektdaten aus seiner (JDom) "fo_dao"-Element Repr&auml;sentation.
//	 * @param pElement JDom Element
//	 */
//	public FODataObject(final Element pElement) {
//		super();
//		super.initEmptyDocument(XML_ELEMENT_ROOT);
//		getDocument().getRootElement().addContent(pElement.cloneContent());
//		initRelatedFoDataObjectsFromDocument();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final String[] getAttributeNames() {
//		final Set<String> lAttributes = getDomValueMap().keySet();
//		final String[] lAttributeNames = new String[getDomValueMap().size()];
//		return lAttributes.toArray(lAttributeNames);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final SimpleAttributeMetaInfo getSimpleAttributeMetaInfoFor(final String pName) {
//		final Element lAttributeElem = getDomValueMap().get(pName);
//		final Namespace lNamespace = getNameSpace();
//		if (lAttributeElem != null) {
//			final String lJavaType = lAttributeElem.getChild(XML_ELEMENT_META, lNamespace).getChildTextTrim(XML_ELEMENT_TYPE, lNamespace);
//			final Attribute lTypeAttribute = lAttributeElem.getAttribute("type");
//			final boolean lIsId = lTypeAttribute != null && lTypeAttribute.getValue().equals(ID.toString());
//			return new SimpleAttributeMetaInfo(pName, DivaJavaType.getJavaTypeFor(lJavaType), lIsId);
//		}
//		return null;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final SimpleAttributeMetaInfo[] getSimpleMetaInfosFor(final String[] pNames) {
//		final SimpleAttributeMetaInfo[] lAttributeMetaInfo = new SimpleAttributeMetaInfo[pNames.length];
//		for (int i = 0; i < pNames.length; i++) {
//			lAttributeMetaInfo[i] = getSimpleAttributeMetaInfoFor(pNames[i]);
//		}
//		return lAttributeMetaInfo;
//	}

	// /**
	// * {@inheritDoc}
	// */
	// @SuppressWarnings("unchecked")
	// @Deprecated
	// public final AttributeMetaInfo getMetaInfoFor(final String pName) {
	//
	// AttributeMetaInfo lAttributeMetaInfo;
	// List<Element> lAttributes;
	// Element lMeta;
	// List<Element> lEAttrNames;
	// String[] lSAttrNames;
	// Attribute lJDomAttributeType;
	// String lAttributeName;
	// String lMvAttributeName;
	// String lType;
	// String lSchlverzName;
	// String lTitle;
	// boolean lRvArchivAttribute;
	// boolean lNullable;
	// boolean lReadOnly;
	// int lMinLen;
	// int lMaxLen;
	// boolean lUpperCase;
	// boolean lLowerCase;
	// String lDefault;
	// DivaAttributeType lAttributeType;
	//
	// lAttributes = getDocument().getRootElement().getChild(XML_ELEMENT_ATTRIBUTES, getNameSpace()).getChildren();
	//
	// for (int i = 0; i < lAttributes.size(); i++) {
	// lAttributeName = lAttributes.get(i).getChildTextTrim(XML_ELEMENT_NAME, getNameSpace());
	// if (lAttributeName.equals(pName)) {
	// lMeta = lAttributes.get(i).getChild(XML_ELEMENT_META, getNameSpace());
	// lMvAttributeName = lMeta.getChildTextTrim(XML_ELEMENT_MV_ATTR_NAME, getNameSpace());
	// lType = lMeta.getChildTextTrim(XML_ELEMENT_TYPE, getNameSpace());
	// lSchlverzName = lMeta.getChildTextTrim(XML_ELEMENT_SCHLVERZNAME, getNameSpace());
	// lTitle = lMeta.getChildTextTrim(XML_ELEMENT_TITLE, getNameSpace());
	// lRvArchivAttribute = Boolean.parseBoolean(lMeta.getChildTextTrim(XML_ELEMENT_RVARCHIV, getNameSpace()));
	// lNullable = Boolean.parseBoolean((lMeta.getChildTextTrim(XML_ELEMENT_NULLABLE, getNameSpace())));
	// lReadOnly = Boolean.parseBoolean(lMeta.getChildTextTrim(XML_ELEMENT_READONLY, getNameSpace()));
	// lMinLen = Integer.parseInt(lMeta.getChildTextTrim(XML_ELEMENT_MINLEN, getNameSpace()));
	// lMaxLen = Integer.parseInt(lMeta.getChildTextTrim(XML_ELEMENT_MAXLEN, getNameSpace()));
	// lUpperCase = Boolean.parseBoolean(lMeta.getChildTextTrim(XML_ELEMENT_UPPERCASE, getNameSpace()));
	// lLowerCase = Boolean.parseBoolean(lMeta.getChildTextTrim(XML_ELEMENT_LOWERCASE, getNameSpace()));
	// lDefault = lMeta.getChildTextTrim(XML_ELEMENT_ATTR_DEFAULT, getNameSpace());
	// lJDomAttributeType = lAttributes.get(i).getAttribute(XML_ATTRIBUTE_TYPE);
	//
	// lAttributeType = STANDARD;
	// if (lJDomAttributeType != null) {
	// if (lJDomAttributeType.getValue().equals(KEY.toString())) {
	// lAttributeType = KEY;
	// }
	// if (lJDomAttributeType.getValue().equals(ID.toString())) {
	// lAttributeType = ID;
	// }
	// }
	//
	// lAttributeMetaInfo = new AttributeMetaInfo(pName, lMvAttributeName, lAttributeType, DivaJavaType.getJavaTypeFor(lType),
	// lSchlverzName, lTitle, lRvArchivAttribute, lNullable, lReadOnly, lMinLen, lMaxLen, lUpperCase, lLowerCase, lDefault);
	//
	// lEAttrNames = lMeta.getChild(XML_ELEMENT_SUPERIOR, getNameSpace()).getChildren(XML_ELEMENT_ATTR_NAME, getNameSpace());
	//
	// if (lEAttrNames != null) {
	// lSAttrNames = new String[lEAttrNames.size()];
	// for (int j = 0; j < lSAttrNames.length; j++) {
	// lSAttrNames[j] = lEAttrNames.get(j).getTextTrim();
	// }
	// lAttributeMetaInfo.setSuperiorAttributes(lSAttrNames);
	// }
	//
	// return lAttributeMetaInfo;
	// }
	// }
	//
	// return null;
	// }
	//
	// /**
	// * {@inheritDoc}
	// */
	// @Deprecated
	// public AttributeMetaInfo[] getMetaInfosFor(String[] pNames) {
	// AttributeMetaInfo[] lAttributeMetaInfo;
	// lAttributeMetaInfo = new AttributeMetaInfo[pNames.length];
	// for (int i = 0; i < pNames.length; i++) {
	// lAttributeMetaInfo[i] = getMetaInfoFor(pNames[i]);
	// }
	// return lAttributeMetaInfo;
	// }

//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final String getName() {
//		return getDocument().getRootElement().getChildText(XML_ELEMENT_NAME, getNameSpace());
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public Object getId() {
//		if (this.id == null) {
//			for (final Entry<String, Element> lEntry : getDomValueMap().entrySet()) {
//				final Attribute lTypeAttribute = lEntry.getValue().getAttribute("type");
//				if (lTypeAttribute != null && lTypeAttribute.getValue().equals(ID.toString())) {
//					this.id = getAttributeValue(lEntry.getKey());
//					break;
//				}
//			}
//		}
//		return this.id;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
	public Object getAttributeValue(final String pName) {
//		final Element lAttrElem = getAttributeElem(pName);
//		if (lAttrElem == null) {
//			throw new TechnicalException(
//					EXCEPTION_MESSAGE_ATTR_NAME + "attrname=[" + pName + "] foname=[" + getName() + "] op=[getAttributeValue]");
//		}
//		final Namespace lNamespace = getNameSpace();
//		final String lType = lAttrElem.getChild(XML_ELEMENT_META, lNamespace).getChildTextTrim(XML_ELEMENT_TYPE, lNamespace);
//		final Element lValueElem = lAttrElem.getChild(XML_ELEMENT_VALUES, lNamespace).getChild(XML_ELEMENT_VALUE, lNamespace);
//		if (lValueElem == null) {
//			return null;
//		} else {
//			try {
//				return TypeFactory.getObjectFor(DivaJavaType.getJavaTypeFor(lType), lValueElem.getTextTrim());
//			} catch (final FactoryException e) {
//				throw new TechnicalException(
//						"Fehler bei der String->Object-Konvertierung: name=[" + pName + "] value=[" + lValueElem.getTextTrim() + "]", e);
//			}
//		}
		return attributes.get(pName);
	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final Object[] getAttributeValues(final String pName) {
//		final Element lAttrElem = getAttributeElem(pName);
//		if (lAttrElem == null) {
//			throw new TechnicalException(
//					EXCEPTION_MESSAGE_ATTR_NAME + "attrname=[" + pName + "] foname=[" + getName() + "] op=[getAttributeValue]");
//		}
//		final Namespace lNamespace = getNameSpace();
//
//		final String lType = lAttrElem.getChild(XML_ELEMENT_META, lNamespace).getChildTextTrim(XML_ELEMENT_TYPE, lNamespace);
//		final Class<?> lArrayClass;
//		try {
//			lArrayClass = Class.forName(lType);
//		} catch (final ClassNotFoundException e) {
//			throw new TechnicalException("ClassNotFoundException bei Erzeugung der Array-Class: fo=[" + getName() + "] attr-name=[" + pName
//												 + "] attr-typ=[" + lType + "]", e);
//		}
//		final List<Element> lValuesElemList = lAttrElem.getChild(XML_ELEMENT_VALUES, lNamespace).getChildren();
//		final Object[] lValues = (Object[]) Array.newInstance(lArrayClass, lValuesElemList.size());
//		for (int i = 0; i < lValues.length; i++) {
//			try {
//				lValues[i] = TypeFactory.getObjectFor(DivaJavaType.getJavaTypeFor(lType), lValuesElemList.get(i).getTextTrim());
//			} catch (final FactoryException e) {
//				throw new TechnicalException("Fehler bei der String->Object-Konvertierung: fo=[" + getName() + "] attr-name=[" + pName
//													 + "] value=[" + lValuesElemList.get(i).getTextTrim() + "]", e);
//			}
//		}
//
//		return lValues;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final Object[] getAttributeValues(final String[] pNames) {
//		final Object[] lValueList = new Object[pNames.length];
//		for (int i = 0; i < pNames.length; i++) {
//			lValueList[i] = getAttributeValue(pNames[i]);
//		}
//		return lValueList;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override

	Map<String, Object> attributes = new HashMap<>();

	public final void setAttributeValue(final String pName, final Object pValue) {
		attributes.put(pName, pValue);
	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final void setAttributeValue(final String pName, final Object[] pValues) {
//		// Es wird nur etwas getan, wenn der Wert nicht schon gesetzt ist
//		if (Arrays.deepEquals(getAttributeValues(pName), pValues)) {
//			return;
//		}
//
//		final Element lAttrElem = getAttributeElem(pName);
//		if (lAttrElem == null) {
//			throw new TechnicalException(
//					EXCEPTION_MESSAGE_ATTR_NAME + "attrname=[" + pName + "] foname=[" + getName() + "] op=[getAttributeValue]");
//		}
//		final Namespace lNamespace = getNameSpace();
//		// ggf. vorhandene Werte zuruecksetzen
//		final Element lValuesElem = lAttrElem.getChild(XML_ELEMENT_VALUES, lNamespace);
//		if (lValuesElem != null) {
//			lValuesElem.removeContent();
//		}
//		if (pValues != null) {
//			for (final Object lValue : pValues) {
//				if (lValue != null) {
//					final Element lValueElem = getJDomFactory().element(XML_ELEMENT_VALUE, lNamespace);
//					lAttrElem.getChild(XML_ELEMENT_VALUES, lNamespace).addContent(lValueElem);
//					if (isCorrectJavaType(pName, lValue)) {
//						try {
//							lValueElem.setText(TypeFactory.getStringFor(lValue));
//						} catch (final FactoryException e) {
//							throw new TechnicalException(
//									"Fehler bei der Object->String-Konvertierung: name=[" + pName + "] value=[" + lValue + "]", e);
//						}
//					} else {
//						throw new DataObjectException(EXCEPTION_MESSAGE_JAVA_TYPE + "attr-name=[" + pName + "] attr-type=["
//															  + lValue.getClass().getName() + "] attr-type-erwartet=[" + getSimpleAttributeMetaInfoFor(pName).getJavaType()
//															  + "] attr-value=[" + lValue + "]");
//					}
//				}
//			}
//		}
//
//		// wird beim Auslesen der Attribute als Properties wieder neu aufgebaut
//		this.attributeValueProps = null;
//		// ModifiedVersion Zaehler aktualisieren
//		rsChanged();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final void setAttributeValues(final String[] pNames, final Object[] pValues) {
//		if (pNames.length != pValues.length) {
//			throw new TechnicalException("ungueltige Verwendung: pNames.length != pValues.length: pNames.length=[" + pNames.length
//												 + "] pValues.length=[" + pValues.length + "]");
//		}
//		for (int i = 0; i < pNames.length; i++) {
//			setAttributeValue(pNames[i], pValues[i]);
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final void registerAttribute(final AttributeMetaInfo pMetaInfo) {
//		final Namespace lNamespace = getNameSpace();
//
//		final Element lAttribute = getJDomFactory().element(XML_ELEMENT_ATTRIBUTE, lNamespace);
//
//		if (pMetaInfo.isKeyAttribute()) {
//			lAttribute.setAttribute(XML_ATTRIBUTE_TYPE, KEY.toString());
//		}
//		if (pMetaInfo.isIdAttribute()) {
//			lAttribute.setAttribute(XML_ATTRIBUTE_TYPE, ID.toString());
//		}
//
//		final Element lName = getJDomFactory().element(XML_ELEMENT_NAME, lNamespace);
//		lName.setText(pMetaInfo.getAttributeName());
//		lAttribute.addContent(lName);
//
//		final Element lValues = new Element(XML_ELEMENT_VALUES, lNamespace);
//		lAttribute.addContent(lValues);
//
//		final Element lMetaInfo = getJDomFactory().element(XML_ELEMENT_META, lNamespace);
//		final Element lSuperior = getJDomFactory().element(XML_ELEMENT_SUPERIOR, lNamespace);
//
//		Element lAttrName;
//		if (pMetaInfo.hasSuperiorAttributes()) {
//			for (final String lSuperiorAttribute : pMetaInfo.getSuperiorAttributes()) {
//				lAttrName = getJDomFactory().element(XML_ELEMENT_ATTR_NAME, lNamespace);
//				lAttrName.setText(lSuperiorAttribute);
//				lSuperior.addContent(lAttrName);
//			}
//		}
//
//		lMetaInfo.addContent(lSuperior);
//
//		final Element lMvAttrName = getJDomFactory().element(XML_ELEMENT_MV_ATTR_NAME, lNamespace);
//		lMvAttrName.setText(pMetaInfo.getMultiValueAttributeName());
//		lMetaInfo.addContent(lMvAttrName);
//
//		final Element lType = getJDomFactory().element(XML_ELEMENT_TYPE, lNamespace);
//		lType.setText(pMetaInfo.getJavaType().toString());
//		lMetaInfo.addContent(lType);
//
//		final Element lSchlverzName = getJDomFactory().element(XML_ELEMENT_SCHLVERZNAME, lNamespace);
//		lSchlverzName.setText(pMetaInfo.getSchlverzName());
//		lMetaInfo.addContent(lSchlverzName);
//
//		final Element lTitle = getJDomFactory().element(XML_ELEMENT_TITLE, lNamespace);
//		lTitle.setText(pMetaInfo.getTitle());
//		lMetaInfo.addContent(lTitle);
//
//		final Element lRvArchiv = getJDomFactory().element(XML_ELEMENT_RVARCHIV, lNamespace);
//		lRvArchiv.setText(Boolean.toString(pMetaInfo.isRvArchivAttribute()));
//		lMetaInfo.addContent(lRvArchiv);
//
//		final Element lNullable = getJDomFactory().element(XML_ELEMENT_NULLABLE, lNamespace);
//		lNullable.setText(Boolean.toString(pMetaInfo.isNullable()));
//		lMetaInfo.addContent(lNullable);
//
//		final Element lReadOnly = getJDomFactory().element(XML_ELEMENT_READONLY, lNamespace);
//		lReadOnly.setText(Boolean.toString(pMetaInfo.isReadonly()));
//		lMetaInfo.addContent(lReadOnly);
//
//		final Element lMinLen = getJDomFactory().element(XML_ELEMENT_MINLEN, lNamespace);
//		lMinLen.setText(Integer.toString(pMetaInfo.getMinlen()));
//		lMetaInfo.addContent(lMinLen);
//
//		final Element lMaxLen = getJDomFactory().element(XML_ELEMENT_MAXLEN, lNamespace);
//		lMaxLen.setText(Integer.toString(pMetaInfo.getMaxlen()));
//		lMetaInfo.addContent(lMaxLen);
//
//		final Element lUpperCase = getJDomFactory().element(XML_ELEMENT_UPPERCASE, lNamespace);
//		lUpperCase.setText(Boolean.toString(pMetaInfo.isUppercase()));
//		lMetaInfo.addContent(lUpperCase);
//
//		final Element lLowerCase = getJDomFactory().element(XML_ELEMENT_LOWERCASE, lNamespace);
//		lLowerCase.setText(Boolean.toString(pMetaInfo.isLowercase()));
//		lMetaInfo.addContent(lLowerCase);
//
//		final Element lDefault = getJDomFactory().element(XML_ELEMENT_ATTR_DEFAULT, lNamespace);
//		lDefault.setText(pMetaInfo.getDefault());
//		lMetaInfo.addContent(lDefault);
//
//		lAttribute.addContent(lMetaInfo);
//		getDocument().getRootElement().getChild(XML_ELEMENT_ATTRIBUTES, lNamespace).addContent(lAttribute);
//		getDomValueMap().put(pMetaInfo.getAttributeName(), lAttribute);
//
//		// sofern die Attribut-Metainformationen einen Default vorsehen, wird dieser als Value vorbelegt
//		if (pMetaInfo.getDefault() != null && !pMetaInfo.getDefault().isEmpty()) {
//			setDefautValue(pMetaInfo.getAttributeName(), pMetaInfo.getJavaType(), pMetaInfo.getDefault());
//		}
//		this.attributeValueProps = null;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final void registerAttributes(final List<AttributeMetaInfo> pMetaInfos) {
//		for (final AttributeMetaInfo lAttributeMetaInfo : pMetaInfos) {
//			registerAttribute(lAttributeMetaInfo);
//		}
//	}
//
//	/**
//	 * Entfernt ein Attribut.
//	 *
//	 * @param pAttrName Attributname, der entfernt werden soll.
//	 *
//	 */
//	@Override
//	public final void removeAttribute(final String pAttrName) {
//		final Element lAttrElem = getDomValueMap().get(pAttrName);
//		final Namespace lNamespace = getNameSpace();
//		if (lAttrElem != null) {
//			lAttrElem.getChild(XML_ELEMENT_VALUES, lNamespace).removeContent();
//			lAttrElem.getChild(XML_ELEMENT_NAME, lNamespace).removeContent();
//			lAttrElem.getChild(XML_ELEMENT_META, lNamespace).removeContent();
//			getDocument().getRootElement().getChild(XML_ELEMENT_ATTRIBUTES, lNamespace).removeContent(lAttrElem);
//			getDomValueMap().remove(pAttrName);
//		}
//		this.attributeValueProps = null;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final boolean hasAttribute(final String pName) {
//		return getDomValueMap().containsKey(pName);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final void setReadOnly(final boolean pCondition) {
//		final Collection<Element> lAttributes = getDomValueMap().values();
//		final Namespace lNamespace = getNameSpace();
//		for (final Element lAttribute : lAttributes) {
//			lAttribute.getChild(XML_ELEMENT_META, lNamespace).getChild(XML_ELEMENT_READONLY, lNamespace)
//					.setText(Boolean.toString(pCondition));
//		}
//		// ModifiedVersion Zaehler aktualisieren
//		rsChanged();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final Properties getAttributeValueProps() {
//		return getAttributeValueProps(getName());
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final Properties getAttributeValueProps(final String pNamePrefix) {
//		// TODO Cache pro Prefix!
//		if (this.attributeValueProps == null) {
//			if (getName() == null) {
//				throw new TechnicalException("Namenloses FO: Namen mit setName() belegen!");
//			}
//			this.attributeValueProps = new Properties();
//			this.attributeValueProps.put("pur.fo.name", getName());
//			final String lNamePrefix = pNamePrefix == null ? null : pNamePrefix + "#";
//			for (final String lAttrName : getAttributeNames()) {
//				final String lPropName = lNamePrefix == null ? lAttrName : lNamePrefix + lAttrName;
//				final Object lValue = getAttributeValue(lAttrName);
//				if (lValue == null) {
//					this.attributeValueProps.put(lPropName, "");
//				} else {
//					this.attributeValueProps.put(lPropName, lValue.toString());
//				}
//			}
//		}
//		return this.attributeValueProps;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final boolean isNull(final String pAttrName) {
//		final Object lValue = getAttributeValue(pAttrName);
//		if (lValue != null) {
//			return lValue instanceof String && isNullString((String) lValue);
//		}
//		return true;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	@SuppressWarnings("unchecked")
//	public final <T extends IFODataObject> List<T> getRelatedFoDataObjects(final String pRelationName) {
//		if (getFoRelationMap().containsKey(pRelationName)) {
//			return (List<T>) getFoRelationMap().get(pRelationName).getFoList();
//		} else {
//			// Beziehung ist nicht bekannt - leere Liste zurueckgeben
//			return new ArrayList<T>(0);
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final Object[] getRelatedFoDataObjectIds(final String pRelationName) {
//		final Object[] lIds;
//		if (getFoRelationMap().containsKey(pRelationName)) {
//			final List<IFODataObject> lList = getFoRelationMap().get(pRelationName).getFoList();
//			lIds = new Object[lList.size()];
//			for (int i = 0; i < lIds.length; i++) {
//				lIds[i] = lList.get(i).getId();
//			}
//		} else {
//			lIds = new Object[0];
//		}
//		return lIds;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final void addRelatedFoDataObject(final String pRelationName, final IFODataObject pFoDataObject) {
//		// FO der Beziehung nur dann hinzufuegen, wenn entweder die Id am FO nicht belegt oder
//		// das FO mit dieser Id in der Beziehung noch nicht hinzugefuegt wurde
//		final Object lOtherId = pFoDataObject.getId();
//		if (lOtherId == null || "".equals(lOtherId) || !containsRelatedFoDataObject(pRelationName, lOtherId)) {
//			final FoRelationInfo lFoRelationInfo = getFoRelationMap().computeIfAbsent(pRelationName,
//																					  lRelationName -> new FoRelationInfo(lRelationName));
//			lFoRelationInfo.addFo(pFoDataObject);
//		}
//
//		// ModifiedVersion Zaehler aktualisieren
//		rsChanged();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final void removeRelatedFoDataObject(final String pRelationName, final Object pId) {
//		if (containsRelatedFoDataObject(pRelationName, pId)) {
//			getFoRelationMap().get(pRelationName).removeFo(pId);
//		}
//
//		// ModifiedVersion Zaehler aktualisieren
//		rsChanged();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final void removeRelatedFoDataObjects(final String pRelationName) {
//		if (getFoRelationMap().containsKey(pRelationName)) {
//			getFoRelationMap().get(pRelationName).removeAll();
//		}
//
//		// ModifiedVersion Zaehler aktualisieren
//		rsChanged();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final boolean containsRelatedFoDataObject(final String pRelationName, final Object pId) {
//		if (getFoRelationMap().containsKey(pRelationName)) {
//			return getFoRelationMap().get(pRelationName).containsFo(pId);
//		} else {
//			return false;
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final String[] getRelationNames() {
//		if (this.foRelationMap == null) {
//			return new String[0];
//		} else {
//			return getFoRelationMap().keySet().toArray(new String[getFoRelationMap().size()]);
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final IFODataObject clone() {
//		final String lFoXml;
//		try {
//			super.clone();
//			lFoXml = this.toXML();
//		} catch (final CloneNotSupportedException e) {
//			throw new DataObjectException("Fehler beim Clone", e);
//		}
//		return new FODataObject(lFoXml);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final void setProperties(final Properties pProperties) {
//		if (pProperties == null || pProperties.isEmpty()) {
//			return;
//		}
//		for (final Object lName : pProperties.keySet()) {
//			setProperty((String) lName, pProperties.getProperty((String) lName));
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final void setProperty(final String pKey, final String pValue) {
//		final Element lPropertiesElement;
//		final Element lChildElement;
//		final Attribute lKeyAttribute;
//		final Namespace lNamespace = getNameSpace();
//		if (pKey != null) {
//			lPropertiesElement = getJDomFactory().element(XML_ELEMENT_PROPERTIES, lNamespace);
//			lChildElement = getJDomFactory().element(XML_ELEMENT_PROPERTY, lNamespace);
//			lKeyAttribute = getJDomFactory().attribute(XML_ATTRIBUTE_KEY, pKey);
//			lChildElement.setAttribute(lKeyAttribute);
//			lChildElement.setText(pValue);
//
//			// Properties Tag schon vorhanden
//			if (getDocument().getRootElement().getChild(XML_ELEMENT_PROPERTIES, lNamespace) == null) {
//				// noch kein Properties Tag vorhanden
//				lPropertiesElement.addContent(lChildElement);
//				getDocument().getRootElement().addContent(lPropertiesElement);
//			} else {
//				// Properties Tag schon vorhanden
//				getDocument().getRootElement().getChild(XML_ELEMENT_PROPERTIES, lNamespace).addContent(lChildElement);
//			}
//			// ModifiedVersion Zaehler aktualisieren
//			rsChanged();
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final String getProperty(final String pKey) {
//		List<Element> lProperties = null;
//		final Namespace lNamespace = getNameSpace();
//
//		if (getDocument().getRootElement().getChild(XML_ELEMENT_PROPERTIES, lNamespace) != null) {
//			lProperties = getDocument().getRootElement().getChild(XML_ELEMENT_PROPERTIES, lNamespace).getChildren();
//		}
//
//		if (lProperties == null) {
//			return null;
//		}
//
//		for (final Element lProperty : lProperties) {
//			if (lProperty.getAttributeValue(XML_ATTRIBUTE_KEY).equals(pKey)) {
//				return lProperty.getText();
//			}
//		}
//
//		return null;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final Properties getProperties() {
//		final Properties lProps = new Properties();
//		final Namespace lNamespace = getNameSpace();
//		final Element lPropsElem = getDocument().getRootElement().getChild(XML_ELEMENT_PROPERTIES, lNamespace);
//		if (lPropsElem != null) {
//			final List<Element> lPropertiesElemList = lPropsElem.getChildren();
//			for (final Element lPropertiesElem : lPropertiesElemList) {
//				lProps.put(lPropertiesElem.getAttributeValue(XML_ATTRIBUTE_KEY), lPropertiesElem.getText());
//			}
//		}
//		return lProps;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final long getModifiedVersion() {
//		return this.modVersionBase + this.modVersion;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public IValuesObject getValuesObject() {
//		final IValuesObject lReturn = DataObjectFactory.createValuesObject();
//		for (final String lAttributename : getAttributeNames()) {
//			lReturn.setValue(lAttributename, getAttributeValue(lAttributename));
//		}
//		return lReturn;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	protected final void initEmptyDocument(final String pRoot) {
//		super.initEmptyDocument(pRoot);
//		final Namespace lNamespace = getNameSpace();
//		final Element lType = getJDomFactory().element(XML_ELEMENT_TYPE, lNamespace);
//		if (this.getClass().getInterfaces().length > 0) {
//			lType.setText(this.getClass().getInterfaces()[0].getCanonicalName());
//		} else {
//			lType.setText(this.getClass().getCanonicalName());
//		}
//		getDocument().getRootElement().addContent(lType);
//		getDocument().getRootElement().addContent(getJDomFactory().element(XML_ELEMENT_ATTRIBUTES, lNamespace));
//	}
//
//	/**
//	 * Registriert ein Attribut mithilfe der {@link SimpleAttributeMetaInfo}.
//	 * @param pSimpleMetaInfo {@link SimpleAttributeMetaInfo} des zu registrierenden Attributs.
//	 */
//	protected final void registerAttribute(final SimpleAttributeMetaInfo pSimpleMetaInfo) {
//		final Namespace lNamespace = getNameSpace();
//		final Element lAttributeElem = getJDomFactory().element(XML_ELEMENT_ATTRIBUTE, lNamespace);
//		if (pSimpleMetaInfo.isIdAttribute()) {
//			lAttributeElem.setAttribute(XML_ATTRIBUTE_TYPE, ID.toString());
//		}
//
//		final Element lNameElem = getJDomFactory().element(XML_ELEMENT_NAME, lNamespace);
//		lNameElem.setText(pSimpleMetaInfo.getAttributeName());
//		lAttributeElem.addContent(lNameElem);
//
//		final Element lValuesElem = new Element(XML_ELEMENT_VALUES, lNamespace);
//		lAttributeElem.addContent(lValuesElem);
//
//		final Element lMetaInfoElem = getJDomFactory().element(XML_ELEMENT_META, lNamespace);
//		lAttributeElem.addContent(lMetaInfoElem);
//
//		final Element lJavaTypeElem = getJDomFactory().element(XML_ELEMENT_TYPE, lNamespace);
//		lJavaTypeElem.setText(pSimpleMetaInfo.getJavaType().toString());
//		lMetaInfoElem.addContent(lJavaTypeElem);
//
//		// Das zusammengestellte attribute-Element dem Root-Element hinzufuegen
//		getDocument().getRootElement().getChild(XML_ELEMENT_ATTRIBUTES, lNamespace).addContent(lAttributeElem);
//		getDomValueMap().put(pSimpleMetaInfo.getAttributeName(), lAttributeElem);
//
//		this.attributeValueProps = null;
//	}
//
//	/**
//	 * Initialiert aus dem Document die - sofern vorhanden - mit diesem FO in Beziehung stehenden FO.
//	 */
//	protected final void initRelatedFoDataObjectsFromDocument() {
////		final Namespace lNamespace = getNameSpace();
////		final Element lRelationListElem = getDocument().getRootElement().getChild(XML_ELEM_RELATION_LIST, lNamespace);
////		if (lRelationListElem != null) {
////			final List<Element> lRelationElemList = lRelationListElem.getChildren(XML_ELEM_RELATION, lNamespace);
////			for (final Element lRelationElem : lRelationElemList) {
////				final String lRelationName = lRelationElem.getChildText(XML_ELEMENT_NAME, lNamespace);
////				final String lFoImplClassName = lRelationElem.getChildText(XML_ELEM_REL_FO_IMPL_CLASS, lNamespace);
////				final List<?> lRelatedFoElemList = lRelationElem.getChild(XML_ELEM_RELATED_FO_LIST, lNamespace).getChildren();
////				for (final Object lRelatedFoElem : lRelatedFoElemList) {
////					try {
////						final IFODataObject lRelatedFo = (IFODataObject) getGenericObjectFactory().create(lFoImplClassName, lRelatedFoElem);
////						addRelatedFoDataObject(lRelationName, lRelatedFo);
////					} catch (final FactoryException e) {
////						throw new TechnicalException(e.getMessage(), e);
////					}
////				}
////			}
////			// Relation-Liste aus dem Document entfernen - da diese bei der XML-Konvertierung wieder neu
////			// in den DOM-Tree eingepflegt wird
////			getDocument().getRootElement().removeChild(XML_ELEM_RELATION_LIST, lNamespace);
////			// DOM-Tree darf nun nicht mehr das Element "relations" haben
////			// System.out.println(getXmlOutputter().outputString(getDocument()));
////		}
//	}
//
//	/**
//	 * Interne Methode zum Besetzen einer Liste von in Beziehung stehenden FOs.
//	 * @param <T> Typ der Liste
//	 * @param pRelationName Beziehungsname
//	 * @param pFoList Liste der FOs
//	 */
//	@SuppressWarnings("unchecked")
//	protected final <T extends IFODataObject> void setRelatedFoDataObjects(final String pRelationName, final List<T> pFoList) {
//		if (pFoList == null) {
//			final FoRelationInfo lFoRelationInfo = getFoRelationMap().get(pRelationName);
//			if (lFoRelationInfo != null) {
//				// leere Liste in den FoRelationInfo fuer die beziehung setzen
//				lFoRelationInfo.setFoList(new ArrayList<IFODataObject>());
//			}
//		} else {
//			FoRelationInfo lFoRelationInfo = getFoRelationMap().get(pRelationName);
//			if (lFoRelationInfo == null) {
//				lFoRelationInfo = new FoRelationInfo(pRelationName);
//				getFoRelationMap().put(pRelationName, lFoRelationInfo);
//			}
//			lFoRelationInfo.setFoList((List<IFODataObject>) pFoList);
//		}
//
//		// ModifiedVersion Zaehler aktualisieren
//		rsChanged();
//	}
//
//	/**
//	 * Ueberschreiben des Callback um -  sofern vorhanden - die Documents der in
//	 * Beziehung stehenden FOs in einen gemeinsamen DOM-Tree zu mergen.
//	 */
//	@Override
//	protected final void completeDocument() {
//		final Namespace lNamespace = getNameSpace();
//		if (getDocument().getRootElement().getChild(XML_ELEM_RELATION_LIST, lNamespace) != null) {
//			// Relation-Liste aus dem Document entfernen - da diese mit dieser Routine neu eingepflegt wird
//			getDocument().getRootElement().removeChild(XML_ELEM_RELATION_LIST, lNamespace);
//		}
//
//		if (!hasRelatedFoDataObjects()) {
//			// keine Relations mit diesem FO
//			return;
//		}
//
//		final Set<String> lRelationNames = getFoRelationMap().keySet();
//		final Element lRelationListElem = getJDomFactory().element(XML_ELEM_RELATION_LIST, lNamespace);
//		getDocument().getRootElement().addContent(lRelationListElem);
//
//		for (final String lRelationName : lRelationNames) {
//			final FoRelationInfo lFoRelationInfo = getFoRelationMap().get(lRelationName);
//			if (lFoRelationInfo.isEmpty()) {
//				// keine Elemente - braucht nicht mit im XML aufzutauchen
//				continue;
//			}
//
//			final Element lRelationElem = getJDomFactory().element(XML_ELEM_RELATION, lNamespace);
//			lRelationListElem.addContent(lRelationElem);
//
//			final Element lRelationNameElem = getJDomFactory().element(XML_ELEMENT_NAME, lNamespace);
//			lRelationNameElem.setText(lFoRelationInfo.getName());
//			lRelationElem.addContent(lRelationNameElem);
//
//			final Element lRelatedFoImplClassElem = getJDomFactory().element(XML_ELEM_REL_FO_IMPL_CLASS, lNamespace);
//			lRelatedFoImplClassElem.setText(lFoRelationInfo.getFoImplClass().getName());
//			lRelationElem.addContent(lRelatedFoImplClassElem);
//
//			final Element lRelatedFoListElem = getJDomFactory().element(XML_ELEM_RELATED_FO_LIST, lNamespace);
//			lRelationElem.addContent(lRelatedFoListElem);
//
//			for (final IFODataObject lRelatedFo : getRelatedFoDataObjects(lRelationName)) {
//				// Element fo_dao hinzufuegen
//				final Element lFoDaoElem = getJDomFactory().element(XML_ELEM_RELATED_FO, lNamespace);
//				lRelatedFoListElem.addContent(lFoDaoElem);
//				// Content unterhalb von fo_dao aus dem Document des Beziehungs-FODataObject uebernehmen
//				final List<Content> lElemList = ((FODataObject) lRelatedFo).getDocument(true).getRootElement().cloneContent();
//				lFoDaoElem.addContent(lElemList);
//			}
//		}
//	}
//
//	/**
//	 * Liefert die interne Map mit den Listen der in Beziehung stehenden FOs.
//	 * @return Beziehungs-Map
//	 */
//	protected final Map<String, FoRelationInfo> getFoRelationMap() {
//		if (this.foRelationMap == null) {
//			this.foRelationMap = new HashMap<>();
//		}
//		return this.foRelationMap;
//	}
//
//	/**
//	 * Liefert das attribute-Element fuer das Attribut mit Namen pName.
//	 * @param pName Name des Attributs
//	 * @return DOM-Element oder null, wenn nicht gefunden
//	 */
//	protected final Element getAttributeElem(final String pName) {
//		return getDomValueMap().get(pName);
//	}
//
//	/** Holt die lazy-initialisierte Map für die Attributelemente zurück.
//	 *
//	 * @return eine Map mit den Attributnamen als Key und
//	 * {@code getDocument().getRootElement().getChild(XML_ELEMENT_ATTRIBUTES, getNameSpace()).getChildren()} als Value
//	 */
//	protected final Map<String, Element> getDomValueMap() {
//		if (this.domValueMap == null) {
//			final Namespace lNamespace = getNameSpace();
//			// AttrCache erstmalig laden, z.B. wenn das DataObject aus einem XML erstellt wurde
//			this.domValueMap = new HashMap<>();
//			final List<Element> lAttributeElemList = getDocument().getRootElement().getChild(XML_ELEMENT_ATTRIBUTES, lNamespace)
//					.getChildren();
//			for (final Element lElem : lAttributeElemList) {
//				this.domValueMap.put(lElem.getChildTextTrim(XML_ELEMENT_NAME, lNamespace), lElem);
//			}
//		}
//		return this.domValueMap;
//	}
//
//	/**
//	 * Falls der Defaultwert belegt ist, wird er gesetzt.
//	 * @param pName Name des Attributs
//	 * @param pType JavaTyp des Defaultwerts
//	 * @param pDefaultValue Stringrepraesentation des Defaultwerts.
//	 */
//	private void setDefautValue(final String pName, final DivaJavaType pType, final String pDefaultValue) {
//		String defaultValue = pDefaultValue;
//		if (defaultValue != null) {
//			try {
//				if (defaultValue.equals("sysdate")) {
//					defaultValue = String.valueOf(System.currentTimeMillis());
//				}
//				final Object lObject = TypeFactory.getObjectFor(pType, defaultValue);
//				setAttributeValue(pName, lObject);
//			} catch (final FactoryException e) {
//				throw new DataObjectException("Fehler bei Erzeugung des Default-Value: ", e);
//			}
//		}
//
//		// ModifiedVersion Zaehler aktualisieren
//		rsChanged();
//	}
//
//	/**
//	 * Pr&uuml;ft ob ein Wert eine Instanz des in den AttributeMetaInfos hinterlegten Java-Klassentypen ist.
//	 *
//	 * @param pAttrName Attributname, dessen AttributeMetaInfos zur Pr&uuml;fung herangezogen werden
//	 * @param pValue Wert
//	 * @return pValue ist Instanz des hinterlegten Java-Klassentypen
//	 */
//	private boolean isCorrectJavaType(final String pAttrName, final Object pValue) {
//		try {
//			final DivaJavaType lJavaType = getSimpleAttributeMetaInfoFor(pAttrName).getJavaType();
//			final Class<?> lClazz = Class.forName(lJavaType.value());
//			if (lClazz.isInstance(pValue)) {
//				return true;
//			}
//		} catch (final Exception e) {
//			throw new DataObjectException(e.getMessage(), e);
//		}
//		return false;
//	}
//
//	// /**
//	// * Liefert die Metainfo des ersten gefundenen Id-Attributs.
//	// * @return Metainfo des Id-Attributs oder null
//	// */
//	// private SimpleAttributeMetaInfo getIdAttributeMetaInfo() {
//	// if (_idAttrMetaInfo == null) {
//	// SimpleAttributeMetaInfo[] lMetaInfos = getSimpleMetaInfosFor(getAttributeNames());
//	// for (int i = 0; i < lMetaInfos.length; i++) {
//	// if (lMetaInfos[i].isIdAttribute()) {
//	// _idAttrMetaInfo = lMetaInfos[i];
//	// break;
//	// }
//	// }
//	// }
//	// return _idAttrMetaInfo;
//	// }
//
//	/**
//	 * Interne Abfrage ob diesem FO in Beziehung stehende FOs zugeordnet wurden.
//	 * @return true, wenn Beziehungen bestehen
//	 */
//	private boolean hasRelatedFoDataObjects() {
//		return this.foRelationMap != null;
//	}
//
//	/**
//	 * Methode muss von allen FO veraendernden Methoden aufgerufen werden, um den
//	 * ModifiedVersion-Zaehler zu aktualisieren.
//	 */
//	private void rsChanged() {
//		this.modVersion++;
//	}
//
//	/**
//	 * Liefert die generische Factory zur Erzeugung von Objekten (wird bei der Instanziierung
//	 * von in Beziehung stehenden FOs nach XML-Deserialisierung verwendet).
//	 * @return Helper zur Instanziierung von FO-Datenobjekten (insbesondere von abgeleiteten Implementierungen)
//	 */
//	private GenericObjectFactory getGenericObjectFactory() {
//		if (this.genericObjectFactoy == null) {
//			this.genericObjectFactoy = new GenericObjectFactory(Thread.currentThread().getContextClassLoader());
//		}
//		return this.genericObjectFactoy;
//	}
//
//	/**
//	 * Innere Klasse zur Verwaltung der Informationen fuer eine FO-Beziehung.
//	 */
//	private static class FoRelationInfo implements Serializable {
//		/**Name des FO's*/
//		private final String name;
//		/**Implementierungsklasse*/
//		private Class<?> foImplClass;
//		/**Fo-Beziehung*/
//		private List<IFODataObject> relatedFoList;
//
//		/**
//		 * Konstruktor.
//		 * @param pName Name der Beziehung
//		 */
//		FoRelationInfo(final String pName) {
//			this.name = pName;
//			this.relatedFoList = new ArrayList<>();
//			this.foImplClass = null;
//		}
//
//		/**
//		 * Liefert den Namen der FO-Beziehung.
//		 * @return Beziehungsname
//		 */
//		String getName() {
//			return this.name;
//		}
//
//		/**
//		 * Liefert Klassendefinition der Implementierungsklasse der in Beziehung stehenden FOs.
//		 * @return Klassendefinition
//		 */
//		Class<?> getFoImplClass() {
//			bestimmeFoImplClass();
//			return this.foImplClass;
//		}
//
//		/**
//		 * Liefert die Liste mit den in Beziehung stehenden FOs.
//		 * @return Liste
//		 */
//		List<IFODataObject> getFoList() {
//			assert true;
//			return this.relatedFoList;
//		}
//
//		/**
//		 * Setzen der Liste der in Beziehung stehenden FOs.
//		 * @param pFoList Liste
//		 */
//		void setFoList(final List<IFODataObject> pFoList) {
//
//			this.relatedFoList = pFoList;
//			this.foImplClass = null;
//		}
//
//		/**
//		 * Prueft, ob das FO mit Id pId in der Beziehungsliste bereits enthalten ist.
//		 * @param pId Id des FO
//		 * @return true, wenn FO bereits enthalten
//		 */
//		boolean containsFo(final Object pId) {
//			for (final IFODataObject lFo : this.relatedFoList) {
//				if (pId.equals(lFo.getId())) {
//					return true;
//				}
//			}
//			return false;
//		}
//
//		/**
//		 * Fuegt ein FO der Beziehung hinzu.
//		 * @param pFo FO
//		 */
//		void addFo(final IFODataObject pFo) {
//			this.relatedFoList.add(pFo);
//		}
//
//		/**
//		 * Entfernt ein FO aus der Beziehungsliste.
//		 * @param pId Id des FO
//		 */
//		void removeFo(final Object pId) {
//			for (int i = 0; i < this.relatedFoList.size(); i++) {
//				final IFODataObject lFo = this.relatedFoList.get(i);
//				if (pId.equals(lFo.getId())) {
//					this.relatedFoList.remove(i);
//					break;
//				}
//			}
//		}
//
//		/**
//		 * Entfernt alle FO aus der Beziehung.
//		 */
//		void removeAll() {
//			this.relatedFoList.clear();
//		}
//
//		/**
//		 * true, wenn in der Beziehung keine FOs zugeordnet sind.
//		 * @return true/false
//		 */
//		boolean isEmpty() {
//			return this.relatedFoList.isEmpty();
//		}
//
//		/**
//		 * interne Methode zur Bestimmung der konkreten Implementierungsklasse der in
//		 * Beziehung stehenden FOs.
//		 */
//		private void bestimmeFoImplClass() {
//			if (this.foImplClass == null && !this.relatedFoList.isEmpty()) {
//				this.foImplClass = this.relatedFoList.get(0).getClass();
//			}
//		}
//	}
}