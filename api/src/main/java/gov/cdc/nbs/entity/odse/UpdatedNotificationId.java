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
public class UpdatedNotificationId implements Serializable {
    private static final long serialVersionUID = -7375280817633629924L;
    @Column(name = "notification_uid", nullable = false)
    private Long notificationUid;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        UpdatedNotificationId entity = (UpdatedNotificationId) o;
        return Objects.equals(this.notificationUid, entity.notificationUid) &&
                Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationUid, versionCtrlNbr);
    }

}