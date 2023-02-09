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
public class ObsValueCodedId implements Serializable {
    private static final long serialVersionUID = 7553021853109481071L;
    @Column(name = "observation_uid", nullable = false)
    private Long observationUid;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ObsValueCodedId entity = (ObsValueCodedId) o;
        return Objects.equals(this.code, entity.code) &&
                Objects.equals(this.observationUid, entity.observationUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, observationUid);
    }

}