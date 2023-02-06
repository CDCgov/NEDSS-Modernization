package gov.cdc.nbs.entity.odse;

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
public class PersonRaceId implements Serializable {
    private static final long serialVersionUID = -8655697160777324427L;
    @Column(name = "person_uid", nullable = false)
    private Long personUid;

    @Column(name = "race_cd", nullable = false, length = 20)
    private String raceCd;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        PersonRaceId entity = (PersonRaceId) o;
        return Objects.equals(this.personUid, entity.personUid) &&
                Objects.equals(this.raceCd, entity.raceCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personUid, raceCd);
    }

}