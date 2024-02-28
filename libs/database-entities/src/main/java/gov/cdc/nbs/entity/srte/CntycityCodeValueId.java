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
public class CntycityCodeValueId implements Serializable {
    private static final long serialVersionUID = -4288159949420948773L;
    @Column(name = "cnty_code", nullable = false, length = 20)
    private String cntyCode;

    @Column(name = "city_code", nullable = false, length = 20)
    private String cityCode;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        CntycityCodeValueId entity = (CntycityCodeValueId) o;
        return Objects.equals(this.cntyCode, entity.cntyCode) &&
                Objects.equals(this.cityCode, entity.cityCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cntyCode, cityCode);
    }

}
