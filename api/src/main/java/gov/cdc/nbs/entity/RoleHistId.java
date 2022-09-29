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
public class RoleHistId implements Serializable {
    private static final long serialVersionUID = -217545237166903338L;
    @Column(name = "subject_entity_uid", nullable = false)
    private Long subjectEntityUid;

    @Column(name = "role_seq", nullable = false)
    private Long roleSeq;

    @Column(name = "cd", nullable = false, length = 40)
    private String cd;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        RoleHistId entity = (RoleHistId) o;
        return Objects.equals(this.cd, entity.cd) &&
                Objects.equals(this.subjectEntityUid, entity.subjectEntityUid) &&
                Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.roleSeq, entity.roleSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cd, subjectEntityUid, versionCtrlNbr, roleSeq);
    }

}