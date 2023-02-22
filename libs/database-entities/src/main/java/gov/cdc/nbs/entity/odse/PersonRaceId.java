package gov.cdc.nbs.entity.odse;

import java.io.Serializable;
import java.util.Objects;


public class PersonRaceId implements Serializable {
    private static final long serialVersionUID = -8655697160777324427L;

    private long personUid;

    private String raceCd;

    protected PersonRaceId() {
    }

    public PersonRaceId(long personUid, String raceCd) {
        this.personUid = personUid;
        this.raceCd = raceCd;
    }

    public long getPersonUid() {
        return personUid;
    }

    public String getRaceCd() {
        return raceCd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonRaceId that = (PersonRaceId) o;
        return personUid == that.personUid && raceCd.equals(that.raceCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personUid, raceCd);
    }

    @Override
    public String toString() {
        return "PersonRaceId{" +
                "personUid=" + personUid +
                ", raceCd='" + raceCd + '\'' +
                '}';
    }
}