package gov.cdc.nbs.patient.profile.general;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.time.LocalDateInstantMapper;

import java.time.Instant;
import java.util.Optional;

public class GeneralInformationDemographicPatientCommandMapper {

  public static PatientCommand.UpdateGeneralInfo asUpdateGeneralInfo(
      final long patient,
      final RequestContext context,
      final GeneralInformationDemographic demographic
  ) {

    Instant asOf = LocalDateInstantMapper.from(demographic.asOf());

    return new PatientCommand.UpdateGeneralInfo(
        patient,
        asOf,
        demographic.maritalStatus(),
        demographic.maternalMaidenName(),
        demographic.adultsInResidence(),
        demographic.childrenInResidence(),
        demographic.primaryOccupation(),
        demographic.educationLevel(),
        demographic.primaryLanguage(),
        demographic.speaksEnglish(),
        context.requestedBy(),
        context.requestedAt()
    );

  }

  public static PatientCommand.AssociateStateHIVCase asAssociateStateHIVCase(
      final long patient,
      final RequestContext context,
      final GeneralInformationDemographic demographic
  ) {
    return new PatientCommand.AssociateStateHIVCase(
        patient,
        demographic.stateHIVCase(),
        context.requestedBy(),
        context.requestedAt()
    );
  }

  public static Optional<PatientCommand.AssociateStateHIVCase> maybeAsAssociateStateHIVCase(
      final long patient,
      final RequestContext context,
      final GeneralInformationDemographic demographic
  ) {
    return demographic.stateHIVCase() == null || demographic.stateHIVCase().isBlank()
        ? Optional.empty()
        : Optional.of(asAssociateStateHIVCase(patient, context, demographic));
  }

  private GeneralInformationDemographicPatientCommandMapper() {
    //  static
  }

}
