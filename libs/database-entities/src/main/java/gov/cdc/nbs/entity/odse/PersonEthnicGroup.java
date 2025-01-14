package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientEthnicityHistoryListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Person_ethnic_group")
@SuppressWarnings(
    //  The PatientEthnicityHistoryListener is an entity listener specifically for instances of this class
    {"javaarchitecture:S7027","javaarchitecture:S7091"}
)
@EntityListeners(PatientEthnicityHistoryListener.class)
public class PersonEthnicGroup {
    @EmbeddedId
    private PersonEthnicGroupId id;

    @MapsId("personUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_uid", nullable = false)
    private Person personUid;

    @Column(name = "ethnic_group_desc_txt", length = 100)
    private String ethnicGroupDescTxt;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Embedded
    private Audit audit;

    protected PersonEthnicGroup() {

    }

    public PersonEthnicGroup(
            final Person person,
            final PatientCommand.AddDetailedEthnicity added) {
        this.id = new PersonEthnicGroupId(
                person.getId(),
                added.ethnicity());

        this.personUid = person;

        this.recordStatusCd = "ACTIVE";
        this.recordStatusTime = added.requestedOn();

        this.audit = new Audit(added.requester(), added.requestedOn());
    }

    @Override
    public String toString() {
        return "PersonEthnicGroup{" +
                "id=" + id +
                '}';
    }
}
