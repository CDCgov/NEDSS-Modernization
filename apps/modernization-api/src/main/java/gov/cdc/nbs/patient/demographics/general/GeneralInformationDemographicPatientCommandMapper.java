package gov.cdc.nbs.patient.demographics.general;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

import java.util.Optional;

public class GeneralInformationDemographicPatientCommandMapper {

  public static PatientCommand.UpdateGeneralInfo asUpdateGeneralInfo(
      final long patient,
      final RequestContext context,
      final GeneralInformationDemographic demographic
  ) {

    return new PatientCommand.UpdateGeneralInfo(
        patient,
        demographic.asOf(),
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

  public static PatientCommand.ClearGeneralInformationDemographics asClearGeneralInformationDemographics(
      final long patient,
      final RequestContext context
  ) {
    return new PatientCommand.ClearGeneralInformationDemographics(
        patient,
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

  public static PatientCommand.DisassociateStateHIVCase asDisassociateStateHIVCase(
      final long patient,
      final RequestContext context
  ) {
    return new PatientCommand.DisassociateStateHIVCase(
        patient,
        context.requestedBy(),
        context.requestedAt()
    );
  }

  private GeneralInformationDemographicPatientCommandMapper() {
    //  static
  }

}
