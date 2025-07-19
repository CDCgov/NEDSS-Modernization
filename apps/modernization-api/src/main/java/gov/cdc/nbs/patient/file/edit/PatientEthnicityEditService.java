package gov.cdc.nbs.patient.file.edit;

import gov.cdc.nbs.change.ChangeResolver;
import gov.cdc.nbs.change.Changes;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonEthnicGroup;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographics.ethnicity.EthnicityDemographic;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static gov.cdc.nbs.patient.demographics.ethnicity.EthnicityPatientCommandMapper.asAddDetailedEthnicity;
import static gov.cdc.nbs.patient.demographics.ethnicity.EthnicityPatientCommandMapper.asUpdateEthnicityInfo;

@Component
class PatientEthnicityEditService {

  private final ChangeResolver<PersonEthnicGroup, String, String> resolver = ChangeResolver.ofDifferingTypes(
      PersonEthnicGroup::ethnicGroup,
      Function.identity()
  );


  void apply(
      final RequestContext context,
      final Person patient,
      final EthnicityDemographic demographic
  ) {
    //  apply any changes to the base ethnicity

    patient.update(asUpdateEthnicityInfo(patient.getId(), context, demographic));

    //  resolve the changes for the details
    Changes<PersonEthnicGroup, String> changes =
        resolver.resolve(patient.getEthnicity().ethnicities(), demographic.detailed());

    changes.added()
        .map(detail -> asAddDetailedEthnicity(patient.getId(), context, detail))
        .forEach(patient::add);

    changes.removed()
        .map(detail -> asRemoveDetailedEthnicity(patient.getId(), context, detail.ethnicGroup()))
        .forEach(patient::remove);
  }

  private static PatientCommand.RemoveDetailedEthnicity asRemoveDetailedEthnicity(
      final long patient,
      final RequestContext context,
      final String detail
  ) {
    return new PatientCommand.RemoveDetailedEthnicity(
        patient,
        detail,
        context.requestedBy(),
        context.requestedAt()
    );
  }
}
