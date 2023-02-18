package gov.cdc.nbs.patientlistener.odse;

import java.io.Serializable;
import java.util.Objects;

/*
import jakarta.persistence.Column;
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