package gov.cdc.nbs.entity.srte;

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
public class ZipcntyCodeValueId implements Serializable {
    private static final long serialVersionUID = -5769681767840856112L;
    @Column(name = "zip_code", nullable = false, length = 20)
    private String zipCode;

    @Column(name = "cnty_code", nullable = false, length = 20)
    private String cntyCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ZipcntyCodeValueId entity = (ZipcntyCodeValueId) o;
        return Objects.equals(this.cntyCode, entity.cntyCode) &&
                Objects.equals(this.zipCode, entity.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cntyCode, zipCode);
    }

}