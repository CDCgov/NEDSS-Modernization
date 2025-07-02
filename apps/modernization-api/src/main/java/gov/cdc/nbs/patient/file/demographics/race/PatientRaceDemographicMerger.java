package gov.cdc.nbs.patient.file.demographics.race;

import gov.cdc.nbs.accumulation.CollectionMerge;
import gov.cdc.nbs.data.selectable.Selectable;

import java.util.Collection;

class PatientRaceDemographicMerger {

  static PatientRaceDemographic merge(final PatientRaceDemographic current, final PatientRaceDemographic next) {

    Collection<Selectable> detailed = CollectionMerge.merged(current.detailed(), next.detailed());

    return new PatientRaceDemographic(
        current.asOf(),
        current.race(),
        detailed
    );
  }

  private PatientRaceDemographicMerger() {
    // static
  }

}
