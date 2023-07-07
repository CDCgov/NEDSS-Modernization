package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class PublicHealthCaseHistId implements Serializable {
    private static final long serialVersionUID = 8327350690479721653L;
    @Column(name = "public_health_case_uid", nullable = false)
    private Long publicHealthCaseUid;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        PublicHealthCaseHistId entity = (PublicHealthCaseHistId) o;
        return Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.publicHealthCaseUid, entity.publicHealthCaseUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versionCtrlNbr, publicHealthCaseUid);
    }

}
