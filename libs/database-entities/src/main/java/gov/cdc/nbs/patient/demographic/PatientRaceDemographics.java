package gov.cdc.nbs.patient.demographic;

import gov.cdc.nbs.entity.odse.PatientRace;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.demographic.race.ExistingPatientRaceException;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Embeddable
public class PatientRaceDemographics {

  private static Predicate<PatientRace> inCategory(final String category) {
    return test -> Objects.equals(test.category(), category);
  }

  private static Predicate<PatientRace> identifiedBy(final String race) {
    return test -> Objects.equals(test.detail(), race);
  }

  private static Predicate<PatientRace> isCategory() {
    return test -> Objects.equals(test.category(), test.detail());
  }

  private static Predicate<PatientRace> isDetail() {
    return Predicate.not(isCategory());
  }

  @SuppressWarnings(
      //  The parent entity is needed to add races to
      "javaarchitecture:S7027")
  @Transient
  private Person patient;

  @OneToMany(
      mappedBy = "patient",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
      orphanRemoval = true)
  private List<PatientRace> races;

  protected PatientRaceDemographics() {
    this(null);
  }

  public PatientRaceDemographics(final Person patient) {
    this.patient = patient;
  }

  public PatientRaceDemographics patient(final Person patient) {
    this.patient = patient;
    return this;
  }

  public void add(final PatientCommand.AddRaceInfo added) {
    // Add a PersonRace for the category
    add(
        patient,
        new PatientCommand.AddRaceCategory(
            added.person(),
            added.asOf(),
            added.category(),
            added.requester(),
            added.requestedOn()));

    // Add a PersonRace for each detail
    added.detailed().stream()
        .map(
            detail ->
                new PatientCommand.AddDetailedRace(
                    added.person(),
                    added.asOf(),
                    added.category(),
                    detail,
                    added.requester(),
                    added.requestedOn()))
        .forEach(detail -> add(patient, detail));
  }

  private void add(final Person patient, final PatientCommand.AddRaceCategory added) {
    checkExistingRaceCategory(added.category());
    ensureRaces().add(new PatientRace(patient, added));
  }

  private void checkExistingRaceCategory(final String category) {
    ensureRaces().stream()
        .filter(inCategory(category))
        .findFirst()
        .ifPresent(existing -> existingRaceCategoryError(existing.category()));
  }

  private void existingRaceCategoryError(final String category) {
    throw new ExistingPatientRaceException(this.patient.id(), category);
  }

  private void add(final Person patient, final PatientCommand.AddDetailedRace added) {
    ensureRaces().add(new PatientRace(patient, added));
  }

  public void update(final Person patient, final PatientCommand.UpdateRaceInfo changes) {

    //  find all the races that are associated with the change
    Collection<PatientRace> associated =
        ensureRaces().stream().filter(inCategory(changes.category())).toList();

    //  apply changes to the as of date for existing races
    associated.stream()
        .filter(existing -> !Objects.equals(changes.asOf(), existing.asOf()))
        .forEach(race -> race.update(changes));

    Collection<String> existingDetails =
        associated.stream().filter(isDetail()).map(PatientRace::detail).toList();

    List<String> detailed = changes.detailed();

    ArrayList<String> added = new ArrayList<>(detailed);
    added.removeAll(existingDetails);

    //  add any new values
    added.stream()
        .map(
            detail ->
                new PatientCommand.AddDetailedRace(
                    changes.person(),
                    changes.asOf(),
                    changes.category(),
                    detail,
                    changes.requester(),
                    changes.requestedOn()))
        .forEach(detail -> add(patient, detail));

    ArrayList<String> removed = new ArrayList<>(existingDetails);
    removed.removeAll(detailed);

    //  remove any values that no longer apply
    removed.stream()
        .map(PatientRaceDemographics::identifiedBy)
        .reduce(Predicate::or)
        .ifPresent(criteria -> ensureRaces().removeIf(criteria));
  }

  public void delete(final PatientCommand.DeleteRaceInfo info) {
    // Race is deleted by category, the details should be removed also
    ensureRaces().removeIf(inCategory(info.category()));
  }

  private Collection<PatientRace> ensureRaces() {
    if (this.races == null) {
      this.races = new ArrayList<>();
    }
    return this.races;
  }

  public List<PatientRace> categories() {
    return ensureRaces().stream().filter(isCategory()).toList();
  }

  public List<PatientRace> details() {
    return this.races == null ? List.of() : List.copyOf(this.races);
  }
}
