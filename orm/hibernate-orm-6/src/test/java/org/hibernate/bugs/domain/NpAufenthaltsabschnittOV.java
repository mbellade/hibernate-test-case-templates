package org.hibernate.bugs.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.type.NumericBooleanConverter;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Developer
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "NP_AUFENTHALTSABSCHNITT_OV")
@XmlRootElement
public class NpAufenthaltsabschnittOV extends AbstractIdentifiableEntity implements Serializable {

	private static final long serialVersionUID = 8116410423155649319L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aufenthaltsabschnitt_ov_id_seq")
	@SequenceGenerator(name = "aufenthaltsabschnitt_ov_id_seq", sequenceName = "NP_AUFENTHALTSABSCHNITT_OV_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "TENS")
	private String tens;

	@Column(name = "ZEITPUNKT_BEGINN")
	@Temporal(TemporalType.TIMESTAMP)
	private Date zeitpunktBeginn;

	@Column(name = "ZEITPUNKT_ENDE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date zeitpunktEnde;

	@Column(name = "STATUS")
	private String status; // "netzintern", "netzextern", "unbestimmt", "unbekannt"

	@Column(name = "ISD_GEBIET")
	private String isdGebiet; // UIC from ISD_OERTLICHKEIT (e.g. "0080")


	@Column(name = "NEU")
	@Convert(converter = NumericBooleanConverter.class)
	private Boolean neu = Boolean.TRUE; // flag for plausi to evaluate the newly

	@JoinColumn(name = "OV_ID", referencedColumnName = "ID")
	@OneToOne(fetch = FetchType.LAZY)
	private NiOrtungsvorgang ortungsvorgang;

	@Column(name = "ERSTELL_ZEITPUNKT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date erstellZeitpunkt;

	@Column(name = "AENDERUNGSZEITSTEMPEL")
	@Temporal(TemporalType.TIMESTAMP)
	private Date aenderungszeitstempel;

	@Version
	@Column(name = "VERSION", nullable = false)
	private long version;

	public NpAufenthaltsabschnittOV() {
		this.erstellZeitpunkt = new Date();
	}

	public NpAufenthaltsabschnittOV(Long id) {
		this.id = id;
		this.erstellZeitpunkt = new Date();
	}

	public NpAufenthaltsabschnittOV(
			Long id, String tens,
			Date zeitpunktBeginn, Date zeitpunktEnde, String status,
			String isdGebiet, Boolean neu, NiOrtungsvorgang ortungsvorgang) {
		super();
		this.id = id;
		this.tens = tens;
		this.zeitpunktBeginn = zeitpunktBeginn;
		this.zeitpunktEnde = zeitpunktEnde;
		this.status = status;
		this.isdGebiet = isdGebiet;
		this.neu = neu;
		this.ortungsvorgang = ortungsvorgang;
	}

	@PostConstruct
	private void initializeErstellZeitpunkt() {
		setErstellZeitpunkt( new Date() );
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTens() {
		return tens;
	}

	public void setTens(String tens) {
		this.tens = tens;
	}

	public Date getZeitpunktBeginn() {
		return zeitpunktBeginn;
	}

	public void setZeitpunktBeginn(Date zeitpunktBeginn) {
		this.zeitpunktBeginn = zeitpunktBeginn;
	}

	public Date getZeitpunktEnde() {
		return zeitpunktEnde;
	}

	public void setZeitpunktEnde(Date zeitpunktEnde) {
		this.zeitpunktEnde = zeitpunktEnde;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAenderungszeitstempel() {
		return aenderungszeitstempel;
	}

	public void setAenderungszeitstempel(Date aenderungszeitstempel) {
		this.aenderungszeitstempel = aenderungszeitstempel;
	}

	@PrePersist
	@PreUpdate
	public void updateAenderungszeitstempel() {
		setAenderungszeitstempel( new Date() );
	}

	public String getIsdGebiet() {
		return isdGebiet;
	}

	public void setIsdGebiet(String isdGebiet) {
		this.isdGebiet = isdGebiet;
	}

	public Boolean getNeu() {
		return neu;
	}

	public void setNeu(Boolean neu) {
		this.neu = neu;
	}

	public Date getErstellZeitpunkt() {
		return erstellZeitpunkt;
	}

	public void setErstellZeitpunkt(Date erstellZeitpunkt) {
		this.erstellZeitpunkt = erstellZeitpunkt;
	}

	public NiOrtungsvorgang getOrtungsvorgang() {
		return ortungsvorgang;
	}

	public void setOrtungsvorgang(NiOrtungsvorgang ortungsvorgang) {
		this.ortungsvorgang = ortungsvorgang;
	}

	public long getVersion() {
		return version;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null ) {
			return false;
		}
		if ( !( obj instanceof NpAufenthaltsabschnittOV ) ) {
			return false;
		}
		NpAufenthaltsabschnittOV other = (NpAufenthaltsabschnittOV) obj;
		return Objects.equals( getId(), other.getId() );
	}

	@Override
	public int hashCode() {
		if ( getId() == null ) {
			return System.identityHashCode( this );
		}
		return Objects.hash( getId() );
	}

	@Override
	public String toString() {
		return "NpAufenthaltsabschnittOV [id=" + id + ", tens=" + tens + ", zeitpunktBeginn=" + zeitpunktBeginn
				+ ", zeitpunktEnde=" + zeitpunktEnde + ", status=" + status + ", ov_id= " + ortungsvorgang
				+ ", isdGebiet=" + isdGebiet + ", neu = " + neu + ", erstellZeitpunkt=" + erstellZeitpunkt
				+ ", aenderungszeitstempel=" + aenderungszeitstempel + ", version=" + version + "]";
	}

}
