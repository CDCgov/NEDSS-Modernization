package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.message.PatientInput;
import gov.cdc.nbs.patient.PatientCommand;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue(TeleEntityLocatorParticipation.TELECOM_CLASS_CODE)
public class TeleEntityLocatorParticipation extends EntityLocatorParticipation {

    static final String TELECOM_CLASS_CODE = "TELE";

    @MapsId("locatorUid")
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            },
            optional = false
    )
    @JoinColumn(
            referencedColumnName = "tele_locator_uid",
            name = "locator_uid",
            updatable = false,
            insertable = false
    )
    private TeleLocator locator;

    public TeleEntityLocatorParticipation() {
    }

    public TeleEntityLocatorParticipation(
            final NBSEntity nbs,
            final EntityLocatorParticipationId identifier,
            final PatientCommand.AddPhoneNumber phoneNumber
    ) {
        super(phoneNumber, nbs, identifier);

        resolveCodes(phoneNumber.type());
        this.locator = new TeleLocator(phoneNumber);
    }

    private void resolveCodes(final PatientInput.PhoneType type) {
        switch (type) {
            case CELL -> {
                this.cd = "CP";
                this.useCd = "MC";
            }
            case HOME -> {
                this.cd = "PH";
                this.useCd = "H";
            }
            case WORK -> {
                this.cd = "PH";
                this.useCd = "WP";
            }
            default -> throw new IllegalArgumentException("Invalid PhoneType specified: " + type);
        }
    }

    public TeleEntityLocatorParticipation(
            final NBSEntity nbs,
            final EntityLocatorParticipationId identifier,
            final PatientCommand.AddEmailAddress emailAddress
    ) {
        super(emailAddress, nbs, identifier);

        this.cd = "NET";
        this.useCd = "h";

        this.locator = new TeleLocator(emailAddress);
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
