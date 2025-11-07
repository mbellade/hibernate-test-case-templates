package org.hibernate.bugs.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "NI_ORTUNGSVORGANG")
@XmlRootElement
public class NiOrtungsvorgang extends AbstractIdentifiableEntity implements Serializable {

	private static final long serialVersionUID = -34234234741613784L;

	public static final String ISD_GEBIET_DEUTSCHLAND = "0080";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ortungsvorgang_id_seq")
	@SequenceGenerator(name = "ortungsvorgang_id_seq", sequenceName = "NI_ORTUNGSVORGANG_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;


	@Column(name = "ZEITPUNKT_BEGINN")
	@Temporal(TemporalType.TIMESTAMP)
	private Date zeitpunktBeginn;


	@Column(name = "ZEITPUNKT_ENDE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date zeitpunktEnde;


	@JoinColumn(name = "NUTZUNGSINFORMATION_ID", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	private NiNutzungsinformation nutzungsinformation;

	@Column(name = "STARTORT_LAENGSKOORDINATE")
	private String startOrtLaengskoordinate;

	@Column(name = "STARTORT_BREITKOORDINATE")
	private String startOrtBreitkoordinate;

	@Column(name = "ZIELORT_LAENGSKOORDINATE")
	private String zielOrtLaengskoordinate;

	@Column(name = "ZIELORT_BREITKOORDINATE")
	private String zielOrtBreitkoordinate;

//    @JoinColumn(name = "STARTORT_ID", referencedColumnName = "ID")
//    @OneToOne(fetch = FetchType.LAZY)
//    private IsdOertlichkeit startOrt;
//
//    @JoinColumn(name = "ZIELORT_ID", referencedColumnName = "ID")
//    @OneToOne(fetch = FetchType.LAZY)
//    private IsdOertlichkeit zielOrt;
//
//    @Column(name = "NETZSTATUS")
//    @Enumerated(EnumType.STRING)
//    private Netzstatus netzstatus;

	@Column(name = "AENDERUNGSGRUND")
	private String aenderungsgrund;

	@Column(name = "NEU")
	@Convert(converter = NumericBooleanConverter.class)
	private Boolean neu = Boolean.TRUE;

	@Column(name = "ERSTELL_ZEITPUNKT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date erstellZeitpunkt;

	@Column(name = "AENDERUNGSZEITSTEMPEL")
	@Temporal(TemporalType.TIMESTAMP)
	private Date aenderungszeitstempel;


	@Column(name = "SCHUTZVORGANG")
	@Convert(converter = NumericBooleanConverter.class)
	private Boolean schutzvorgang = Boolean.FALSE;

	@Column(name = "ISD_GEBIET")
	private String isdGebiet;

	@Column(name = "TENS")
	private String tens;

	@Version
	@Column(name = "VERSION")
	private long version;

	@Transient
	private BigDecimal konfidenz;

	@Transient
	private boolean gueStartOrt = false;

	@Transient
	private boolean gueZielOrt = false;

	public Date getAenderungszeitstempel() {
		return aenderungszeitstempel;
	}

	public void setAenderungszeitstempel(Date aenderungszeitstempel) {
		this.aenderungszeitstempel = aenderungszeitstempel;
	}

	@PostConstruct
	private void initializeErstellZeitpunkt() {
		setErstellZeitpunkt( new Date() );
	}

	@PreUpdate
	private void updatesForPreUpdate() {
		setAenderungszeitstempel( new Date() );
	}

	@PrePersist
	private void updatesForPrePersist() {
		setAenderungszeitstempel( new Date() );
		setTens();
	}

	public NiOrtungsvorgang(
			Date zeitpunktBeginn, Date zeitpunktEnde,
			String startOrtLaengskoordinate, String startOrtBreitkoordinate,
			String zielOrtLaengskoordinate, String zielOrtBreitkoordinate,
			String aenderungsgrund, NiNutzungsinformation nutzungsinformation, Boolean schutzvorgang,
			String isdGebiet) {

		this.zeitpunktBeginn = zeitpunktBeginn;
		this.zeitpunktEnde = zeitpunktEnde;
//        this.startOrt = startOrt;
		this.startOrtLaengskoordinate = startOrtLaengskoordinate;
		this.startOrtBreitkoordinate = startOrtBreitkoordinate;
//        this.zielOrt = zielOrt;
		this.zielOrtLaengskoordinate = zielOrtLaengskoordinate;
		this.zielOrtBreitkoordinate = zielOrtBreitkoordinate;
//        this.netzstatus = netzstatus;
		this.aenderungsgrund = aenderungsgrund;
		this.nutzungsinformation = nutzungsinformation;
		this.neu = Boolean.TRUE;
		this.erstellZeitpunkt = new Date();
		this.schutzvorgang = schutzvorgang;
		this.isdGebiet = isdGebiet;
		this.gueStartOrt = false;
		this.gueZielOrt = false;
	}

	public NiOrtungsvorgang() {
		this.erstellZeitpunkt = new Date();
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStartOrtLaengskoordinate() {
		return startOrtLaengskoordinate;
	}

	public void setStartOrtLaengskoordinate(String startOrtLaengskoordinate) {
		this.startOrtLaengskoordinate = startOrtLaengskoordinate;
	}

	public String getStartOrtBreitkoordinate() {
		return startOrtBreitkoordinate;
	}

	public void setStartOrtBreitkoordinate(String startOrtBreitkoordinate) {
		this.startOrtBreitkoordinate = startOrtBreitkoordinate;
	}

	public String getZielOrtLaengskoordinate() {
		return zielOrtLaengskoordinate;
	}

	public void setZielOrtLaengskoordinate(String zielOrtLaengskoordinate) {
		this.zielOrtLaengskoordinate = zielOrtLaengskoordinate;
	}

	public String getZielOrtBreitkoordinate() {
		return zielOrtBreitkoordinate;
	}

	public void setZielOrtBreitkoordinate(String zielOrtBreitkoordinate) {
		this.zielOrtBreitkoordinate = zielOrtBreitkoordinate;
	}

//    public IsdOertlichkeit getStartOrt() {
//        return startOrt;
//    }
//
//    public void setStartOrt(IsdOertlichkeit startOrt) {
//        this.startOrt = startOrt;
//    }
//
//    public IsdOertlichkeit getZielOrt() {
//        return zielOrt;
//    }
//
//    public void setZielOrt(IsdOertlichkeit zielOrt) {
//        this.zielOrt = zielOrt;
//    }
//
//    public Netzstatus getNetzstatus() {
//        return netzstatus;
//    }
//
//    public void setNetzstatus(Netzstatus netzstatus) {
//        if (netzstatus == Netzstatus.NETZINTERN) {
//            isdGebiet = "0080";
//        }
//        this.netzstatus = netzstatus;
//    }

	public String getAenderungsgrund() {
		return aenderungsgrund;
	}

	public void setAenderungsgrund(String aenderungsgrund) {
		this.aenderungsgrund = aenderungsgrund;
	}

	public Boolean getNeu() {
		return neu;
	}

	public void setNeu(Boolean neu) {
		this.neu = neu;
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

	public NiNutzungsinformation getNutzungsinformation() {
		return nutzungsinformation;
	}

	public void setNiNutzungsinformation(NiNutzungsinformation nutzungsinformation) {
		this.nutzungsinformation = nutzungsinformation;
	}

	public BigDecimal getKonfidenz() {
		return konfidenz;
	}

	public void setKonfidenz(BigDecimal konfidenz) {
		this.konfidenz = konfidenz;
	}

	public boolean isGueStartOrt() {
		return gueStartOrt;
	}

	public void setGueStartOrt(boolean gueStartOrt) {
		this.gueStartOrt = gueStartOrt;
	}

	public boolean isGueZielOrt() {
		return gueZielOrt;
	}

	public void setGueZielOrt(boolean gueZielOrt) {
		this.gueZielOrt = gueZielOrt;
	}

	public Date getErstellZeitpunkt() {
		return erstellZeitpunkt;
	}

	public void setErstellZeitpunkt(Date erstellZeitpunkt) {
		this.erstellZeitpunkt = erstellZeitpunkt;
	}

	public Boolean getSchutzvorgang() {
		return schutzvorgang;
	}

	public void setSchutzvorgang(Boolean schutzvorgang) {
		this.schutzvorgang = schutzvorgang;
	}

	public String getIsdGebiet() {
		return isdGebiet;
	}

	public void setIsdGebiet(String isdGebiet) {
//        if ("0080".equals(isdGebiet)) {
//            netzstatus = Netzstatus.NETZINTERN;
//        }
		this.isdGebiet = isdGebiet;
	}

	public long getVersion() {
		return version;
	}

	public String getTens() {
		return tens;
	}

	private void setTens() {
		this.tens = this.nutzungsinformation.getTens();
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null ) {
			return false;
		}
		if ( !( obj instanceof NiOrtungsvorgang ) ) {
			return false;
		}
		NiOrtungsvorgang other = (NiOrtungsvorgang) obj;
		if ( getId() == null || other.getId() == null ) {
			return this == other;
		}
		return Objects.equals( getId(), other.getId() );
	}

	@Override
	public int hashCode() {
		if ( getId() == null ) {
			return System.identityHashCode( this );
		}
		return Objects.hashCode( getId() );
	}

//	@Override
//	public String toString() {
//		return "NiOrtungsvorgang [id=" + id + ", zeitpunktBeginn=" + zeitpunktBeginn + ", zeitpunktEnde="
//				+ zeitpunktEnde + ", nutzungsinformation=" + nutzungsinformation + ", startOrtLaengskoordinate="
//				+ startOrtLaengskoordinate + ", startOrtBreitkoordinate=" + startOrtBreitkoordinate
//				+ ", zielOrtLaengskoordinate=" + zielOrtLaengskoordinate + ", zielOrtBreitkoordinate="
//				+ zielOrtBreitkoordinate + ", aenderungsgrund=" + aenderungsgrund + ", neu=" + neu + ", schutzvorgang="
//				+ schutzvorgang + ", isdGebiet=" + isdGebiet + ", erstellZeitpunkt=" + erstellZeitpunkt
//				+ ", aenderungszeitstempel=" + aenderungszeitstempel + ", version=" + version + ", konfidenz="
//				+ konfidenz + ", tens=" + tens + "]";
//	}

}
