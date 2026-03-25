package gov.cdc.nbs.entity.odse;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class EntityLocatorParticipationId implements Serializable {
  private static final long serialVersionUID = -6729514198431752991L;

  @Column(name = "entity_uid", nullable = false)
  private Long entityUid;

  @Column(name = "locator_uid", nullable = false)
  private Long locatorUid;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    EntityLocatorParticipationId entity = (EntityLocatorParticipationId) o;
    return Objects.equals(this.entityUid, entity.entityUid)
        && Objects.equals(this.locatorUid, entity.locatorUid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entityUid, locatorUid);
  }
}
