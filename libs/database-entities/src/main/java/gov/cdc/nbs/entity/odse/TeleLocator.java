package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
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

    public TeleLocator() {
    }

    public TeleLocator(final PatientCommand.AddPhoneNumber phoneNumber) {
        super(phoneNumber);
        this.id = phoneNumber.id();
        this.phoneNbrTxt = phoneNumber.number();
        this.extensionTxt = phoneNumber.extension();
    }

    public TeleLocator(final PatientCommand.AddEmailAddress emailAddress) {
        super(emailAddress);
        this.id = emailAddress.id();
        this.emailAddress = emailAddress.email();
    }

    public TeleLocator(final PatientCommand.AddPhone phone) {
        super(phone);
        this.id = phone.id();
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

    @Override
    public String toString() {
        return "TeleLocator{" +
                "id=" + id +
                ", emailAddress='" + emailAddress + '\'' +
                ", extensionTxt='" + extensionTxt + '\'' +
                ", phoneNbrTxt='" + phoneNbrTxt + '\'' +
                '}';
    }
}
