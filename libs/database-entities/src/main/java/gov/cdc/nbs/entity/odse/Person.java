package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.RecordStatus;
import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.entity.enums.converter.SuffixConverter;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.PatientAssociationCountFinder;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientHasAssociatedEventsException;
import gov.cdc.nbs.patient.demographic.*;
import gov.cdc.nbs.patient.demographic.name.PatientLegalNameResolver;
import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import gov.cdc.nbs.patient.demographic.phone.PhoneIdentifierGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

@Getter
@Setter
@Entity
@SuppressWarnings({"javaarchitecture:S7027", "javaarchitecture:S7027", "javaarchitecture:S7091"})
//  Bidirectional mappings require knowledge of each other
public class Person {
  public static final Predicate<EntityLocatorParticipation> BIRTH_PLACE = EntityLocatorParticipation.withUse("BIR");
  public static final Predicate<EntityLocatorParticipation> DEATH_PLACE = EntityLocatorParticipation.withUse("DTH");
  @Id
  @Column(name = "person_uid")
  private Long id;

  @Version
  @Column(name = "version_ctrl_nbr")
  private short versionCtrlNbr;

  @Column(name = "cd", length = 50)
  private String cd;

  @Column(name = "local_id", length = 50)
  private String localId;

  @Column(name = "electronic_ind")
  private Character electronicInd;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "person_parent_uid")
  private Person personParentUid;

  @Column(name = "edx_ind", length = 1)
  private String edxInd;

  @MapsId
  @OneToOne(
      fetch = FetchType.EAGER,
      cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE,
          CascadeType.REMOVE
      }, optional = false)
  @JoinColumn(name = "person_uid", nullable = false)
  private NBSEntity nbsEntity;

  // administrative
  @Embedded
  private PatientAdministrativeInformation administrative;

  // general information
  @Embedded
  private GeneralInformation generalInformation;

  // Mortality
  @Embedded
  private PatientMortality mortality;

  // Ethnicity
  @Embedded
  private PatientEthnicity ethnicity;

  // Sex & birth
  @Embedded
  private PatientSexBirth sexBirth;

  // Names
  // The name fields on Person are redundant, a patient's name is resolved from
  // PersonName
  @Column(name = "nm_prefix", length = 20)
  private String nmPrefix;

  @Column(name = "first_nm", length = 50)
  private String firstNm;

  @Column(name = "middle_nm", length = 50)
  private String middleNm;

  @Column(name = "last_nm", length = 50)
  private String lastNm;

  @Convert(converter = SuffixConverter.class)
  @Column(name = "nm_suffix", length = 20)
  private Suffix nmSuffix;

  @OneToMany(
      mappedBy = "personUid",
      fetch = FetchType.EAGER,
      cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE,
          CascadeType.REMOVE
      }, orphanRemoval = true)
  private List<PersonName> names;

  @Embedded
  private PatientRaceDemographic race;

  @Embedded
  private Audit audit;

  @Embedded
  private RecordStatus recordStatus;

  @Embedded
  private Status status;

  protected Person() {
    this.audit = new Audit();
    this.recordStatus = new RecordStatus();
    this.status = new Status();
    this.race = new PatientRaceDemographic(this);
  }

  public Person(final long identifier, final String localId) {
    this();
    this.id = identifier;
    this.localId = localId;
    this.nbsEntity = new NBSEntity(identifier, "PSN");

    this.personParentUid = this;

    this.cd = "PAT";
    this.electronicInd = 'N';
    this.edxInd = "Y";

  }

  public Person(final PatientCommand.CreatePatient patient) {
    this(patient.person(), patient.localId());

    this.status = new Status(patient.requestedOn());
    this.recordStatus = new RecordStatus(patient.requestedOn());
    this.audit = new Audit(patient.requester(), patient.requestedOn());
  }

  public Person(final PatientCommand.AddPatient patient) {
    this(patient.person(), patient.localId());

    this.nbsEntity = new NBSEntity(patient);

    this.administrative = new PatientAdministrativeInformation(patient);
    this.generalInformation = new GeneralInformation(patient);
    this.ethnicity = new PatientEthnicity(patient);
    this.sexBirth = new PatientSexBirth(patient);
    this.mortality = new PatientMortality(patient);

    this.status = new Status(patient.requestedOn());
    this.recordStatus = new gov.cdc.nbs.audit.RecordStatus(patient.requestedOn());
    this.audit = new Audit(patient.requester(), patient.requestedOn());

  }

  public PersonName add(final SoundexResolver resolver, final PatientCommand.AddName added) {

    Collection<PersonName> existing = ensureNames();

    PersonNameId identifier = PersonNameId.from(this.id, existing.size() + 1);

    PersonName personName = new PersonName(
        identifier,
        this,
        resolver,
        added
    );

    existing.add(personName);

    changed(added);
    return personName;
  }

  public void update(final SoundexResolver resolver, final PatientCommand.UpdateNameInfo updated) {
    PersonNameId identifier = PersonNameId.from(updated.person(), updated.sequence());

    ensureNames().stream()
        .filter(name -> Objects.equals(name.getId(), identifier))
        .findFirst()
        .ifPresent(name -> name.update(resolver, updated));

    changed(updated);
  }

  public void delete(final PatientCommand.DeleteNameInfo deleted) {
    PersonNameId identifier = PersonNameId.from(deleted.person(), deleted.sequence());
    ensureNames().stream()
        .filter(name -> Objects.equals(name.getId(), identifier))
        .findFirst()
        .ifPresent(name -> delete(deleted, name));

    changed(deleted);
  }

  private void delete(final PatientCommand.DeleteNameInfo deleted, final PersonName name) {
    name.delete(deleted);
    changed(deleted);
  }

  private Collection<PersonName> ensureNames() {
    if (this.names == null) {
      this.names = new ArrayList<>();
    }
    return this.names;
  }

  public List<PersonName> getNames() {
    return this.names == null ? List.of() : this.names.stream().filter(PersonName.active()).toList();
  }

  public Optional<PersonName> legalName(final LocalDate asOf) {
    return this.names == null
        ? Optional.empty()
        : PatientLegalNameResolver.resolve(this.names, asOf);
  }

  public void add(final PatientCommand.AddRace added) {
    this.race.patient(this).add(added);
    changed(added);
  }

  public void update(final PatientCommand.UpdateRaceInfo changes) {
    this.race.update(this, changes);
    changed(changes);
  }

  public void delete(final PatientCommand.DeleteRaceInfo deleted) {
    this.race.delete(deleted);
    changed(deleted);
  }

  public List<PersonRace> getRaces() {
    return this.race.races();
  }

  public EntityId add(final PatientCommand.AddIdentification information) {
    changed(information);
    return this.nbsEntity.add(information);
  }

  public void update(final PatientCommand.UpdateIdentification information) {
    this.nbsEntity.update(information);
    changed(information);
  }

  public void delete(final PatientCommand.DeleteIdentification information) {
    this.nbsEntity.delete(information);
    changed(information);
  }

  public EntityLocatorParticipation add(
      final PatientCommand.AddAddress address,
      final AddressIdentifierGenerator generator
  ) {
    changed(address);
    return this.nbsEntity.add(address, generator);
  }

  public void update(final PatientCommand.UpdateAddress address) {
    this.nbsEntity.update(address);
    changed(address);
  }

  public void delete(final PatientCommand.DeleteAddress address) {
    this.nbsEntity.delete(address);
    changed(address);
  }

  public Optional<PostalEntityLocatorParticipation> locationOfBirth() {
    return this.nbsEntity.addresses(BIRTH_PLACE).findFirst();
  }

  public Optional<PostalEntityLocatorParticipation> locationOfDeath() {
    return this.nbsEntity.addresses(DEATH_PLACE).findFirst();
  }

  public List<PostalEntityLocatorParticipation> addresses() {
    return this.nbsEntity.addresses(not(BIRTH_PLACE.or(DEATH_PLACE))).toList();
  }

  public Collection<TeleEntityLocatorParticipation> phones() {
    return this.nbsEntity.phones();
  }

  public List<TeleEntityLocatorParticipation> phoneNumbers() {
    return this.nbsEntity.phoneNumbers();
  }

  public List<TeleEntityLocatorParticipation> emailAddresses() {
    return this.nbsEntity.emailAddress();
  }

  public List<EntityId> identifications() {
    return this.nbsEntity.identifications();
  }

  public EntityLocatorParticipation add(
      final PatientCommand.AddPhone phone,
      final PhoneIdentifierGenerator generator
  ) {
    changed(phone);
    return this.nbsEntity.add(phone, generator);
  }

  public void update(final PatientCommand.UpdatePhone phone) {
    this.nbsEntity.update(phone);
    changed(phone);
  }

  public void delete(final PatientCommand.DeletePhone phone) {
    this.nbsEntity.delete(phone);
    changed(phone);
  }

  private GeneralInformation ensureGeneralInformation() {
    if (this.generalInformation == null) {
      this.generalInformation = new GeneralInformation();
    }
    return this.generalInformation;
  }

  public void update(final PatientCommand.UpdateGeneralInfo info) {
    ensureGeneralInformation().update(info);
    changed(info);
  }

  public void associate(
      final PermissionScopeResolver resolver,
      final PatientCommand.AssociateStateHIVCase associate
  ) {
    ensureGeneralInformation().associate(resolver, associate);
  }

  public Person update(final PatientCommand.UpdateAdministrativeInfo info) {
    if (this.administrative == null) {
      this.administrative = new PatientAdministrativeInformation();
    }
    this.administrative.update(info);

    changed(info);

    return this;
  }

  private PatientSexBirth ensurePatientSexBirth() {
    if (sexBirth == null) {
      this.sexBirth = new PatientSexBirth();
    }
    return this.sexBirth;
  }

  public void update(
      final PatientCommand.UpdateBirth birth,
      final AddressIdentifierGenerator identifierGenerator
  ) {

    ensurePatientSexBirth().update(birth);
    this.nbsEntity.update(birth, identifierGenerator);

    changed(birth);
  }

  public void update(final PatientCommand.UpdateGender changes) {
    ensurePatientSexBirth().update(changes);

    changed(changes);
  }

  public void update(
      final PatientCommand.UpdateMortality info,
      final AddressIdentifierGenerator identifierGenerator
  ) {
    if (this.mortality == null) {
      this.mortality = new PatientMortality();
    }
    this.mortality.update(info);
    this.nbsEntity.update(info, identifierGenerator);

    changed(info);
  }

  public void delete(
      final PatientCommand.Delete delete,
      final PatientAssociationCountFinder finder
  ) throws PatientHasAssociatedEventsException {

    long associations = finder.count(this.id);

    if (associations > 0) {
      throw new PatientHasAssociatedEventsException(this.id);
    }

    this.recordStatus.change("LOG_DEL", delete.requestedOn());

    changed(delete);
  }

  private PatientEthnicity ensureEthnicity() {
    if (this.ethnicity == null) {
      this.ethnicity = new PatientEthnicity();
    }
    return this.ethnicity;
  }

  public Person update(final PatientCommand.UpdateEthnicityInfo info) {
    ensureEthnicity().update(info);

    changed(info);

    return this;
  }

  public Person add(final PatientCommand.AddDetailedEthnicity add) {
    ensureEthnicity().add(this, add).ifPresent(added -> changed(add));
    return this;
  }

  public void remove(final PatientCommand.RemoveDetailedEthnicity remove) {
    ensureEthnicity().remove(remove);

    changed(remove);
  }

  private void changed(final PatientCommand command) {
    this.audit.changed(command.requester(), command.requestedOn());
  }

  public Audit audit() {
    return audit;
  }

  public RecordStatus recordStatus() {
    return recordStatus;
  }

  public Status status() {
    return status;
  }

  public PatientAdministrativeInformation administrative() {
    return administrative;
  }

  public PatientSexBirth sexBirth() {
    return sexBirth;
  }

  public PatientEthnicity ethnicity() {
    return ethnicity;
  }

  public PatientRaceDemographic race() {
    return race;
  }

  public GeneralInformation generalInformation() {
    return generalInformation;
  }

  public PatientMortality mortality() {
    return mortality;
  }

  public long signature() {
    return Objects.hash(
        administrative == null ? 0 : administrative.signature(),
        generalInformation == null ? 0 : generalInformation.signature(),
        mortality == null ? 0 : mortality.signature(),
        ethnicity == null ? 0 : ethnicity.signature(),
        sexBirth == null ? 0 : sexBirth.signature()
    );
  }
}
