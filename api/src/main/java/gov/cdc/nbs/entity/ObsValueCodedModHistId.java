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
public class ObsValueCodedModHistId implements Serializable {
    private static final long serialVersionUID = 7244931989241596803L;
    @Column(name = "observation_uid", nullable = false)
    private Long observationUid;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "code_mod_cd", nullable = false, length = 20)
    private String codeModCd;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ObsValueCodedModHistId entity = (ObsValueCodedModHistId) o;
        return Objects.equals(this.code, entity.code) &&
                Objects.equals(this.codeModCd, entity.codeModCd) &&
                Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.observationUid, entity.observationUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, codeModCd, versionCtrlNbr, observationUid);
    }

}