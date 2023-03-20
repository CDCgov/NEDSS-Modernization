package gov.cdc.nbs.graphql.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@JsonInclude(Include.NON_NULL)
public class PatientFilter {
  public PatientFilter() {
    this(RecordStatus.ACTIVE);
  }

  public PatientFilter(RecordStatus required, RecordStatus... recordStatus) {
    this.recordStatus = new ArrayList<>();
    this.recordStatus.add(required);
    if (recordStatus != null && recordStatus.length > 0) {
      Collections.addAll(this.recordStatus, recordStatus);
    }
  }

  private String id;
  private String lastName;
  private String firstName;
  private String race;
  private Identification identification;
  private String ssn;
  private String phoneNumber;
  private String email;
  private LocalDate dateOfBirth;
  private String dateOfBirthOperator;
  private Gender gender;
  private Deceased deceased;
  private String address;
  private String city;
  private String state;
  private String country;
  private String zip;
  private String mortalityStatus;
  private String ethnicity;
  private List<RecordStatus> recordStatus;
  private String treatmentId;
  private String vaccinationId;


  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Identification {
    private String identificationNumber;
    private String assigningAuthority;
    private String identificationType;
  }
}
