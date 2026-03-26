package gov.cdc.nbs.entity.odse;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class PersonNameId implements Serializable {

  public static PersonNameId from(final long patient, int sequence) {
    return new PersonNameId(patient, (short) sequence);
  }

  @Serial private static final long serialVersionUID = -6533992946080388101L;

  @Column(name = "person_uid", nullable = false)
  private Long personUid;

  @Column(name = "person_name_seq", nullable = false)
  private Short personNameSeq;

  public PersonNameId() {}

  public PersonNameId(long personUid, short personNameSeq) {
    this.personUid = personUid;
    this.personNameSeq = personNameSeq;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PersonNameId that = (PersonNameId) o;
    return Objects.equals(personUid, that.personUid)
        && Objects.equals(personNameSeq, that.personNameSeq);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personUid, personNameSeq);
  }

  @Override
  public String toString() {
    return "PersonNameId{" + "personUid=" + personUid + ", personNameSeq=" + personNameSeq + '}';
  }
}
