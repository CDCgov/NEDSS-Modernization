package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientCommand.AddMortalityLocator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@Table(name = "Entity")
public class NBSEntity {
    @Id
    @Column(name = "entity_uid", nullable = false)
    private Long id;

    @Column(name = "class_cd", nullable = false, length = 10)
    private String classCd;

    @OneToMany(mappedBy = "id.subjectEntityUid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Participation> participations;

    @OneToMany(mappedBy = "id.entityUid", fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE
    }, orphanRemoval = true)
    private List<EntityLocatorParticipation> entityLocatorParticipations;

    protected NBSEntity() {
    }

    public NBSEntity(Long id, String classCd) {
        this.id = id;
        this.classCd = classCd;
    }

    public NBSEntity(final PatientCommand.AddPatient patient) {
        this(patient.person(), "PSN");
    }

    private List<EntityLocatorParticipation> ensureLocators() {
        if (this.entityLocatorParticipations == null) {
            this.entityLocatorParticipations = new ArrayList<>();
        }

        return this.entityLocatorParticipations;
    }

    public EntityLocatorParticipation add(
            final PatientCommand.AddAddress address) {

        List<EntityLocatorParticipation> locators = ensureLocators();

        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, address.id());

        EntityLocatorParticipation participation = new PostalEntityLocatorParticipation(
                this,
                identifier,
                address);

        locators.add(participation);

        return participation;
    }

    public Optional<EntityLocatorParticipation> update(
            final PatientCommand.UpdateAddress address) {

        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, address.id());
        List<EntityLocatorParticipation> existing = ensureLocators();
        Optional<EntityLocatorParticipation> elp = existing.stream()
                .filter(p -> p.getId() != null && p.getId().equals(identifier)).findFirst();
        if (elp.isPresent()) {
            PostalLocator pl = ((PostalEntityLocatorParticipation) elp.get()).getLocator();
            pl.setStreetAddr1(address.address1());
            pl.setStreetAddr2(address.address2());
            pl.setCensusTract(address.censusTract());
            pl.setCityCd(address.city().code());
            pl.setCntryCd(address.country().code());
            pl.setStateCd(address.state());
            pl.setZipCd(address.zip());
        }
        return elp;
    }

    public boolean delete(
            final PatientCommand.DeleteAddress address) {
        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, address.id());
        List<EntityLocatorParticipation> existing = ensureLocators();
        return existing.removeIf(p -> p.getId() != null && p.getId().equals(identifier));
    }

    public EntityLocatorParticipation add(final PatientCommand.AddPhoneNumber phoneNumber) {
        List<EntityLocatorParticipation> locators = ensureLocators();

        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, phoneNumber.id());

        EntityLocatorParticipation participation = new TeleEntityLocatorParticipation(
                this,
                identifier,
                phoneNumber);

        locators.add(participation);

        return participation;
    }

    public Optional<EntityLocatorParticipation> update(final PatientCommand.UpdatePhoneNumber phoneNumber) {
        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, phoneNumber.id());
        List<EntityLocatorParticipation> existing = ensureLocators();
        Optional<EntityLocatorParticipation> elp = existing.stream()
                .filter(p -> p.getId() != null && p.getId().equals(identifier)).findFirst();
        if (elp.isPresent()) {
            TeleLocator pl = ((TeleEntityLocatorParticipation) elp.get()).getLocator();
            pl.setPhoneNbrTxt(phoneNumber.number());
            pl.setExtensionTxt(phoneNumber.extension());
        }
        return elp;
    }

    public boolean delete(final PatientCommand.DeletePhoneNumber phoneNumber) {
        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, phoneNumber.id());
        List<EntityLocatorParticipation> existing = ensureLocators();
        return existing.removeIf(p -> p.getId() != null && p.getId().equals(identifier));
    }

    public EntityLocatorParticipation add(final PatientCommand.AddEmailAddress emailAddress) {
        List<EntityLocatorParticipation> locators = ensureLocators();

        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, emailAddress.id());

        EntityLocatorParticipation participation = new TeleEntityLocatorParticipation(
                this,
                identifier,
                emailAddress);

        locators.add(participation);

        return participation;
    }

    public Optional<EntityLocatorParticipation> update(final PatientCommand.UpdateEmailAddress emailAddress) {
        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, emailAddress.id());
        List<EntityLocatorParticipation> existing = ensureLocators();
        Optional<EntityLocatorParticipation> elp = existing.stream()
                .filter(p -> p.getId() != null && p.getId().equals(identifier)).findFirst();
        if (elp.isPresent()) {
            TeleLocator pl = ((TeleEntityLocatorParticipation) elp.get()).getLocator();
            pl.setEmailAddress(emailAddress.email());
        }
        return elp;
    }

    public boolean delete(final PatientCommand.DeleteEmailAddress emailAddress) {
        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, emailAddress.id());
        List<EntityLocatorParticipation> existing = ensureLocators();
        return existing.removeIf(p -> p.getId() != null && p.getId().equals(identifier));
    }

    public EntityLocatorParticipation add(AddMortalityLocator mortality) {
        List<EntityLocatorParticipation> locators = ensureLocators();
        if (locators.stream().anyMatch(l -> l.getUseCd().equals("DTH"))) {
            // a mortality locator already exists, do not allow adding another
            throw new UnsupportedOperationException("Unable to add more than one mortality locator");
        }
        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, mortality.id());

        EntityLocatorParticipation participation = new PostalEntityLocatorParticipation(
                this,
                identifier,
                mortality);

        locators.add(participation);

        return participation;
    }

    public boolean delete(final PatientCommand.DeleteMortalityLocator mortality) {
        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, mortality.id());
        List<EntityLocatorParticipation> existing = ensureLocators();
        return existing.removeIf(p -> p.getId() != null && p.getId().equals(identifier));
    }
}
