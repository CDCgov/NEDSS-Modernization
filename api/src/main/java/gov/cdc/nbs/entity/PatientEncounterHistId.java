package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class PatientEncounterHistId implements Serializable {
    private static final long serialVersionUID = -3079897926443328488L;
    @Column(name = "patient_encounter_uid", nullable = false)
    private Long patientEncounterUid;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        PatientEncounterHistId entity = (PatientEncounterHistId) o;
        return Objects.equals(this.patientEncounterUid, entity.patientEncounterUid) &&
                Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientEncounterUid, versionCtrlNbr);
    }

}