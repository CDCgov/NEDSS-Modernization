package gov.cdc.nbs.patient.file.edit;

import gov.cdc.nbs.change.ChangeResolver;
import gov.cdc.nbs.change.Changes;
import gov.cdc.nbs.entity.odse.PatientRace;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographics.race.RaceDemographic;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static gov.cdc.nbs.patient.demographics.race.RaceDemographicPatientCommandMapper.asAddRace;

@Component
class PatientRaceEditService {

  private static final ChangeResolver<PatientRace, RaceDemographic, String> resolver =
      ChangeResolver.ofDifferingTypes(
          PatientRace::category,
          RaceDemographic::race
      );

  void apply(
      final RequestContext context,
      final Person patient,
      final Collection<RaceDemographic> demographics
  ) {

    Changes<PatientRace, RaceDemographic> changes = resolver.resolve(patient.race().categories(), demographics);

    changes.added()
        .map(demographic -> asAddRace(patient.id(), context, demographic))
        .forEach(patient::add);

    changes.altered()
        .map(match -> asUpdate(patient.getId(), context, match.right()))
        .forEach(patient::update);

    changes.removed()
        .map(existing -> asRemove(patient.id(), context, existing))
        .forEach(patient::delete);
  }


  public static PatientCommand.UpdateRaceInfo asUpdate(
      final long patient,
      final RequestContext context,
      final RaceDemographic demographic
  ) {

    return new PatientCommand.UpdateRaceInfo(
        patient,
        demographic.asOf(),
        demographic.race(),
        demographic.detailed(),
        context.requestedBy(),
        context.requestedAt()
    );
  }

  public static PatientCommand.DeleteRaceInfo asRemove(
      final long patient,
      final RequestContext context,
      final PatientRace existing
  ) {

    return new PatientCommand.DeleteRaceInfo(
        patient,
        existing.category(),
        context.requestedBy(),
        context.requestedAt()
    );
  }
}
