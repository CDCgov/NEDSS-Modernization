package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientPostalLocatorHistoryListener;
import jakarta.persistence.*;

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

    this.cd = address.type();
    this.useCd = address.use();
    this.asOfDate = address.asOf();
    this.locatorDescTxt = address.comments();

    this.locator = new PostalLocator(address, identifier);
  }

  public PostalEntityLocatorParticipation(
      final NBSEntity nbs,
      final EntityLocatorParticipationId identifier,
      final PatientCommand.UpdateBirth birth
  ) {
    super(birth, nbs, identifier);
    this.cd = "F";
    this.useCd = "BIR";
    this.asOfDate = birth.asOf();

    this.locator = new PostalLocator(identifier.getLocatorUid(), birth);
  }

  public PostalEntityLocatorParticipation(
      final NBSEntity nbs,
      final EntityLocatorParticipationId identifier,
      final PatientCommand.UpdateMortality mortality
  ) {
    super(mortality, nbs, identifier);
    this.cd = "U";
    this.useCd = "DTH";
    this.asOfDate = mortality.asOf();

    this.locator = new PostalLocator(identifier.getLocatorUid(), mortality);
  }

  public void update(final PatientCommand.UpdateAddress update) {

    this.asOfDate = update.asOf();
    this.cd = update.type();
    this.useCd = update.use();
    this.locatorDescTxt = update.comments();
    changed(update);

    this.locator.update(update);
  }

  public void delete(final PatientCommand.DeleteAddress deleted) {
    this.recordStatus().inactivate(deleted.requestedOn());
    changed(deleted);
  }

  public void update(final PatientCommand.UpdateBirth birth) {
    this.asOfDate = birth.asOf();
    this.locator.update(birth);
    changed(birth);
  }

  public void clear(final PatientCommand.ClearBirthDemographics command) {
    inactivate(command);
  }

  public void update(final PatientCommand.UpdateMortality mortality) {
    this.asOfDate = mortality.asOf();
    this.locator.update(mortality);
    changed(mortality);
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
        ", cd='" + cd + '\'' +
        ", use='" + useCd + '\'' +
        '}';
  }

}
