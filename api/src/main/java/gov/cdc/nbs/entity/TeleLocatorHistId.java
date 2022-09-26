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
public class TeleLocatorHistId implements Serializable {
    private static final long serialVersionUID = 1225428701600969933L;
    @Column(name = "tele_locator_uid", nullable = false)
    private Long teleLocatorUid;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        TeleLocatorHistId entity = (TeleLocatorHistId) o;
        return Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr) &&
                Objects.equals(this.teleLocatorUid, entity.teleLocatorUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versionCtrlNbr, teleLocatorUid);
    }

}