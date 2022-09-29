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
public class PersonNameHistId implements Serializable {
    private static final long serialVersionUID = -1881653145668918498L;
    @Column(name = "person_uid", nullable = false)
    private Long personUid;

    @Column(name = "person_name_seq", nullable = false)
    private Short personNameSeq;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        PersonNameHistId entity = (PersonNameHistId) o;
        return Objects.equals(this.personUid, entity.personUid) &&
                Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.personNameSeq, entity.personNameSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personUid, versionCtrlNbr, personNameSeq);
    }

}