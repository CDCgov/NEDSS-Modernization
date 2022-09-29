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
public class ConfirmationMethodId implements Serializable {
    private static final long serialVersionUID = -4950496696849544013L;
    @Column(name = "public_health_case_uid", nullable = false)
    private Long publicHealthCaseUid;

    @Column(name = "confirmation_method_cd", nullable = false, length = 20)
    private String confirmationMethodCd;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ConfirmationMethodId entity = (ConfirmationMethodId) o;
        return Objects.equals(this.confirmationMethodCd, entity.confirmationMethodCd) &&
                Objects.equals(this.publicHealthCaseUid, entity.publicHealthCaseUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(confirmationMethodCd, publicHealthCaseUid);
    }

}