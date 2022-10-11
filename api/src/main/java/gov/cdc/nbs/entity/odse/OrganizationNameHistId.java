package gov.cdc.nbs.entity.odse;

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
public class OrganizationNameHistId implements Serializable {
    private static final long serialVersionUID = -8381217551705263777L;
    @Column(name = "organization_uid", nullable = false)
    private Long organizationUid;

    @Column(name = "organization_name_seq", nullable = false)
    private Short organizationNameSeq;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        OrganizationNameHistId entity = (OrganizationNameHistId) o;
        return Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.organizationNameSeq, entity.organizationNameSeq) &&
                Objects.equals(this.organizationUid, entity.organizationUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versionCtrlNbr, organizationNameSeq, organizationUid);
    }

}