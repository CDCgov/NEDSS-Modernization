package gov.cdc.nbs.patient.file.demographics.ethnicity;

import gov.cdc.nbs.accumulation.CollectionMerge;
import gov.cdc.nbs.data.selectable.Selectable;

import java.util.Collection;

class PatientEthnicityDemographicMerger {

  static PatientEthnicityDemographic merge(final PatientEthnicityDemographic current,
      final PatientEthnicityDemographic next) {

    Collection<Selectable> detailed = CollectionMerge.merged(current.detailed(), next.detailed());

    return new PatientEthnicityDemographic(
        current.asOf(),
        current.ethnicGroup(),
        current.unknownReason(),
        detailed
    );
  }

  private PatientEthnicityDemographicMerger() {
    //  static
  }

}
