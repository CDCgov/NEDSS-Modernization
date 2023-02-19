package gov.cdc.nbs.patientlistener.odse;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.Hibernate;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class PersonNameId implements Serializable {
    private static final long serialVersionUID = -6533992946080388101L;
    @Column(name = "person_uid", nullable = false)
    private Long personUid;

    @Column(name = "person_name_seq", nullable = false)
    private Short personNameSeq;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        PersonNameId entity = (PersonNameId) o;
        return Objects.equals(this.personUid, entity.personUid) &&
                Objects.equals(this.personNameSeq, entity.personNameSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personUid, personNameSeq);
    }

}