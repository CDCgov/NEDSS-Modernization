package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.patient.PatientCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Person_ethnic_group")
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
