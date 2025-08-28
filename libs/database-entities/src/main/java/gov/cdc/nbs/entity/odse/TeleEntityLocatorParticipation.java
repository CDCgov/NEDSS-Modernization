package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientPhoneLocatorHistoryListener;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue(TeleEntityLocatorParticipation.TELECOM_CLASS_CODE)
@EntityListeners(PatientPhoneLocatorHistoryListener.class)
@SuppressWarnings(
    //  Bidirectional mappings require knowledge of each other
    "javaarchitecture:S7027"
)
public class TeleEntityLocatorParticipation extends EntityLocatorParticipation {

  static final String TELECOM_CLASS_CODE = "TELE";

  @MapsId("locatorUid")
  @OneToOne(fetch = FetchType.LAZY, cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE,
      CascadeType.REMOVE
  }, optional = false)
  @JoinColumn(referencedColumnName = "tele_locator_uid", name = "locator_uid", updatable = false, insertable = false)
  private TeleLocator locator;

  protected TeleEntityLocatorParticipation() {
  }

  public TeleEntityLocatorParticipation(
      final NBSEntity nbs,
      final EntityLocatorParticipationId identifier,
      final PatientCommand.AddPhone phone
  ) {
    super(phone, nbs, identifier);

    this.cd = phone.type();
    this.useCd = phone.use();
    this.asOfDate = phone.asOf();
    this.locatorDescTxt = phone.comment();

    this.locator = new TeleLocator(phone, identifier);
  }

  public void update(final PatientCommand.UpdatePhone phone) {
    this.cd = phone.type();
    this.useCd = phone.use();
    this.asOfDate = phone.asOf();
    this.locatorDescTxt = phone.comment();

    this.locator.update(phone);

    changed(phone);
  }

  public void delete(final PatientCommand.DeletePhone deleted) {
    this.recordStatus.inactivate(deleted.requestedOn());
    changed(deleted);
  }

  @Override
  public TeleLocator getLocator() {
    return locator;
  }

  public void setLocator(final TeleLocator locator) {
    this.locator = locator;
  }

  @Override
  public String getClassCd() {
    return TELECOM_CLASS_CODE;
  }

  @Override
  public String toString() {
    return "TeleEntityLocatorParticipation{" +
        "locator=" + locator +
        ", cd='" + cd + '\'' +
        ", use='" + useCd + '\'' +
        '}';
  }
}
