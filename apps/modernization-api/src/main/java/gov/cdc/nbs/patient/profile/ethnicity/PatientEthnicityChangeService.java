package gov.cdc.nbs.patient.profile.ethnicity;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import gov.cdc.nbs.message.patient.input.EthnicityInput;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.PatientEthnicity;
import gov.cdc.nbs.patient.event.PatientEventEmitter;
import gov.cdc.nbs.patient.profile.PatientProfileService;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PatientEthnicityChangeService {

  private final PatientEventEmitter emitter;
  private final PatientProfileService service;

  PatientEthnicityChangeService(
      final PatientEventEmitter emitter, PatientProfileService service) {
    this.emitter = emitter;
    this.service = service;
  }


  public void update(final RequestContext context, final EthnicityInput input) {

    service.with(input.getPatient(), found -> update(found, context, input))
        .map(this::asChanged)
        .ifPresent(this.emitter::emit);
  }

  private Person update(final Person patient, final RequestContext context, final EthnicityInput input) {
    patient.update(
        new PatientCommand.UpdateEthnicityInfo(
            input.getPatient(),
            input.getAsOf(),
            input.getEthnicGroup(),
            input.getUnknownReason(),
            context.requestedBy(),
            context.requestedAt()));

    List<String> detailed = input.getDetailed();

    List<String> existing = patient.getEthnicity().ethnicities()
        .stream()
        .map(group -> group.getId().getEthnicGroupCd())
        .toList();


    ArrayList<String> added = new ArrayList<>(detailed);
    added.removeAll(existing);

    added.stream()
        .map(detail -> asAdded(context, input.getPatient(), detail))
        .forEach(patient::add);

    ArrayList<String> removed = new ArrayList<>(existing);
    removed.removeAll(detailed);

    removed.stream()
        .map(detail -> asRemove(context, input.getPatient(), detail))
        .forEach(patient::remove);

    return patient;
  }

  private PatientCommand.AddDetailedEthnicity asAdded(
      final RequestContext context,
      final long patient,
      final String detailed) {
    return new PatientCommand.AddDetailedEthnicity(
        patient,
        detailed,
        context.requestedBy(),
        context.requestedAt());
  }

  private PatientCommand.RemoveDetailedEthnicity asRemove(
      final RequestContext context,
      final long patient,
      final String detailed) {
    return new PatientCommand.RemoveDetailedEthnicity(
        patient,
        detailed,
        context.requestedBy(),
        context.requestedAt());
  }

  private PatientEvent asChanged(final Person patient) {
    PatientEthnicity ethnicity = patient.getEthnicity();

    List<String> detailed = ethnicity.ethnicities()
        .stream()
        .map(group -> group.getId().getEthnicGroupCd())
        .toList();

    return new PatientEvent.EthnicityChanged(
        patient.getId(),
        patient.getLocalId(),
        ethnicity.asOf(),
        ethnicity.ethnicGroup(),
        ethnicity.unknownReason(),
        detailed,
        patient.audit().changed().changedBy(),
        patient.audit().changed().changedOn());
  }

}
