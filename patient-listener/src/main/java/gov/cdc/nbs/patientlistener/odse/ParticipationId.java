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
public class ParticipationId implements Serializable {
    private static final long serialVersionUID = 2886679967506817471L;
    @Column(name = "subject_entity_uid", nullable = false)
    private Long subjectEntityUid;

    @Column(name = "act_uid", nullable = false)
    private Long actUid;

    @Column(name = "type_cd", nullable = false, length = 50)
    private String typeCd;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ParticipationId entity = (ParticipationId) o;
        return Objects.equals(this.actUid, entity.actUid) &&
                Objects.equals(this.subjectEntityUid, entity.subjectEntityUid) &&
                Objects.equals(this.typeCd, entity.typeCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actUid, subjectEntityUid, typeCd);
    }

}