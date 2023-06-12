package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.patient.PatientCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.function.Predicate;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Person_race")
@IdClass(PersonRaceId.class)
public class PersonRace {

    public static Predicate<PersonRace> inCategory(final String category) {
        return test -> Objects.equals(test.getRaceCategoryCd(), category);
    }

    public static Predicate<PersonRace> identifiedBy(final String race) {
        return test -> Objects.equals(test.getRaceCd(), race);
    }

    public static Predicate<PersonRace> isDetail() {
        return test -> !Objects.equals(test.getRaceCategoryCd(), test.getRaceCd());
    }

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

    PersonRace() {

    }

    public PersonRace(
            final Person person,
            final PatientCommand.AddRaceCategory added
    ) {
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
        final PatientCommand.AddDetailedRace added
    ) {
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
