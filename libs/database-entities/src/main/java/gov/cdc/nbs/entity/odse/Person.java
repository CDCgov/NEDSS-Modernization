package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.RecordStatus;
import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.entity.enums.converter.SuffixConverter;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.GenderConverter;
import gov.cdc.nbs.patient.PatientAssociationCountFinder;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientHasAssociatedEventsException;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.demographic.GeneralInformation;
import gov.cdc.nbs.patient.demographic.PatientEthnicity;
import gov.cdc.nbs.patient.demographic.PatientRaceDemographic;
import gov.cdc.nbs.patient.demographic.name.PatientLegalNameResolver;
import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@Entity
@SuppressWarnings({"javaarchitecture:S7027", "javaarchitecture:S7027", "javaarchitecture:S7091"}) //  Bidirectional mappings require knowledge of each other
public class Person {
  @Id
  @Column(name = "person_uid", nullable = false)
  private Long id;

  @Column(name = "version_ctrl_nbr", nullable = false)
  private Short versionCtrlNbr;

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
  @Column(name = "as_of_date_admin")
  private LocalDate asOfDateAdmin;

  @Column(name = "description", length = 2000)
  private String description;

  // general information
  @Embedded
  private GeneralInformation generalInformation;

  // Mortality
  @Column(name = "as_of_date_morbidity")
  private LocalDate asOfDateMorbidity;

  @Enumerated(EnumType.STRING)
  @Column(name = "deceased_ind_cd", length = 20)
  @ColumnTransformer(read = "UPPER(deceased_ind_cd)")
  private Deceased deceasedIndCd;

  @Column(name = "deceased_time")
  private LocalDate deceasedTime;

  // Ethnicity
  @Embedded
  private PatientEthnicity ethnicity;

  // Sex & birth
  @Column(name = "as_of_date_sex")
  private LocalDate asOfDateSex;

  @Column(name = "birth_time")
  private LocalDateTime birthTime;

  @Column(name = "birth_time_calc")
  private LocalDateTime birthTimeCalc;

  @Convert(converter = GenderConverter.class)
  @Column(name = "curr_sex_cd")
  private Gender currSexCd;

  @Column(name = "sex_unk_reason_cd", length = 20)
  private String sexUnkReasonCd;

  @Column(name = "preferred_gender_cd", length = 20)
  private String preferredGenderCd;

  @Column(name = "additional_gender_cd", length = 50)
  private String additionalGenderCd;

  @Convert(converter = GenderConverter.class)
  @Column(name = "birth_gender_cd", columnDefinition = "CHAR")
  private Gender birthGenderCd;

  @Column(name = "multiple_birth_ind", length = 20)
  private String multipleBirthInd;

  @Column(name = "birth_order_nbr")
  private Short birthOrderNbr;

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
    this.versionCtrlNbr = 1;
    this.audit = new Audit();
    this.recordStatus = new RecordStatus();
    this.status = new Status();
    this.race = new PatientRaceDemographic(this);
    this.generalInformation = new GeneralInformation();
    this.ethnicity = new PatientEthnicity();
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

    this.asOfDateAdmin = patient.asOf();
    this.description = patient.comments();

    this.generalInformation = new GeneralInformation(patient);

    resolveDateOfBirth(patient.dateOfBirth());

    this.birthGenderCd = patient.birthGender();
    this.currSexCd = patient.currentGender();

    this.deceasedIndCd = patient.deceased();
    this.deceasedTime = patient.deceasedTime();

    this.asOfDateSex = patient.asOf();
    this.asOfDateMorbidity = patient.asOf();

    this.ethnicity = new PatientEthnicity(patient);

    this.status = new Status(patient.requestedOn());
    this.recordStatus = new gov.cdc.nbs.audit.RecordStatus(patient.requestedOn());
    this.audit = new Audit(patient.requester(), patient.requestedOn());

  }

  private void resolveDateOfBirth(final LocalDate dateOfBirth) {
    if (dateOfBirth != null) {
      this.birthTime = dateOfBirth.atStartOfDay();
    } else {
      this.birthTime = null;
    }
    this.birthTimeCalc = this.birthTime;
  }

  public PersonName add(final SoundexResolver resolver, final PatientCommand.AddName added) {

    Collection<PersonName> existing = ensureNames();

    if (existing.isEmpty()) {

      this.firstNm = added.first();
      this.middleNm = added.middle();
      this.lastNm = added.last();
      this.nmSuffix = Suffix.resolve(added.suffix());
    }

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

  public EntityLocatorParticipation add(final PatientCommand.AddAddress address) {
    changed(address);
    return this.nbsEntity.add(address);
  }

  public void update(final PatientCommand.UpdateAddress address) {
    this.nbsEntity.update(address);
    changed(address);
  }

  public void delete(final PatientCommand.DeleteAddress address) {
    this.nbsEntity.delete(address);
    changed(address);
  }

  public List<PostalEntityLocatorParticipation> addresses() {
    return this.nbsEntity.addresses();
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

  public EntityLocatorParticipation add(final PatientCommand.AddPhoneNumber phoneNumber) {
    changed(phoneNumber);
    return this.nbsEntity.add(phoneNumber);
  }

  public EntityLocatorParticipation add(final PatientCommand.AddEmailAddress emailAddress) {
    return this.nbsEntity.add(emailAddress);
  }

  public EntityLocatorParticipation add(final PatientCommand.AddPhone phone) {
    changed(phone);
    return this.nbsEntity.add(phone);
  }

  public void update(final PatientCommand.UpdatePhone phone) {
    this.nbsEntity.update(phone);
    changed(phone);
  }

  public void delete(final PatientCommand.DeletePhone phone) {
    this.nbsEntity.delete(phone);
    changed(phone);
  }

  public Optional<EntityLocatorParticipation> update(final PatientCommand.UpdateEmailAddress emailAddress) {
    return this.nbsEntity.update(emailAddress);
  }

  public boolean delete(final PatientCommand.DeleteEmailAddress emailAddress) {
    return this.nbsEntity.delete(emailAddress);
  }

  public void update(final PatientCommand.UpdateGeneralInfo info) {
    this.generalInformation.update(info);
    changed(info);
  }

  public void associate(
      final PermissionScopeResolver resolver,
      final PatientCommand.AssociateStateHIVCase associate
  ) {
    this.generalInformation.associate(resolver, associate);
  }

  public Person update(final PatientCommand.UpdateAdministrativeInfo info) {
    this.asOfDateAdmin = info.asOf();
    this.description = info.comment();

    changed(info);

    return this;
  }

  public void update(
      final PatientCommand.UpdateBirth birth,
      final AddressIdentifierGenerator identifierGenerator
  ) {
    this.asOfDateSex = birth.asOf();
    resolveDateOfBirth(birth.bornOn());
    this.birthGenderCd = Gender.resolve(birth.gender());
    this.multipleBirthInd = birth.multipleBirth();
    this.birthOrderNbr = birth.birthOrder() == null ? null : birth.birthOrder().shortValue();

    this.nbsEntity.update(birth, identifierGenerator);

    changed(birth);
  }

  public void update(final PatientCommand.UpdateGender changes) {

    this.asOfDateSex = changes.asOf();
    this.currSexCd = Gender.resolve(changes.current());
    this.sexUnkReasonCd = changes.unknownReason();
    this.preferredGenderCd = changes.preferred();
    this.additionalGenderCd = changes.additional();

    changed(changes);
  }

  public void update(
      final PatientCommand.UpdateMortality info,
      final AddressIdentifierGenerator identifierGenerator
  ) {
    this.asOfDateMorbidity = info.asOf();
    this.deceasedIndCd = Deceased.resolve(info.deceased());

    if (Objects.equals(Deceased.Y, this.deceasedIndCd)) {
      this.deceasedTime = info.deceasedOn();
    } else {
      this.deceasedTime = null;
    }
    this.nbsEntity.update(info, identifierGenerator);

    changed(info);
  }

  public void delete(
      final PatientCommand.Delete delete,
      final PatientAssociationCountFinder finder) throws PatientHasAssociatedEventsException {

    long associations = finder.count(this.id);

    if (associations > 0) {
      throw new PatientHasAssociatedEventsException(this.id);
    }

    this.recordStatus.change("LOG_DEL", delete.requestedOn());

    changed(delete);
  }

  public Person update(final PatientCommand.UpdateEthnicityInfo info) {
    if (info.ethnicGroup() != null) {
      this.ethnicity.update(info);

      changed(info);
    }

    return this;
  }

  public Person add(final PatientCommand.AddDetailedEthnicity add) {
    this.ethnicity.add(this, add).ifPresent(added -> changed(add));
    return this;
  }

  public void remove(final PatientCommand.RemoveDetailedEthnicity remove) {
    this.ethnicity.remove(remove);

    changed(remove);
  }

  private void changed(final PatientCommand command) {
    this.versionCtrlNbr = (short) (this.versionCtrlNbr + 1);

    this.audit.changed(command.requester(), command.requestedOn());
  }

  public GeneralInformation getGeneralInformation() {
    if (this.generalInformation == null) {
      this.generalInformation = new GeneralInformation();
    }

    return this.generalInformation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Person person = (Person) o;
    return Objects.equals(id, person.id);
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

  @Override
  public int hashCode() {
    return this.id == null ? System.identityHashCode(this) : Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Person{" +
        "id=" + id +
        '}';
  }

}
