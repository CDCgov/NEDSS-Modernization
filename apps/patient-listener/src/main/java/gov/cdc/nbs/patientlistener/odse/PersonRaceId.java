package gov.cdc.nbs.patientlistener.odse;

import java.io.Serializable;
import java.util.Objects;

/*
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;*/

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import org.hibernate.Hibernate;

import gov.cdc.nbs.patientlistener.enums.Race;
import gov.cdc.nbs.patientlistener.enums.converter.RaceConverter;
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

    @Convert(converter = RaceConverter.class)
    @Column(name = "race_cd", nullable = false, length = 20)
    private Race raceCd;

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