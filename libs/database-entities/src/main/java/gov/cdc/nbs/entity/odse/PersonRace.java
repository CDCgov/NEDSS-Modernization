package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientRaceHistoryListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Person_race")
@IdClass(PersonRaceId.class)
@SuppressWarnings(
    //  The PatientRaceHistoryListener is an entity listener specifically for instances of this class
    {"javaarchitecture:S7027","javaarchitecture:S7091"}
)
@EntityListeners(PatientRaceHistoryListener.class)
public class PersonRace {

    @Id
    @Column(name = "race_cd", nullable = false, length = 20)
    private String raceCd;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_uid", nullable = false)
    private Person personUid;

    @Column(name = "race_category_cd", length = 20)
    private String raceCategoryCd;

    @Column(name = "race_desc_txt", length = 100)
    private String raceDescTxt;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Column(name = "as_of_date")
    private Instant asOfDate;

    @Embedded
    private Audit audit;

    protected PersonRace() {

    }

    public PersonRace(
            final Person person,
            final PatientCommand.AddRaceCategory added) {
        this.personUid = person;
        this.asOfDate = added.asOf();
        this.raceCategoryCd = added.category();
        this.raceCd = added.category();

        this.recordStatusCd = "ACTIVE";
        this.recordStatusTime = added.requestedOn();

        this.audit = new Audit(added.requester(), added.requestedOn());

    }

    public PersonRace(
            final Person person,
            final PatientCommand.AddDetailedRace added) {
        this.personUid = person;
        this.asOfDate = added.asOf();
        this.raceCategoryCd = added.category();
        this.raceCd = added.race();

        this.recordStatusCd = "ACTIVE";
        this.recordStatusTime = added.requestedOn();

        this.audit = new Audit(added.requester(), added.requestedOn());

    }

    public PersonRace update(final PatientCommand.UpdateRaceInfo changed) {
        this.asOfDate = changed.asOf();

        this.audit.changed(changed.requester(), changed.requestedOn());
        return this;
    }

    @Override
    public String toString() {
        return "PersonRace{" +
                "raceCd='" + raceCd + '\'' +
                ", raceCategoryCd='" + raceCategoryCd + '\'' +
                ", asOfDate=" + asOfDate +
                '}';
    }
}
