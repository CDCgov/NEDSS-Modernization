package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tele_locator")
public class TeleLocator extends Locator {

  @Id
  @Column(name = "tele_locator_uid", nullable = false, updatable = false)
  private Long id;

  @Column(name = "cntry_cd", length = 20)
  private String cntryCd;

  @Column(name = "email_address", length = 100)
  private String emailAddress;

  @Column(name = "extension_txt", length = 20)
  private String extensionTxt;

  @Column(name = "phone_nbr_txt", length = 20)
  private String phoneNbrTxt;

  @Column(name = "url_address", length = 100)
  private String urlAddress;

  @Column(name = "user_affiliation_txt", length = 20)
  private String userAffiliationTxt;

  protected TeleLocator() {}

  public TeleLocator(
      final PatientCommand.AddPhone phone, final EntityLocatorParticipationId identifier) {
    super(phone);
    this.id = identifier.getLocatorUid();
    this.cntryCd = phone.countryCode();
    this.phoneNbrTxt = phone.number();
    this.extensionTxt = phone.extension();
    this.emailAddress = phone.email();
    this.urlAddress = phone.url();
  }

  public void update(final PatientCommand.UpdatePhone phone) {
    this.cntryCd = phone.countryCode();
    this.phoneNbrTxt = phone.number();
    this.extensionTxt = phone.extension();
    this.emailAddress = phone.email();
    this.urlAddress = phone.url();

    changed(phone);
  }

  public Long identifier() {
    return id;
  }

  public String countryCode() {
    return cntryCd;
  }

  public String email() {
    return emailAddress;
  }

  public String extension() {
    return extensionTxt;
  }

  public String phoneNumber() {
    return phoneNbrTxt;
  }

  public String url() {
    return urlAddress;
  }

  @Override
  public String toString() {
    return "TeleLocator{"
        + "id="
        + id
        + ", emailAddress='"
        + emailAddress
        + '\''
        + ", extensionTxt='"
        + extensionTxt
        + '\''
        + ", phoneNbrTxt='"
        + phoneNbrTxt
        + '\''
        + '}';
  }
}
