package gov.cdc.nbs.patient.demographic;

import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.patient.GenderConverter;
import gov.cdc.nbs.patient.PatientCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class PatientSexBirth {

  @Column(name = "as_of_date_sex")
  private LocalDate asOf;

  @Column(name = "birth_time")
  private LocalDateTime birthday;

  @Column(name = "birth_time_calc")
  private LocalDateTime birthdayCalc;

  @Convert(converter = GenderConverter.class)
  @Column(name = "curr_sex_cd")
  private Gender gender;

  @Column(name = "sex_unk_reason_cd", length = 20)
  private String genderUnknownReason;

  @Column(name = "preferred_gender_cd", length = 20)
  private String preferredGender;

  @Column(name = "additional_gender_cd", length = 50)
  private String additionalGender;

  @Convert(converter = GenderConverter.class)
  @Column(name = "birth_gender_cd", columnDefinition = "CHAR")
  private Gender birthGender;

  @Column(name = "multiple_birth_ind", length = 20)
  private String multipleBirth;

  @Column(name = "birth_order_nbr")
  private Short birthOrder;

  public PatientSexBirth() {

  }

  private void resolveDateOfBirth(final LocalDate dateOfBirth) {
    if (dateOfBirth != null) {
      this.birthday = dateOfBirth.atStartOfDay();
    } else {
      this.birthday = null;
    }
    this.birthdayCalc = this.birthday;
  }

  public void update(final PatientCommand.UpdateBirth birth) {
    this.asOf = birth.asOf();
    resolveDateOfBirth(birth.bornOn());
    this.birthGender = Gender.resolve(birth.gender());
    this.multipleBirth = birth.multipleBirth();
    this.birthOrder = birth.birthOrder() == null ? null : birth.birthOrder().shortValue();
  }

  public void clear(final PatientCommand.ClearBirthDemographics ignored) {
    this.birthday = null;
    this.birthdayCalc = null;
    this.birthGender = null;
    this.multipleBirth = null;
    this.birthOrder = null;

    if(this.gender == null && this.preferredGender == null && this.genderUnknownReason == null && this.additionalGender == null) {
      this.asOf = null;
    }
  }

  public void update(final PatientCommand.UpdateGender changes) {

    this.asOf = changes.asOf();
    this.gender = Gender.resolve(changes.current());
    this.genderUnknownReason = this.gender == Gender.U ? changes.unknownReason() : null;
    this.preferredGender = changes.preferred();
    this.additionalGender = changes.additional();

  }

  public void clear(final PatientCommand.ClearGenderDemographics ignored) {
    this.gender = null;
    this.genderUnknownReason = null;
    this.preferredGender = null;
    this.additionalGender = null;

    if(this.birthday == null && this.birthdayCalc == null && this.birthGender == null && this.multipleBirth == null && this.birthOrder == null) {
      this.asOf = null;
    }
  }


  public LocalDate asOf() {
    return asOf;
  }

  public LocalDateTime birthday() {
    return birthday;
  }

  public Gender birthGender() {
    return birthGender;
  }

  public String multipleBirth() {
    return multipleBirth;
  }

  public Short birthOrder() {
    return birthOrder;
  }

  public Gender gender() {
    return gender;
  }

  public String genderUnknownReason() {
    return genderUnknownReason;
  }

  public String preferredGender() {
    return preferredGender;
  }

  public String additionalGender() {
    return additionalGender;
  }

  public long signature() {
    return Objects.hash(
        asOf,
        birthday,
        gender,
        genderUnknownReason,
        preferredGender,
        additionalGender,
        birthGender,
        multipleBirth,
        birthOrder
    );
  }
}
