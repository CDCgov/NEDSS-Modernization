package gov.cdc.nbs.patient.demographic;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonEthnicGroup;
import gov.cdc.nbs.patient.PatientCommand;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class PatientEthnicity {

    @Column(name = "as_of_date_ethnicity")
    private Instant asOfDateEthnicity;
    @Column(name = "ethnic_group_ind", length = 20)
    private String ethnicGroupInd;

    @Column(name = "ethnic_unk_reason_cd", length = 20)
    private String ethnicUnkReasonCd;

    @OneToMany(
        mappedBy = "personUid", fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE
        },
        orphanRemoval = true
    )
    private List<PersonEthnicGroup> ethnicities;

    public PatientEthnicity() {

    }

    public PatientEthnicity(final PatientCommand.AddPatient patient) {
        this.asOfDateEthnicity = patient.asOf();
        this.ethnicGroupInd = patient.ethnicityCode();
    }

    public Instant asOf() {
        return asOfDateEthnicity;
    }

    public String ethnicGroup() {
        return ethnicGroupInd;
    }

    public String unknownReason() {
        return ethnicUnkReasonCd;
    }

    public List<PersonEthnicGroup> ethnicities() {
        return ethnicities;
    }

    public void update(final PatientCommand.UpdateEthnicityInfo info) {
        this.ethnicGroupInd = info.ethnicity();
        this.ethnicUnkReasonCd = info.unknownReason();
    }

    public PersonEthnicGroup add(
        final Person patient,
        final PatientCommand.AddDetailedEthnicity added
    ) {

        PersonEthnicGroup ethnicity = new PersonEthnicGroup(
            patient,
            added
        );

        ensureEthnicities().add(ethnicity);

        return ethnicity;
    }

    private List<PersonEthnicGroup> ensureEthnicities() {
        if (this.ethnicities == null) {
            this.ethnicities = new ArrayList<>();
        }
        return this.ethnicities;
    }

}
