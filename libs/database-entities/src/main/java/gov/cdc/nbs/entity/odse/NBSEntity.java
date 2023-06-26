package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
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

    @OneToMany(mappedBy = "id.entityUid", fetch = FetchType.LAZY, cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.REMOVE
    }, orphanRemoval = true)
    private List<EntityId> entityIds;

    @OneToOne(mappedBy = "contactNBSEntityUid", fetch = FetchType.LAZY, cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.REMOVE
    }, orphanRemoval = true)
    private CtContact ctContact;

    protected NBSEntity() {
    }

    public NBSEntity(Long id, String classCd) {
        this.id = id;
        this.classCd = classCd;
    }

    public NBSEntity(final PatientCommand.AddPatient patient) {
        this(patient.person(), "PSN");
    }

    public void update(
        final PatientCommand.UpdateBirth birth,
        final AddressIdentifierGenerator identifierGenerator
    ) {
        PostalEntityLocatorParticipation found = maybeBirthLocator()
            .orElseGet(() -> createBirthLocator(birth, identifierGenerator));

        found.update(birth);
    }

    private Optional<PostalEntityLocatorParticipation> maybeBirthLocator() {
        return this.ensureLocators()
            .stream()
            .filter(PostalEntityLocatorParticipation.class::isInstance)
            .map(PostalEntityLocatorParticipation.class::cast)
            .filter(participation -> Objects.equals("BIR", participation.getUseCd()))
            .findFirst();
    }

    private PostalEntityLocatorParticipation createBirthLocator(
        final PatientCommand.UpdateBirth changes,
        final AddressIdentifierGenerator identifierGenerator
    ) {
        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(
            this.id,
            identifierGenerator.generate()
        );

        PostalEntityLocatorParticipation participation = new PostalEntityLocatorParticipation(
            this,
            identifier,
            changes
        );

        ensureLocators().add(participation);

        return participation;
    }

    public void update(
        final PatientCommand.UpdateMortality info,
        final AddressIdentifierGenerator identifierGenerator
    ) {

        PostalEntityLocatorParticipation found = maybeMortalityLocator()
            .orElseGet(() -> createMortalityLocator(info, identifierGenerator));

        found.update(info);
    }

    private Optional<PostalEntityLocatorParticipation> maybeMortalityLocator() {
        return this.ensureLocators()
            .stream()
            .filter(PostalEntityLocatorParticipation.class::isInstance)
            .map(PostalEntityLocatorParticipation.class::cast)
            .filter(participation -> Objects.equals("DTH", participation.getUseCd()))
            .findFirst();
    }

    private PostalEntityLocatorParticipation createMortalityLocator(
        final PatientCommand.UpdateMortality info,
        final AddressIdentifierGenerator identifierGenerator
    ) {
        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(
            this.id,
            identifierGenerator.generate()
        );

        PostalEntityLocatorParticipation participation = new PostalEntityLocatorParticipation(
            this,
            identifier,
            info
        );

        ensureLocators().add(participation);

        return participation;
    }

    public EntityId add(final PatientCommand.AddIdentification added) {

        Collection<EntityId> existing = ensureEntityIds();
        EntityIdId identifier = new EntityIdId(this.id, (short) (existing.size() + 1));

        EntityId entityId = new EntityId(
            this,
            identifier,
            added
        );
        existing.add(entityId);

        return entityId;
    }

    private Collection<EntityId> ensureEntityIds() {
        if (this.entityIds == null) {
            this.entityIds = new ArrayList<>();
        }
        return this.entityIds;
    }

    public void update(final PatientCommand.UpdateIdentification info) {

        Collection<EntityId> existing = ensureEntityIds();
        EntityIdId identifier = new EntityIdId(info.person(), (short) info.id());

        existing.stream().filter(p -> p.getId() != null && p.getId().equals(identifier)).findFirst()
            .ifPresent(identification -> identification.update(info));

    }

    public void delete(final PatientCommand.DeleteIdentification info) {
        ensureEntityIds().removeIf(existing -> Objects.equals(existing.getId().getEntityIdSeq(), (short) info.id()));
    }

    public List<EntityId> getEntityIds() {
        return this.entityIds == null ? List.of() : List.copyOf(this.entityIds);
    }

    private List<EntityLocatorParticipation> ensureLocators() {
        if (this.entityLocatorParticipations == null) {
            this.entityLocatorParticipations = new ArrayList<>();
        }

        return this.entityLocatorParticipations;
    }

    public EntityLocatorParticipation add(final PatientCommand.AddAddress address) {

        List<EntityLocatorParticipation> locators = ensureLocators();

        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, address.id());

        EntityLocatorParticipation participation = new PostalEntityLocatorParticipation(
            this,
            identifier,
            address
        );

        locators.add(participation);

        return participation;
    }

    public void update(final PatientCommand.UpdateAddress changes) {
        this.ensureLocators().stream()
            .filter(PostalEntityLocatorParticipation.class::isInstance)
            .map(PostalEntityLocatorParticipation.class::cast)
            .filter(existing -> Objects.equals(existing.getId().getLocatorUid(), changes.id()))
            .findFirst()
            .ifPresent(existing -> existing.update(changes));
    }

    public void delete(final PatientCommand.DeleteAddress deleted) {
        this.ensureLocators().stream()
            .filter(PostalEntityLocatorParticipation.class::isInstance)
            .map(PostalEntityLocatorParticipation.class::cast)
            .filter(existing -> Objects.equals(existing.getId().getLocatorUid(), deleted.id()))
            .findFirst()
            .ifPresent(existing -> existing.delete(deleted));
    }

    public Collection<PostalEntityLocatorParticipation> addresses() {
        return this.ensureLocators().stream()
            .filter(EntityLocatorParticipation.active().and(PostalEntityLocatorParticipation.class::isInstance))
            .map(PostalEntityLocatorParticipation.class::cast)
            .toList();
    }

    public Collection<TeleEntityLocatorParticipation> phones() {
        return this.ensureLocators().stream()
            .map(TeleEntityLocatorParticipation.class::cast)
            .toList();
    }

    public Collection<TeleEntityLocatorParticipation> phoneNumbers() {
        return this.ensureLocators().stream()
            .filter(this::isPhoneNumber)
            .map(TeleEntityLocatorParticipation.class::cast)
            .toList();
    }

    private boolean isPhoneNumber(final EntityLocatorParticipation participation) {
        return participation instanceof TeleEntityLocatorParticipation && !Objects.equals(participation.cd, "NET");
    }

    public Collection<TeleEntityLocatorParticipation> emailAddress() {
        return this.ensureLocators().stream()
            .filter(this::isEmailAddress)
            .map(TeleEntityLocatorParticipation.class::cast)
            .toList();
    }

    private boolean isEmailAddress(final EntityLocatorParticipation participation) {
        return Objects.equals(participation.cd, "NET");
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
        List<EntityLocatorParticipation> elps = new ArrayList<>(this.entityLocatorParticipations);
        boolean isDeleted = elps.removeIf(p -> p.getId() != null && p.getId().equals(identifier));
        this.entityLocatorParticipations = elps;
        return isDeleted;
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
        List<EntityLocatorParticipation> elps = new ArrayList<>(this.entityLocatorParticipations);
        boolean isDeleted = elps.removeIf(p -> p.getId() != null && p.getId().equals(identifier));
        this.entityLocatorParticipations = elps;
        return isDeleted;
    }

    public EntityLocatorParticipation add(final PatientCommand.AddPhone phone) {
        List<EntityLocatorParticipation> locators = ensureLocators();

        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, phone.id());

        EntityLocatorParticipation participation = new TeleEntityLocatorParticipation(
            this,
            identifier,
            phone
        );

        locators.add(participation);

        return participation;
    }

    public void update(final PatientCommand.UpdatePhone phone) {
        this.ensureLocators().stream()
            .filter(TeleEntityLocatorParticipation.class::isInstance)
            .map(TeleEntityLocatorParticipation.class::cast)
            .filter(existing -> Objects.equals(existing.getId().getLocatorUid(), phone.id()))
            .findFirst()
            .ifPresent(existing -> existing.update(phone));
    }

    public void delete(final PatientCommand.DeletePhone phone) {
        this.ensureLocators().removeIf(existing -> Objects.equals(existing.getId().getLocatorUid(), phone.id()));
    }
}
