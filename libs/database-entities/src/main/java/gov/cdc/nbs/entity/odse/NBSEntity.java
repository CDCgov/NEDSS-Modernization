package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.demographic.phone.PhoneIdentifierGenerator;
import jakarta.persistence.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Entity
@Table(name = "Entity")
@SuppressWarnings("javaarchitecture:S7027") //  Bidirectional mappings require knowledge of each other
public class NBSEntity {
  @Id
  @Column(name = "entity_uid", nullable = false)
  private Long id;

  @Column(name = "class_cd", nullable = false, length = 10)
  private String classCd;

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

  protected NBSEntity() {
  }

  public NBSEntity(final Long id, final String classCd) {
    this.id = id;
    this.classCd = classCd;
  }

  public void update(
      final PatientCommand.UpdateBirth birth,
      final AddressIdentifierGenerator identifierGenerator
  ) {

    //  does the command include a location?
    boolean hasLocation =
        birth.city() != null || birth.county() != null || birth.state() != null || birth.country() != null;

    if (hasLocation) {
      //  if so make sure the location values are updated
      maybeBirthLocator()
          .orElseGet(() -> createBirthLocator(birth, identifierGenerator))
          .update(birth);
    } else {
      //  inactivate the existing locator
      maybeBirthLocator()
          .ifPresent(
              locator -> locator.delete(
                  new PatientCommand.DeleteAddress(
                      locator.identifier().getEntityUid(),
                      locator.identifier().getLocatorUid(),
                      birth.requester(),
                      birth.requestedOn()
                  )
              )
          );
    }

  }

  private Optional<PostalEntityLocatorParticipation> maybeBirthLocator() {
    return this.ensureLocators()
        .stream()
        .filter(PostalEntityLocatorParticipation.class::isInstance)
        .map(PostalEntityLocatorParticipation.class::cast)
        .filter(EntityLocatorParticipation.withUse("BIR").and(EntityLocatorParticipation.active()))
        .findFirst();
  }

  private PostalEntityLocatorParticipation createBirthLocator(
      final PatientCommand.UpdateBirth changes,
      final AddressIdentifierGenerator identifierGenerator) {
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

  public void clear(final PatientCommand.ClearBirthDemographics command) {
    maybeBirthLocator().ifPresent(locator -> locator.clear(command));
  }

  public void update(
      final PatientCommand.UpdateMortality mortality,
      final AddressIdentifierGenerator identifierGenerator
  ) {

    //  does the command include a location?
    boolean hasLocation =
        mortality.city() != null || mortality.county() != null || mortality.state() != null || mortality.country() != null;

    //  is the patient deceased?
    boolean deceased = Objects.equals(mortality.deceased(), "Y");

    if (deceased && hasLocation) {
      //  if so make sure the location values are updated
      maybeMortalityLocator()
          .orElseGet(() -> createMortalityLocator(mortality, identifierGenerator))
          .update(mortality);
    } else {
      //  inactivate the existing locator
      maybeMortalityLocator()
          .ifPresent(
              locator -> locator.delete(
                  new PatientCommand.DeleteAddress(
                      locator.identifier().getEntityUid(),
                      locator.identifier().getLocatorUid(),
                      mortality.requester(),
                      mortality.requestedOn()
                  )
              )
          );
    }

  }

  private Optional<PostalEntityLocatorParticipation> maybeMortalityLocator() {
    return this.ensureLocators()
        .stream()
        .filter(PostalEntityLocatorParticipation.class::isInstance)
        .map(PostalEntityLocatorParticipation.class::cast)
        .filter(EntityLocatorParticipation.withUse("DTH").and(EntityLocatorParticipation.active()))
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

  public void clear(final PatientCommand.ClearMoralityDemographics command) {
    maybeMortalityLocator().ifPresent(locator -> locator.clear(command));
  }

  public EntityId add(final PatientCommand.AddIdentification added) {

    Collection<EntityId> existing = ensureEntityIds();
    EntityIdId identifier = new EntityIdId(this.id, (short) (existing.size() + 1));

    EntityId entityId = new EntityId(
        this,
        identifier,
        added);
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

    existing.stream().filter(p -> Objects.equals(p.identifier(), identifier)).findFirst()
        .ifPresent(identification -> identification.update(info));

  }

  public void delete(final PatientCommand.DeleteIdentification deleted) {
    Collection<EntityId> existing = ensureEntityIds();
    EntityIdId identifier = new EntityIdId(deleted.person(), (short) deleted.id());

    existing.stream().filter(p -> Objects.equals(p.identifier(), identifier)).findFirst()
        .ifPresent(identification -> identification.delete(deleted));
  }

  public List<EntityId> identifications() {
    return this.entityIds == null ? List.of() : List.copyOf(this.entityIds);
  }

  private List<EntityLocatorParticipation> ensureLocators() {
    if (this.entityLocatorParticipations == null) {
      this.entityLocatorParticipations = new ArrayList<>();
    }

    return this.entityLocatorParticipations;
  }

  public EntityLocatorParticipation add(
      final PatientCommand.AddAddress address,
      final AddressIdentifierGenerator generator
  ) {

    List<EntityLocatorParticipation> locators = ensureLocators();

    EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, generator.generate());

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
        .filter(existing -> Objects.equals(existing.identifier().getLocatorUid(), changes.id()))
        .findFirst()
        .ifPresent(existing -> existing.update(changes));
  }

  public void delete(final PatientCommand.DeleteAddress deleted) {
    this.ensureLocators().stream()
        .filter(PostalEntityLocatorParticipation.class::isInstance)
        .map(PostalEntityLocatorParticipation.class::cast)
        .filter(existing -> Objects.equals(existing.identifier().getLocatorUid(), deleted.id()))
        .findFirst()
        .ifPresent(existing -> existing.delete(deleted));
  }

  List<PostalEntityLocatorParticipation> addresses() {
    return this.ensureLocators().stream().flatMap(
        obj -> (obj instanceof PostalEntityLocatorParticipation postal) ?
            Stream.of(postal) :
            Stream.empty()
    ).toList();
  }

  public Stream<PostalEntityLocatorParticipation> addresses(final Predicate<EntityLocatorParticipation> filter) {
    return this.ensureLocators().stream()
        .filter(EntityLocatorParticipation.active())
        .flatMap(
            obj -> (obj instanceof PostalEntityLocatorParticipation postal) ?
                Stream.of(postal) :
                Stream.empty()
        )
        .filter(filter);
  }

  public Collection<TeleEntityLocatorParticipation> phones() {
    return this.ensureLocators().stream()
        .filter(EntityLocatorParticipation.active())
        .flatMap(
            obj -> (obj instanceof TeleEntityLocatorParticipation tele) ?
                Stream.of(tele) :
                Stream.empty()
        )
        .toList();
  }

  public EntityLocatorParticipation add(
      final PatientCommand.AddPhone phone,
      final PhoneIdentifierGenerator generator
  ) {
    List<EntityLocatorParticipation> locators = ensureLocators();

    EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, generator.generate());

    EntityLocatorParticipation participation = new TeleEntityLocatorParticipation(
        this,
        identifier,
        phone);

    locators.add(participation);

    return participation;
  }

  public void update(final PatientCommand.UpdatePhone phone) {
    this.ensureLocators().stream()
        .filter(EntityLocatorParticipation.active().and(TeleEntityLocatorParticipation.class::isInstance))
        .map(TeleEntityLocatorParticipation.class::cast)
        .filter(existing -> Objects.equals(existing.identifier().getLocatorUid(), phone.identifier()))
        .findFirst()
        .ifPresent(existing -> existing.update(phone));
  }

  public void delete(final PatientCommand.DeletePhone deleted) {
    this.ensureLocators().stream()
        .filter(EntityLocatorParticipation.active().and(TeleEntityLocatorParticipation.class::isInstance))
        .map(TeleEntityLocatorParticipation.class::cast)
        .filter(existing -> Objects.equals(existing.identifier().getLocatorUid(), deleted.id()))
        .findFirst()
        .ifPresent(existing -> existing.delete(deleted));
  }

}
