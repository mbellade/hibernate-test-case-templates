package org.hibernate.bugs.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.type.NumericBooleanConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
@Table(name = "NI_NUTZUNGSINFORMATION")
@XmlRootElement
public class NiNutzungsinformation extends AbstractIdentifiableEntity {

    private static final long serialVersionUID = 7453895652968220773L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nutzungsinformation_id_seq")
    @SequenceGenerator(name = "nutzungsinformation_id_seq", sequenceName = "NI_NUTZUNGSINFORMATION_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TENS")
    private String tens;

    @Column(name = "KONFIDENZ")
    private BigDecimal konfidenz;

    @Column(name = "ERSTELL_ZEITPUNKT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date erstellZeitpunkt;

    @Column(name = "STORNO_ZEITPUNKT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stornoZeitpunkt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "NI_NUTZUNGSINFORMATION_BELEG", joinColumns = @JoinColumn(name = "NUTZUNGSINFORMATION_ID", nullable = false), inverseJoinColumns = @JoinColumn(name = "BELEG_ID", nullable = false))
    private Set<NiBeleg> niBelege = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nutzungsinformation", cascade = CascadeType.ALL)
    private Set<NiOrtungsvorgang> niOrtungsvorgang = new HashSet<>();

    @Column(name = "IGNORED")
    @Convert(converter = NumericBooleanConverter.class)
    private Boolean ignored;

    @Column(name = "NZV_FEHLT")
    @Convert(converter = NumericBooleanConverter.class)
    private Boolean nzvFehlt = Boolean.FALSE;

    @Column(name = "AENDERUNGSZEITSTEMPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date aenderungszeitstempel;

    @Version
    @Column(name = "VERSION")
    private long version;

    @PrePersist
    @PreUpdate
    public void updateAenderungszeitstempel() {
        setAenderungszeitstempel(new Date());
    }

    public NiNutzungsinformation() {
    }

    public NiNutzungsinformation(Long id) {
        this.id = id;
    }

    public NiNutzungsinformation(Long id, String tens, BigDecimal konfidenz, Date erstellZeitpunkt) {
        this.id = id;
        this.tens = tens;
        this.konfidenz = konfidenz;
        this.erstellZeitpunkt = erstellZeitpunkt;
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

    public BigDecimal getKonfidenz() {
        return konfidenz;
    }

    public void setKonfidenz(BigDecimal konfidenz) {
        this.konfidenz = konfidenz;
    }

    public Date getErstellZeitpunkt() {
        return erstellZeitpunkt;
    }

    public void setErstellZeitpunkt(Date erstellZeitpunkt) {
        this.erstellZeitpunkt = erstellZeitpunkt;
    }

    public Date getStornoZeitpunkt() {
        return stornoZeitpunkt;
    }

    public void setStornoZeitpunkt(Date stornoZeitpunkt) {
        this.stornoZeitpunkt = stornoZeitpunkt;
    }

//    public NiTraktionsleistung getTraktionsleistungId() {
//        return traktionsleistungId;
//    }
//
//    public void setTraktionsleistungId(NiTraktionsleistung traktionsleistungId) {
//        this.traktionsleistungId = traktionsleistungId;
//    }
//
//    public NiBeleg getActualMeldungBeleg() {
////        List<NiBeleg> sortedNiBelege = new ArrayList<NiBeleg>(getNiBelege());
////        Collections.sort(sortedNiBelege, Comparator.comparing(NiBeleg::getZeitstempel).reversed());
////        for (NiBeleg niBeleg : sortedNiBelege) {
////            if (Meldungsstatus.MELDUNG == niBeleg.getStatus()) {
////                return niBeleg;
////            }
////        }
//        return null;
//    }

    public Set<NiBeleg> getNiBelege() {
        return niBelege;
    }

    public boolean addNiBeleg(NiBeleg niBeleg) {
        return this.niBelege.add(niBeleg);
    }

    public boolean removeNiBeleg(NiBeleg niBeleg) {
        return this.niBelege.remove(niBeleg);
    }

    public void clearNiBeleg() {
        this.niBelege.clear();
    }

//    public Set<NiZuordnungsereignis> getNiZuordnungsereignisse() {
//        return niZuordnungsereignisse;
//    }
//
//    public boolean addNiZuordnungsereignis(NiZuordnungsereignis niZuordnungsereignis) {
//        return this.niZuordnungsereignisse.add(niZuordnungsereignis);
//    }
//
//    public boolean removeNiZuordnungsereignis(NiZuordnungsereignis niZuordnungsereignis) {
//        return this.niZuordnungsereignisse.remove(niZuordnungsereignis);
//    }
//
//    public void clearNiZuordnungsereignis() {
//        this.niZuordnungsereignisse.clear();
//    }
//
//    public Set<NiOrtungsereignis> getNiOrtungsereignisse() {
//        return niOrtungsereignisse;
//    }
//
//    public boolean addNiOrtungsereignis(NiOrtungsereignis niOrtungsereignis) {
//        return this.niOrtungsereignisse.add(niOrtungsereignis);
//    }
//
//    public boolean removeNiOrtungsereignis(NiOrtungsereignis niOrtungsereignis) {
//        return this.niOrtungsereignisse.remove(niOrtungsereignis);
//    }
//
//    public void clearNiOrtungsereignis() {
//        this.niOrtungsereignisse.clear();
//    }
//
//    public Set<NiTraktionsleistungsvorgang> getNiTraktionsleistungsvorgaenge() {
//        return niTraktionsleistungsvorgaenge;
//    }
//
//    public boolean addNiTraktionsleistungsvorgang(NiTraktionsleistungsvorgang niTraktionsleistungsvorgang) {
//        return this.niTraktionsleistungsvorgaenge.add(niTraktionsleistungsvorgang);
//    }
//
//    public boolean removeNiTraktionsleistungsvorgang(NiTraktionsleistungsvorgang niTraktionsleistungsvorgang) {
//        return this.niTraktionsleistungsvorgaenge.remove(niTraktionsleistungsvorgang);
//    }
//
//    public void clearNiTraktionsleistungsvorgang() {
//        this.niTraktionsleistungsvorgaenge.clear();
//    }

    public Set<NiOrtungsvorgang> getNiOrtungsvorgaenge() {
        return niOrtungsvorgang;
    }

    public boolean addNiOrtungsvorgang(NiOrtungsvorgang niOrtungsvorgang) {
        return this.niOrtungsvorgang.add(niOrtungsvorgang);
    }

    public boolean removeNiOrtungsvorgang(NiOrtungsvorgang niOrtungsvorgang) {
        return this.niOrtungsvorgang.remove(niOrtungsvorgang);
    }

    public void clearNiOrtungsvorgaenge() {
        this.niOrtungsvorgang.clear();
    }

    public Boolean getIgnored() {
        return ignored;
    }

    public void setIgnored(Boolean ignored) {
        this.ignored = ignored;
    }

    public Boolean getNzvFehlt() {
        return nzvFehlt;
    }

    public void setNzvFehlt(Boolean nzvFehlt) {
        this.nzvFehlt = nzvFehlt;
    }

//    public Set<NpZuordnungsabschnitt> getNpZuordnungsabschnitte() {
//        return npZuordnungsabschnitte;
//    }
//
//    public boolean addNpZuordnungsabschnitt(NpZuordnungsabschnitt npZuordnungsabschnitt) {
//        return this.npZuordnungsabschnitte.add(npZuordnungsabschnitt);
//    }
//
//    public boolean removeNpZuordnungsabschnitt(NpZuordnungsabschnitt npZuordnungsabschnitt) {
//        return this.npZuordnungsabschnitte.remove(npZuordnungsabschnitt);
//    }

//    public void clearNpZuordnungsabschnitt() {
//        this.npZuordnungsabschnitte.clear();
//    }

    public Date getAenderungszeitstempel() {
        return aenderungszeitstempel;
    }

    public void setAenderungszeitstempel(Date aenderungszeitstempel) {
        this.aenderungszeitstempel = aenderungszeitstempel;
    }

//    public NiAbweisungsgrund getAbweisungsgrund() {
//        return abweisungsgrund;
//    }
//
//    public void setAbweisungsgrund(NiAbweisungsgrund abweisungsgrund) {
//        this.abweisungsgrund = abweisungsgrund;
//    }

    public long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NiNutzungsinformation)) {
            return false;
        }
        NiNutzungsinformation other = (NiNutzungsinformation) obj;
        if (getId() == null || other.getId() == null) {
            return this == other;
        }
      	return Objects.equals(getId(), other.getId());
    }

    @Override
    public int hashCode() {
        if (getId() == null) {
            return System.identityHashCode(this);
        }
        return Objects.hashCode(getId());
    }
}
