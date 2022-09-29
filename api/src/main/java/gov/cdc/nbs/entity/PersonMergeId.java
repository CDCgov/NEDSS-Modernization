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
public class PersonMergeId implements Serializable {
    private static final long serialVersionUID = 905228520321239265L;
    @Column(name = "superced_person_uid", nullable = false)
    private Long supercedPersonUid;

    @Column(name = "superceded_version_ctrl_nbr", nullable = false)
    private Short supercededVersionCtrlNbr;

    @Column(name = "surviving_person_uid", nullable = false)
    private Long survivingPersonUid;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        PersonMergeId entity = (PersonMergeId) o;
        return Objects.equals(this.survivingPersonUid, entity.survivingPersonUid) &&
                Objects.equals(this.supercededVersionCtrlNbr, entity.supercededVersionCtrlNbr) &&
                Objects.equals(this.supercedPersonUid, entity.supercedPersonUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(survivingPersonUid, supercededVersionCtrlNbr, supercedPersonUid);
    }

}