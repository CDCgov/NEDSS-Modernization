package gov.cdc.nbs.patient.profile.general;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;

import java.time.LocalDate;

public record GeneralInformationDemographic(
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class)
    LocalDate asOf,
    String maritalStatus,
    String maternalMaidenName,
    Integer adultsInResidence,
    Integer childrenInResidence,
    String primaryOccupation,
    String educationLevel,
    String primaryLanguage,
    String speaksEnglish,
    String stateHIVCase
) {

  public GeneralInformationDemographic(final LocalDate asOf) {
    this(
        asOf,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    );
  }

  public GeneralInformationDemographic withAsOf(final LocalDate value) {
    return new GeneralInformationDemographic(
        value,
        maritalStatus(),
        maternalMaidenName(),
        adultsInResidence(),
        childrenInResidence(),
        primaryOccupation(),
        educationLevel(),
        primaryLanguage(),
        speaksEnglish(),
        stateHIVCase()
    );
  }


  public GeneralInformationDemographic withMaritalStatus(final String value) {
    return new GeneralInformationDemographic(
        asOf(),
        value,
        maternalMaidenName(),
        adultsInResidence(),
        childrenInResidence(),
        primaryOccupation(),
        educationLevel(),
        primaryLanguage(),
        speaksEnglish(),
        stateHIVCase()
    );
  }

  public GeneralInformationDemographic withMaternalMaidenName(final String value) {
    return new GeneralInformationDemographic(
        asOf(),
        maritalStatus(),
        value,
        adultsInResidence(),
        childrenInResidence(),
        primaryOccupation(),
        educationLevel(),
        primaryLanguage(),
        speaksEnglish(),
        stateHIVCase()
    );
  }

  public GeneralInformationDemographic withAdultsInResidence(final int value) {
    return new GeneralInformationDemographic(
        asOf(),
        maritalStatus(),
        maternalMaidenName(),
        value,
        childrenInResidence(),
        primaryOccupation(),
        educationLevel(),
        primaryLanguage(),
        speaksEnglish(),
        stateHIVCase()
    );
  }

  public GeneralInformationDemographic withChildrenInResidence(final int value) {
    return new GeneralInformationDemographic(
        asOf(),
        maritalStatus(),
        maternalMaidenName(),
        adultsInResidence(),
        value,
        primaryOccupation(),
        educationLevel(),
        primaryLanguage(),
        speaksEnglish(),
        stateHIVCase()
    );
  }

  public GeneralInformationDemographic withPrimaryOccupation(final String value) {
    return new GeneralInformationDemographic(
        asOf(),
        maritalStatus(),
        maternalMaidenName(),
        adultsInResidence(),
        childrenInResidence(),
        value,
        educationLevel(),
        primaryLanguage(),
        speaksEnglish(),
        stateHIVCase()
    );
  }

  public GeneralInformationDemographic withEducationLevel(final String value) {
    return new GeneralInformationDemographic(
        asOf(),
        maritalStatus(),
        maternalMaidenName(),
        adultsInResidence(),
        childrenInResidence(),
        primaryOccupation(),
        value,
        primaryLanguage(),
        speaksEnglish(),
        stateHIVCase()
    );
  }

  public GeneralInformationDemographic withPrimaryLanguage(final String value) {
    return new GeneralInformationDemographic(
        asOf(),
        maritalStatus(),
        maternalMaidenName(),
        adultsInResidence(),
        childrenInResidence(),
        primaryOccupation(),
        educationLevel(),
        value,
        speaksEnglish(),
        stateHIVCase()
    );
  }

  public GeneralInformationDemographic withSpeaksEnglish(final String value) {
    return new GeneralInformationDemographic(
        asOf(),
        maritalStatus(),
        maternalMaidenName(),
        adultsInResidence(),
        childrenInResidence(),
        primaryOccupation(),
        educationLevel(),
        primaryLanguage(),
        value,
        stateHIVCase()
    );
  }

  public GeneralInformationDemographic withStateHIVCase(final String value) {
    return new GeneralInformationDemographic(
        asOf(),
        maritalStatus(),
        maternalMaidenName(),
        adultsInResidence(),
        childrenInResidence(),
        primaryOccupation(),
        educationLevel(),
        primaryLanguage(),
        speaksEnglish(),
        value
    );
  }
}
