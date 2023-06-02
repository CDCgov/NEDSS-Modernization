package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
