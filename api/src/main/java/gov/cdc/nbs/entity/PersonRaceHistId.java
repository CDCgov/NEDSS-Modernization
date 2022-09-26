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
public class PersonRaceHistId implements Serializable {
    private static final long serialVersionUID = -4996466220899268581L;
    @Column(name = "person_uid", nullable = false)
    private Long personUid;

    @Column(name = "race_cd", nullable = false, length = 20)
    private String raceCd;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        PersonRaceHistId entity = (PersonRaceHistId) o;
        return Objects.equals(this.personUid, entity.personUid) &&
                Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.raceCd, entity.raceCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personUid, versionCtrlNbr, raceCd);
    }

}