package org.hibernate.bugs.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.jdom2.DefaultJDOMFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.JDOMFactory;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Implementiert ein allgemeines Transferdatenobjekt.
 * Diese Klasse implementiert Methoden, die in allen Datenobjekten zur Verfuegung stehen sollen.
 * Ebenso werden zentrale Member in dieser Klasse vorgehalten. Die Daten werden in einem JDOM-Document
 * vorgehalten und ausschliesslich dort verwaltet.
 */
public abstract class ATransferDataObject {
	/**
	 * name.
	 */
	protected static final String XML_ELEMENT_NAME = "name";

	/**
	 * Namespace URI
	 */
	private static final String NAMESPACE_URI = "http://deutscherv.de/diva/rc";

	/**
	 * divarc.
	 */
	private static final String NAMESPACE_PREFIX = "divarc";

	/**
	 * XML JDOM-Dokument.
	 */
	private Document document;

	/**
	 * Factory zur Erzeugung von JDOM-Elementen.
	 */
	private transient JDOMFactory jDOMFactory;

	/**
	 * Builder zum Einlesen von XML-Daten.
	 */
	private transient SAXBuilder saxBuilder;

	/**
	 * spezifischer Namespace.
	 */
	private transient Namespace namespace;

	/**
	 * Schalter zur Steuerung des XML-Ausgabeformats.
	 */
	private transient boolean xmlFormatPretty;

	/**
	 * Erzeugt ein leeres Datenobjekt.
	 */
	public ATransferDataObject() {
	}

	/**
	 * Erzeugt ein Datenobjekt aus einem XML- oder JSON-String.
	 *
	 * @param pString XML/JSON-String
	 */
	public ATransferDataObject(final String pString) {
		if ( pString == null ) {
			return;
		}

		try {
			final StringReader lStringReader = new StringReader( pString );
			setDocument( getSaxBuilder().build( lStringReader ) );
		}
		catch (final Exception e) {
			throw new RuntimeException( e.getMessage() + " xml=[" + pString + "]", e );
		}
	}

	/**
	 * Erzeugt ein Datenobjekt aus einem XML-Stream.
	 *
	 * @param pXml XML-Stream
	 */
	public ATransferDataObject(final InputStream pXml) {
		try (pXml) {
			setDocument( generateDocumentFromInputStream( pXml ) );
		}
		catch (JDOMException | IOException e) {
			throw new RuntimeException( e.getMessage(), e );
		}
	}

	/**
	 * Erzeugt ein Datenobjekt aus dem XML. Der Parameter pXml kann hierbei gzip-komprimiert
	 * oder unkomprimiert uebergeben werden.
	 *
	 * @param pXml gzip-komprimiertes oder unkomprimiertes XML des Datenobjekts
	 */
	public ATransferDataObject(final byte[] pXml) {
		InputStream lIn = null;
		try {
			try {
				lIn = new GZIPInputStream( new ByteArrayInputStream( pXml ) );
			}
			catch (final java.util.zip.ZipException lZipEx) {
				// pXml ist unkomprimiert
				lIn = new ByteArrayInputStream( pXml );
			}
			setDocument( generateDocumentFromInputStream( lIn ) );
		}
		catch (JDOMException | IOException e) {
			throw new RuntimeException( e.getMessage(), e );
		}
		finally {
			try {
				if ( lIn != null ) {
					lIn.close();
				}
			}
			catch (final IOException e) {
				// ignored
			}
		}
	}

	/**
	 * Liefert den Namen des Datenobjekts.
	 *
	 * @return pName Name des Datenobjekts
	 */
	public String getName() {
		final Element lNameElem = getDocument().getRootElement().getChild( XML_ELEMENT_NAME, getNameSpace() );
		if ( lNameElem != null ) {
			return lNameElem.getText();
		}
		else {
			return null;
		}
	}

	/**
	 * Setzt den Namen des Datenobjekts.
	 *
	 * @param pName Name des Datenobjekts
	 */
	public void setName(final String pName) {
		Element lName = getDocument().getRootElement().getChild( XML_ELEMENT_NAME, getNameSpace() );
		if ( lName == null ) {
			lName = getJDomFactory().element( XML_ELEMENT_NAME, getNameSpace() );
			lName.setText( pName );
			getDocument().getRootElement().addContent( 0, lName );
		}
		else {
			lName.setText( pName );
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String toXML() {
		// Callback fuer ableitende Klassen
		completeDocument();
		return getXmlOutputter().outputString( getDocument() );
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] toXMLZip() {
		// Callback fuer ableitende Klassen
		completeDocument();
		GZIPOutputStream lGzos = null;
		final ByteArrayOutputStream lBaos = new ByteArrayOutputStream();
		try {
			lGzos = new GZIPOutputStream( lBaos );
			getXmlOutputter().output( getDocument(), lGzos );
		}
		catch (final IOException e) {
			throw new RuntimeException( e.getMessage(), e.getCause() );
		}
		finally {
			try {
				lBaos.close();
			}
			catch (IOException e) {
				// ignoriert
			}
			if ( lGzos != null ) {
				try {
					lGzos.close();
				}
				catch (IOException e) {
					// ignoriert
				}
			}
		}

		return lBaos.toByteArray();

	}

	/**
	 * {@inheritDoc}
	 */
	public final OutputStream toStream() {
		// Callback fuer ableitende Klassen
		completeDocument();
		final ByteArrayOutputStream lBaos = new ByteArrayOutputStream();
		try (lBaos) {
			getXmlOutputter().output( getDocument(), lBaos );
		}
		catch (final IOException e) {
			throw new RuntimeException( e.getMessage(), e.getCause() );
		}

		return lBaos;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void writeXmlToSream(final OutputStream pStream) {
		// Callback fuer ableitende Klassen
		completeDocument();
		try {
			getXmlOutputter().output( getDocument(), pStream );
		}
		catch (final IOException e) {
			throw new RuntimeException( e.getMessage(), e.getCause() );
		}
	}

	/**
	 * Callback der von ableitenden Klassen ueberschrieben werden kann, um den DOM-Tree zu komplettieren
	 * (z.B. wenn aus Performance-Grunden nicht alle Informationen permanten im DOM-Tree gehalten werden sollen).
	 * Der Aufruf des Callbacks erfolgt vor einer XML-Transformation oder wenn das der vollstaendige
	 * DOM-Tree von Aussen angefordert wird.
	 */
	protected void completeDocument() {
		// Callback
	}

	/**
	 * Callback der von ableitenden Klassen ueberschrieben werden kann, um ein leeres Document zu initialisieren.
	 */
	protected void initEmptyDocument() {
		// Callback
	}

	/**
	 * Erzeugt ein leeres JDOM-Document.
	 *
	 * @param pRoot Name des XML-Root-Elements
	 */
	protected void initEmptyDocument(final String pRoot) {
		setDocument( getJDomFactory().document( getJDomFactory().element( pRoot, getNameSpace() ) ) );
	}

	/**
	 * Liefert die JDOM-Factory.
	 *
	 * @return JDOM-Factory
	 */
	protected final JDOMFactory getJDomFactory() {
		if ( this.jDOMFactory == null ) {
			this.jDOMFactory = new DefaultJDOMFactory();
		}
		return this.jDOMFactory;
	}

	/**
	 * Liefert den SAX-Builder.
	 *
	 * @return SAX-Builder
	 */
	protected final SAXBuilder getSaxBuilder() {
		if ( this.saxBuilder == null ) {
			this.saxBuilder = new SAXBuilder();
		}
		return this.saxBuilder;
	}

	/**
	 * Liefert den XML-Outputter.
	 *
	 * @return XML-Outputter
	 */
	protected final XMLOutputter getXmlOutputter() {
		final Format lFormat;
		if ( this.xmlFormatPretty ) {
			lFormat = Format.getPrettyFormat();
		}
		else {
			lFormat = Format.getRawFormat();
		}
		lFormat.setEncoding( getEncoding() );
		return new XMLOutputter( lFormat );
	}

	/**
	 * Liefert das beim XML-Marshalling zu verwendende Encoding.
	 *
	 * @return Encoding fuer XML-Marshaller
	 */
	protected String getEncoding() {
		return "XML"; // default
	}

	/**
	 * Liefert den XML-Namespace.
	 *
	 * @return XML-Namespace
	 */
	protected Namespace getNameSpace() {
		if ( this.namespace == null ) {
			this.namespace = Namespace.getNamespace( NAMESPACE_PREFIX, NAMESPACE_URI );

		}
		return this.namespace;
	}

	/**
	 * Liefert das JDOM-Document.
	 * (public zur internen Verwendung - gehoert nicht mit zum Interface ITransferDataObject)
	 *
	 * @return JDOM-Document
	 */
	public final Document getDocument() {
		return this.document;
	}

	/**
	 * Setzt das JDOM-Document.
	 * (public zur internen Verwendung - gehoert nicht mit zum Interface ITransferDataObject)
	 *
	 * @param pDocument JDOM-Document
	 */
	public final void setDocument(final Document pDocument) {
		this.document = pDocument;
	}

	/**
	 * Liefert das JDOM-Document. Ueber den Parameter pCompleted kann gesteuert werden,
	 * ob der Callback completeDocument() zuvor aufgerufen werden soll.
	 *
	 * @param pCompleted true, wenn Callback aufgerufen werden soll
	 *
	 * @return JDOM-Document
	 */
	protected final Document getDocument(final boolean pCompleted) {
		if ( pCompleted ) {
			completeDocument();
		}
		return this.document;
	}

	/**
	 * Steuert das XML-Ausagbeformat.
	 *
	 * @param pPretty true -&gt; XML-formatierte Ausgabe (default); false -&gt; XML auf einer Zeile
	 */
	public final void setXmlFormatPretty(final boolean pPretty) {
		this.xmlFormatPretty = pPretty;
	}

	/**
	 * Erzeugt ein JDOM-Document aus einem InputStream.
	 *
	 * @param pInputStream InputStream
	 *
	 * @return JDOM-Document
	 *
	 * @throws JDOMException JDOMException
	 * @throws IOException IOExceptiom
	 */
	private Document generateDocumentFromInputStream(final InputStream pInputStream) throws JDOMException, IOException {

		final Document lDocument;

		lDocument = getSaxBuilder().build( pInputStream );
		pInputStream.close();
		return lDocument;
	}
}