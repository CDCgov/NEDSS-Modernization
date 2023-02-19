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
public class RoleId implements Serializable {
    private static final long serialVersionUID = 7424615062089523937L;
    @Column(name = "subject_entity_uid", nullable = false)
    private Long subjectEntityUid;

    @Column(name = "role_seq", nullable = false)
    private Long roleSeq;

    @Column(name = "cd", nullable = false, length = 40)
    private String cd;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        RoleId entity = (RoleId) o;
        return Objects.equals(this.cd, entity.cd) &&
                Objects.equals(this.subjectEntityUid, entity.subjectEntityUid) &&
                Objects.equals(this.roleSeq, entity.roleSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cd, subjectEntityUid, roleSeq);
    }

}