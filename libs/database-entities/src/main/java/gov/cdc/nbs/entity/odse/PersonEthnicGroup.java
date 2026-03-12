package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.RecordStatus;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientEthnicityHistoryListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Person_ethnic_group")
@EntityListeners(PatientEthnicityHistoryListener.class)
@SuppressWarnings(
    //  Bidirectional mappings require knowledge of each other
    "javaarchitecture:S7027")
public class PersonEthnicGroup implements Identifiable<PersonEthnicGroupId> {
  @EmbeddedId private PersonEthnicGroupId id;

  @MapsId("personUid")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "person_uid", nullable = false)
  private Person personUid;

  @Column(name = "ethnic_group_desc_txt", length = 100)
  private String ethnicGroupDescTxt;

  @Column(name = "user_affiliation_txt", length = 20)
  private String userAffiliationTxt;

  @Embedded private Audit audit;

  @Embedded private RecordStatus recordStatus;

  protected PersonEthnicGroup() {}

  public PersonEthnicGroup(final Person person, final PatientCommand.AddDetailedEthnicity added) {
    this.id = new PersonEthnicGroupId(person.id(), added.ethnicity());

    this.personUid = person;

    this.recordStatus = new RecordStatus(added.requestedOn());
    this.audit = new Audit(added.requester(), added.requestedOn());
  }

  @Override
  public PersonEthnicGroupId identifier() {
    return this.id;
  }

  public RecordStatus recordStatus() {
    return recordStatus;
  }

  public String ethnicGroup() {
    return this.id.getEthnicGroupCd();
  }

  @Override
  public String toString() {
    return "PersonEthnicGroup{" + "id=" + id + '}';
  }
}
