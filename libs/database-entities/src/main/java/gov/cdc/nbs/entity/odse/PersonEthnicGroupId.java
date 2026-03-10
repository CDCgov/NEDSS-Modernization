package gov.cdc.nbs.entity.odse;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
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
public class PersonEthnicGroupId implements Serializable {
  @Serial private static final long serialVersionUID = 6131564727043939572L;

  @Column(name = "person_uid", nullable = false)
  private Long personUid;

  @Column(name = "ethnic_group_cd", nullable = false, length = 20)
  private String ethnicGroupCd;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    PersonEthnicGroupId entity = (PersonEthnicGroupId) o;
    return Objects.equals(this.personUid, entity.personUid)
        && Objects.equals(this.ethnicGroupCd, entity.ethnicGroupCd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personUid, ethnicGroupCd);
  }
}
