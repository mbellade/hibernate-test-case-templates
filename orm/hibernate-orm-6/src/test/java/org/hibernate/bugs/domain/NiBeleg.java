package org.hibernate.bugs.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Developer
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "NI_BELEG")
@XmlRootElement
public class NiBeleg extends AbstractIdentifiableEntity implements Serializable {

	private static final long serialVersionUID = 3292602478217271465L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beleg_id_seq")
	@SequenceGenerator(name = "beleg_id_seq", sequenceName = "NI_BELEG_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NUMMER")
	private String nummer;

	@Column(name = "ZEITSTEMPEL")
	@Temporal(TemporalType.TIMESTAMP)
	private Date zeitstempel;

	// TODO: Kann diese Spalte weg? Ist null in PRD
	@Column(name = "REFERENZ_BELEGNUMMER")
	private String referenzBelegnummer;


	@Column(name = "BETEILIGTER")
	private String beteiligter;

	@Column(name = "ERSTELL_ZEITPUNKT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date erstellZeitpunkt;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "niBelege", cascade = CascadeType.ALL)
	private Set<NiNutzungsinformation> niNutzungsinformationen = new HashSet<>();

	@Version
	@Column(name = "VERSION")
	private long version;

	public NiBeleg() {
	}

//    public NiBeleg(Long id) {
//        this.id = id;
//    }
//
//    public NiBeleg(Long id, String nummer, Date zeitstempel, BelegKategorie kategorie) {
//        this.id = id;
//        this.nummer = nummer;
//        this.zeitstempel = zeitstempel;
//        this.kategorie = kategorie;
//    }
//
//    public NiBeleg(String nummer, GKommunikationspartner sender, GKommunikationspartnerart senderArt,
//            GKommunikationspartner empfaenger, GKommunikationspartnerart empfaengerArt, String referenzBelegnummer,
//            BelegKategorie kategorie, Date zeitstempel, Meldungsstatus status) {
//        this.nummer = nummer;
//        this.sender = sender;
//        this.senderArt = senderArt;
//        this.referenzBelegnummer = referenzBelegnummer;
//        this.kategorie = kategorie;
//        this.zeitstempel = zeitstempel;
//        this.status = status;
//    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNummer() {
		return nummer;
	}

	public void setNummer(String nummer) {
		this.nummer = nummer;
	}

//    public GKommunikationspartner getSender() {
//        return sender;
//    }
//
//    public void setSender(GKommunikationspartner sender) {
//        this.sender = sender;
//    }
//
//    public GKommunikationspartnerart getSenderArt() {
//        return senderArt;
//    }
//
//    public void setSenderArt(GKommunikationspartnerart senderArt) {
//        this.senderArt = senderArt;
//    }

	public Date getZeitstempel() {
		return zeitstempel;
	}

	public void setZeitstempel(Date zeitstempel) {
		this.zeitstempel = zeitstempel;
	}

//    public BelegKategorie getKategorie() {
//        return kategorie;
//    }
//
//    public void setKategorie(BelegKategorie kategorie) {
//        this.kategorie = kategorie;
//    }
//
//    public Meldungsstatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(Meldungsstatus status) {
//        this.status = status;
//    }
//
//    public String getReferenzBelegnummer() {
//        return referenzBelegnummer;
//    }
//
//    public void setReferenzBelegnummer(String referenzBelegnummer) {
//        this.referenzBelegnummer = referenzBelegnummer;
//    }
//
//    public GKommunikationspartnerart getBeteiligterArt() {
//        return beteiligterArt;
//    }
//
//    public void setBeteiligterArt(GKommunikationspartnerart beteiligterArt) {
//        this.beteiligterArt = beteiligterArt;
//    }

	public String getBeteiligter() {
		return beteiligter;
	}

	public void setbeteiligter(String beteiligter) {
		this.beteiligter = beteiligter;
	}

	public Date getErstellZeitpunkt() {
		return erstellZeitpunkt;
	}

	public void setErstellZeitpunkt(Date erstellZeitpunkt) {
		this.erstellZeitpunkt = erstellZeitpunkt;
	}

	@PrePersist
	public void updateErstellZeitpunkt() {
		setErstellZeitpunkt( new Date() );
	}

	public Set<NiNutzungsinformation> getNiNutzungsinformationen() {
		return niNutzungsinformationen;
	}

	public boolean addNiNutzungsinformation(NiNutzungsinformation niNutzungsinformation) {
		return this.niNutzungsinformationen.add( niNutzungsinformation );
	}

	public boolean removeNiNutzungsinformation(NiNutzungsinformation niNutzungsinformation) {
		return this.niNutzungsinformationen.remove( niNutzungsinformation );
	}

	public void clearNiNutzungsinformation() {
		this.niNutzungsinformationen.clear();
	}

//    public Set<NiRangierfahrtinformation> getNiRangierfahrtinformationen() {
//        return niRangierfahrtinformationen;
//    }
//
//    public boolean addNiRangierfahrtinformation(NiRangierfahrtinformation niRangierfahrtinformation) {
//        return this.niRangierfahrtinformationen.add(niRangierfahrtinformation);
//    }
//
//    public boolean removeNiRangierfahrtinformation(NiRangierfahrtinformation niRangierfahrtinformation) {
//        return this.niRangierfahrtinformationen.remove(niRangierfahrtinformation);
//    }
//
//    public void clearNiRangierfahrtinformation() {
//        this.niRangierfahrtinformationen.clear();
//    }
//
//    public Set<NiZugfahrtinformation> getNiZugfahrtinformationen() {
//        return niZugfahrtinformationen;
//    }
//
//    public boolean addNiZugfahrtinformation(NiZugfahrtinformation niZugfahrtinformation) {
//        return this.niZugfahrtinformationen.add(niZugfahrtinformation);
//    }
//
//    public boolean removeNiZugfahrtinformation(NiZugfahrtinformation niZugfahrtinformation) {
//        return this.niZugfahrtinformationen.remove(niZugfahrtinformation);
//    }
//
//    public void clearNiZugfahrtinformation() {
//        this.niZugfahrtinformationen.clear();
//    }

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
		if ( !( obj instanceof NiBeleg ) ) {
			return false;
		}
		NiBeleg other = (NiBeleg) obj;
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

}
