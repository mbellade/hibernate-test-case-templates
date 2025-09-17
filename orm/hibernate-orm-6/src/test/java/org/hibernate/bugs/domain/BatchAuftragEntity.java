package org.hibernate.bugs.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementierung der Entity ZuweisungPseudo.
 */
@SuppressWarnings("serial")
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "DRV_BATCH_AUFTRAG")
@Cacheable(false)
public class BatchAuftragEntity extends AEntity {
	/** Konstante fuer Beziehungsname Job - SubJob. */
	private static final String RELATION_AUFTRAG_WORKER = "auftrag_hat_worker";
	/** Konstante fuer Beziehungsname Job - Jobknfiguation. */
	private static final String RELATION_AUFTRAG_CONFIG = "auftrag_hat_config";

	/**
	 * Default-Konstruktor.
	 * Dieser Konstruktor wird vom JPA-Manager verwendet, wenn ein gelesener Datensatz in die
	 * Entity ueberfuehrt werden soll.
	 */
	public BatchAuftragEntity() {
		super();
	}

	/**
	 * Spezial-Konstruktor.
	 * Dieser Konstruktor hat die Moeglichkeit, die Basisinformationen der Entity-Attribute per Introspection
	 * zu registrieren. Dieser Konstruktor wird typischerweise verwendet, wenn ein neues Entity-Objekt initialisiert,
	 * anschließend bearbeitet und zum Schluss gespeichert wird.
	 * @param pRegisterAttributes true, wenn die Basisattributemetainformationen mit registriert werden sollen
	 */
//	public BatchAuftragEntity(final boolean pRegisterAttributes) {
//		super(pRegisterAttributes);
//	}
//
//	/**
//	 * Spezial-Konstruktor.
//	 * Dieser Konstruktor wird verwendet, wenn neue oder bestehende Entity-Objekte in ihrer
//	 * XML-Repraesentation per RMI uebertragen werden.
//	 * @param pXml XML Repraesentation des FODataobjects.
//	 */
//	public BatchAuftragEntity(final String pXml) {
//		super(pXml);
//	}
//
//	/**
//	 * Konstruktor.
//	 * @param pXml XML Repraesentation des FODataobjects.
//	 */
//	public BatchAuftragEntity(final byte[] pXml) {
//		super(pXml);
//	}
//
//	/**
//	 * Konstruktor.
//	 * Konstruktor der aus dem XML-Root-Element die Entity und ggf. ihre abhaengigen Entities aufbaut.
//	 * @param pElem FO-XML-Repraesentation der Entity.
//	 */
//	public BatchAuftragEntity(final Element pElem) {
//		super(pElem);
//	}

	/**
	 * {@inheritDoc}
	 */
//	
//	@Transient
//	protected final DivaDataObjectType getEntityName() {
//		return DivaDataObjectType.ENTITY_BATCH_AUFTRAG;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	
//	@Transient
//	public String getStatusWertIntern() {
//		return getStatus();
//	}

	/**
	 * Belegung der technischen Id kurz vor der Persistierung.
	 * Die Methode wird automatisch vom JPA-Manager aufgerufen.
	 */
//	@PrePersist
//	protected void createOid() {
//		setAttributeValue("oid", getInstanceId());
//	}

	/**
	 * Callback:
	 * Nach einer XML-Deserialisierung muss die zu-1-Rueckrichtung der abhaengigen Entities noch
	 * nachgepflegt werden (typischerweise was immer in der zugehoerigen addXXX() Methode ebenfalls passiert).
	 */
//	
//	protected void initReverseRelations() {
//		for (final IBatchWorkerEntity lBatchWorker : getBatchWorker()) {
//			((BatchWorkerEntity) lBatchWorker).setBatchAuftrag(this);
//		}
//	}

	/**
	 * {@inheritDoc}
	 */
//	
	@Id
	@Column(name = "ID")
	public String getOid() {
		return (String) getAttributeValue("oid");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setOid(final String pOid) {
		setAttributeValue("oid", pOid);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Version
	@Column(name = "VERSION")
	public Long getVersion() {
		return (Long) getAttributeValue("version");
	}

	/**
	 * Belegung der Version.
	 * Die Methode wird vom JPA-Manager aufgerufen.
	 * @param pVersion Version
	 */
	public void setVersion(final Long pVersion) {
		setAttributeValue("version", pVersion);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "SEMA_IDENT")
	public String getSemaIdent() {
		return (String) getAttributeValue("semaIdent");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setSemaIdent(final String pSemaIdent) {
		setAttributeValue("semaIdent", pSemaIdent);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "KORREL_IDENT")
	public String getKorrelationsIdent() {
		return (String) getAttributeValue("korrelationsIdent");
	}

	/**
	 * {@inheritDoc}
	 */
	public void setKorrelationsIdent(final String pKorrelationsIdent) {
		setAttributeValue("korrelationsIdent", pKorrelationsIdent);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "ANLAGE_ZP")
	public Date getAnlageZeitpunkt() {
		return (Date) getAttributeValue("anlageZeitpunkt");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setAnlageZeitpunkt(final Date pZeitstmpl) {
		setAttributeValue("anlageZeitpunkt", pZeitstmpl);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "BENUTZERID")
	public String getBenutzerId() {
		return (String) getAttributeValue("benutzerId");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setBenutzerId(final String pBenutzerId) {
		setAttributeValue("benutzerId", pBenutzerId);
	}

	/**
	 * {@inheritDoc}
	 */
	
	// @Basic(fetch = FetchType.LAZY)
	@Column(name = "BENUTZERTOKEN")
	public String getBenutzerToken() {
		return (String) getAttributeValue("benutzerToken");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setBenutzerToken(final String pBenutzerToken) {
		setAttributeValue("benutzerToken", pBenutzerToken);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "KONTEXT_DATEN")
	public String getKontextDaten() {
		return (String) getAttributeValue("kontextDaten");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setKontextDaten(final String pKontextDaten) {
		setAttributeValue("kontextDaten", pKontextDaten);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "STATUS")
	public String getStatus() {
		return (String) getAttributeValue("status");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setStatus(final String pStatus) {
		setAttributeValue("status", pStatus);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "START_ZP")
	public Date getStartZeitpunkt() {
		return (Date) getAttributeValue("startZeitpunkt");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setStartZeitpunkt(final Date pZeitstmpl) {
		setAttributeValue("startZeitpunkt", pZeitstmpl);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "ENDE_ZP")
	public Date getEndeZeitpunkt() {
		return (Date) getAttributeValue("endeZeitpunkt");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setEndeZeitpunkt(final Date pZeitstmpl) {
		setAttributeValue("endeZeitpunkt", pZeitstmpl);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "ANZ_OBJ_OK")
	public Integer getAnzahlObjekteOk() {
		return (Integer) getAttributeValue("anzahlObjekteOk");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setAnzahlObjekteOk(final Integer pAnz) {
		setAttributeValue("anzahlObjekteOk", pAnz);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "ANZ_OBJ_FEH")
	public Integer getAnzahlObjekteFehler() {
		return (Integer) getAttributeValue("anzahlObjekteFehler");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setAnzahlObjekteFehler(final Integer pAnz) {
		setAttributeValue("anzahlObjekteFehler", pAnz);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "ANZ_WRK_GES")
	public Integer getAnzahlWorkerGesamt() {
		return (Integer) getAttributeValue("anzahlWorkerGesamt");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setAnzahlWorkerGesamt(final Integer pAnz) {
		setAttributeValue("anzahlWorkerGesamt", pAnz);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "ANZ_WRK_BEENDET")
	public Integer getAnzahlWorkerBeendet() {
		return (Integer) getAttributeValue("anzahlWorkerBeendet");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setAnzahlWorkerBeendet(final Integer pAnz) {
		setAttributeValue("anzahlWorkerBeendet", pAnz);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "GESEHEN_DAT")
	@Temporal(TemporalType.DATE)
	public Date getDatumGesehen() {
		return (Date) getAttributeValue("datumGesehen");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setDatumGesehen(final Date pGesehen) {
		setAttributeValue("datumGesehen", pGesehen);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "WIED_ZAEHLER")
	public Integer getWiederholungZaehler() {
		return (Integer) getAttributeValue("wiederholungZaehler");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setWiederholungZaehler(final Integer pZaehler) {
		setAttributeValue("wiederholungZaehler", pZaehler);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "WIED_MARKER")
	public Integer getWiederholungMarker() {
		return (Integer) getAttributeValue("wiederholungMarker");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setWiederholungMarker(final Integer pMarker) {
		setAttributeValue("wiederholungMarker", pMarker);
	}

	/**
	 * {@inheritDoc}
	 */
	
	// @Basic(fetch = FetchType.LAZY)
	@Column(name = "RETURN_DATEN")
	public String getReturnDaten() {
		return (String) getAttributeValue("returnDaten");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setReturnDaten(final String pReturnDaten) {
		setAttributeValue("returnDaten", pReturnDaten);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Column(name = "IN_POSTKORB")
	public Boolean isInPostkorb() {
		return (Boolean) getAttributeValue("inPostkorb");
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void setInPostkorb(final Boolean pInPostkorb) {
		setAttributeValue("inPostkorb", pInPostkorb);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@ManyToOne(optional = false)
	// @JoinColumn(name = "KENNUNG", referencedColumnName = "KENNUNG")
	public ToOneEntity getBatchConfig() {
		return null;
	}

	/**
	 * Zu getBatchConfig() gehoerender Setter mit Uebergabetyp JPA-Entity-Klasse. Diese Signatur
	 * wird von JPA benoetigt um das JPA-Metamodel (Query-API) verwenden zu koennen.
	 * @param pBatchConfig Batch-Konfiguration
	 */
	public void setBatchConfig(final ToOneEntity pBatchConfig) {

	}

	/**
	 * {@inheritDoc}
	 * Interface-konformer Setter zu getBatchConfig().
	 */
	
//	public void setBatchConfigEntity(final IBatchConfigEntity pBatchConfig) {
//		setBatchConfig((BatchConfigEntity) pBatchConfig);
//	}

	/**
	 * {@inheritDoc}
	 */

	// CHECKSTYLE:ON
	// @OrderBy("seqNr ASC")
	
	// CHECKSTYLE:OFF
	// Zeilenlaenge ueberschreitet Checkstyle-Limit
	@OneToMany(
			mappedBy = "batchAuftrag", targetEntity = ToManyEntity.class,
			cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY
	)
	public List<IToManyEntity> getBatchWorker() {
		return new ArrayList<>();
	}

	/**
	 * Methode, die fuer JPA notwendig ist, jedoch nicht zur Schnittstelle der Entity gehoert.
	 * @param pBatchWorker Batchworker-Liste
	 */
	public void setBatchWorker(final List<IToManyEntity> pBatchWorker) {
	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//
//	public void addBatchWorker(final IBatchWorkerEntity pBatchWorker) {
//		addRelatedFoDataObject(RELATION_AUFTRAG_WORKER, pBatchWorker);
//		((BatchWorkerEntity) pBatchWorker).setBatchAuftrag(this);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//
//	public IBatchWorkerEntity[] removeBatchWorker(final IBatchWorkerEntity[] pBatchWorker) {
//		final List<IBatchWorkerEntity> lBatchWorkerList = new ArrayList<>();
//		for (final IBatchWorkerEntity lBatchWorkerEntity : pBatchWorker) {
//			for (final IBatchWorkerEntity lCurrentBatchWorker : getBatchWorker()) {
//				if (lBatchWorkerEntity.getInstanceId().equals(lCurrentBatchWorker.getInstanceId())) {
//					removeRelatedEntity(RELATION_AUFTRAG_WORKER, lCurrentBatchWorker);
//					lBatchWorkerList.add(lCurrentBatchWorker);
//					break;
//				}
//			}
//		}
//		return lBatchWorkerList.toArray(new IBatchWorkerEntity[lBatchWorkerList.size()]);
//	}
}