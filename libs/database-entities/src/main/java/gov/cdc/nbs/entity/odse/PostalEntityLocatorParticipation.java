package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("PST")
public class PostalEntityLocatorParticipation extends EntityLocatorParticipation {

    @MapsId("locatorUid")
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            },
            optional = false)
    @JoinColumn(
            referencedColumnName = "postal_locator_uid",
            name = "locator_uid",
            updatable = false,
            insertable = false)
    private PostalLocator locator;

    public PostalEntityLocatorParticipation() {}

    public PostalEntityLocatorParticipation(
            final NBSEntity nbs,
            final EntityLocatorParticipationId identifier,
            final PatientCommand.AddAddress address) {

        super(address, nbs, identifier);

        this.cd = "H";
        this.useCd = "H";

        this.locator = new PostalLocator(address);
    }

    public PostalEntityLocatorParticipation(final NBSEntity nbsEntity,
            final EntityLocatorParticipationId identifier,
            final PatientCommand.AddMortalityLocator add) {
        super(add, nbsEntity, identifier);
        this.setAsOfDate(add.asOf());
        this.cd = "U";
        this.useCd = "DTH";
        this.locator = new PostalLocator(add);
    }

    public void updateMortalityLocator(PatientCommand.UpdateMortalityLocator update) {
        this.locator.update(update);
        this.setLastChgTime(update.requestedOn());
        this.setLastChgUserId(update.requester());
        this.setAsOfDate(update.asOf());
        this.setVersionCtrlNbr((short) (getVersionCtrlNbr() + 1));
    }

    @Override
    public PostalLocator getLocator() {
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
