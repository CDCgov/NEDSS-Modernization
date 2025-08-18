package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.RecordStatus;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientEthnicityHistoryListener;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Person_ethnic_group")
@SuppressWarnings(
    //  The PatientEthnicityHistoryListener is an entity listener specifically for instances of this class
    {"javaarchitecture:S7027", "javaarchitecture:S7091"}
)
@EntityListeners(PatientEthnicityHistoryListener.class)
public class PersonEthnicGroup {
  @EmbeddedId
  private PersonEthnicGroupId id;

  @MapsId("personUid")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "person_uid", nullable = false)
  private Person personUid;

  @Column(name = "ethnic_group_desc_txt", length = 100)
  private String ethnicGroupDescTxt;

  @Column(name = "user_affiliation_txt", length = 20)
  private String userAffiliationTxt;

  @Embedded
  private Audit audit;

  @Embedded
  private RecordStatus recordStatus;

  protected PersonEthnicGroup() {

  }

  public PersonEthnicGroup(
      final Person person,
      final PatientCommand.AddDetailedEthnicity added) {
    this.id = new PersonEthnicGroupId(
        person.id(),
        added.ethnicity());

    this.personUid = person;


    this.recordStatus = new RecordStatus(added.requestedOn());
    this.audit = new Audit(added.requester(), added.requestedOn());
  }

  public RecordStatus recordStatus() {
    return recordStatus;
  }

  public String ethnicGroup() {
    return this.id.getEthnicGroupCd();
  }

  @Override
  public String toString() {
    return "PersonEthnicGroup{" +
        "id=" + id +
        '}';
  }
}
