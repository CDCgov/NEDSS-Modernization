package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientPostalLocatorHistoryListener;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@DiscriminatorValue(PostalEntityLocatorParticipation.POSTAL_CLASS_CODE)
@EntityListeners(PatientPostalLocatorHistoryListener.class)
@SuppressWarnings(
    //  Bidirectional mappings require knowledge of each other
    "javaarchitecture:S7027"
)
public class PostalEntityLocatorParticipation extends EntityLocatorParticipation {

  static final String POSTAL_CLASS_CODE = "PST";

  @MapsId("locatorUid")
  @OneToOne(fetch = FetchType.LAZY, cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE,
      CascadeType.REMOVE
  }, optional = false)
  @JoinColumn(referencedColumnName = "postal_locator_uid", name = "locator_uid", updatable = false, insertable = false)
  private PostalLocator locator;

  protected PostalEntityLocatorParticipation() {
  }

  public PostalEntityLocatorParticipation(
      final NBSEntity nbs,
      final EntityLocatorParticipationId identifier,
      final PatientCommand.AddAddress address
  ) {

    super(address, nbs, identifier);

    this.type = address.type();
    this.use = address.use();
    this.asOf = address.asOf();
    this.comment = address.comments();

    this.locator = new PostalLocator(address, identifier);
  }

  public PostalEntityLocatorParticipation(
      final NBSEntity nbs,
      final EntityLocatorParticipationId identifier,
      final PatientCommand.UpdateBirth birth
  ) {
    super(birth, nbs, identifier);
    this.type = "F";
    this.use = "BIR";
    this.asOf = birth.asOf();

    this.locator = new PostalLocator(identifier.getLocatorUid(), birth);
  }

  public PostalEntityLocatorParticipation(
      final NBSEntity nbs,
      final EntityLocatorParticipationId identifier,
      final PatientCommand.UpdateMortality mortality
  ) {
    super(mortality, nbs, identifier);
    this.type = "U";
    this.use = "DTH";
    this.asOf = mortality.asOf();

    this.locator = new PostalLocator(identifier.getLocatorUid(), mortality);
  }

  public void update(final PatientCommand.UpdateAddress update) {

    this.asOf = update.asOf();
    this.type = update.type();
    this.use = update.use();
    this.comment = update.comments();
    changed(update);

    this.locator.update(update);
  }

  public void delete(final PatientCommand.DeleteAddress deleted) {
    this.recordStatus().inactivate(deleted.requestedOn());
    changed(deleted);
  }

  public void update(final PatientCommand.UpdateBirth birth) {
    if (!Objects.equals(birth.asOf(), asOf)) {
      this.asOf = birth.asOf();
      changed(birth);
    }

    this.locator.update(birth);
  }

  public void clear(final PatientCommand.ClearBirthDemographics command) {
    inactivate(command);
  }

  public void update(final PatientCommand.UpdateMortality mortality) {
    if (!Objects.equals(mortality.asOf(), asOf)) {
      this.asOf = mortality.asOf();
      changed(mortality);
    }

    this.locator.update(mortality);
  }

  public void clear(final PatientCommand.ClearMoralityDemographics command) {
    inactivate(command);
  }

  @Override
  public PostalLocator locator() {
    return locator;
  }

  public void setLocator(final PostalLocator locator) {
    this.locator = locator;
  }

  @Override
  public String toString() {
    return "PostalEntityLocatorParticipation{" +
        "locator=" + locator +
        ", cd='" + type + '\'' +
        ", use='" + use + '\'' +
        '}';
  }

}
