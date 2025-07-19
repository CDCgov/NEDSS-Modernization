package gov.cdc.nbs.patient.demographic;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonEthnicGroup;
import gov.cdc.nbs.patient.PatientCommand;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Embeddable
public class PatientEthnicity {

  private static final String UNKNOWN = "UNK";

  @Column(name = "as_of_date_ethnicity")
  private LocalDate asOfDateEthnicity;
  @Column(name = "ethnic_group_ind", length = 20)
  private String ethnicGroupInd;

  @Column(name = "ethnic_unk_reason_cd", length = 20)
  private String ethnicUnkReasonCd;

  @OneToMany(mappedBy = "personUid", fetch = FetchType.LAZY, cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE,
      CascadeType.REMOVE
  }, orphanRemoval = true)
  private List<PersonEthnicGroup> ethnicities;

  public PatientEthnicity() {

  }

  public PatientEthnicity(final PatientCommand.AddPatient patient) {
    this.ethnicGroupInd = patient.ethnicityCode();

    if (this.ethnicGroupInd != null) {
      this.asOfDateEthnicity = patient.asOf();
    }
  }

  public LocalDate asOf() {
    return asOfDateEthnicity;
  }

  public String ethnicGroup() {
    return ethnicGroupInd;
  }

  public String unknownReason() {
    return ethnicUnkReasonCd;
  }

  public List<PersonEthnicGroup> ethnicities() {
    return ethnicities == null ? List.of() : List.copyOf(ethnicities);
  }

  public void update(final PatientCommand.UpdateEthnicityInfo info) {
    this.asOfDateEthnicity = info.asOf();
    this.ethnicGroupInd = info.ethnicGroup();

    if (Objects.equals(info.ethnicGroup(), UNKNOWN)) {
      this.ethnicUnkReasonCd = info.unknownReason();
      ensureEthnicities().clear();
    } else {
      this.ethnicUnkReasonCd = null;
    }
  }

  public Optional<PersonEthnicGroup> add(
      final Person patient,
      final PatientCommand.AddDetailedEthnicity add
  ) {

    if (this.ethnicGroupInd != null) {
      PersonEthnicGroup added = new PersonEthnicGroup(
          patient,
          add);

      ensureEthnicities().add(added);

      return Optional.of(added);
    } else {
      return Optional.empty();
    }
  }

  private List<PersonEthnicGroup> ensureEthnicities() {
    if (this.ethnicities == null) {
      this.ethnicities = new ArrayList<>();
    }
    return this.ethnicities;
  }

  public void remove(final PatientCommand.RemoveDetailedEthnicity remove) {
    this.ethnicities.removeIf(detail -> Objects.equals(detail.getId().getEthnicGroupCd(), remove.ethnicity()));
  }

  public long signature() {
    return Objects.hash(
        asOfDateEthnicity,
        ethnicGroupInd,
        ethnicUnkReasonCd
    );
  }
}
