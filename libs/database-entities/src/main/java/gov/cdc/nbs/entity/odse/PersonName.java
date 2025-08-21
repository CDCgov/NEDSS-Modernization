package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.RecordStatus;
import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.entity.enums.converter.SuffixConverter;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientNameHistoryListener;
import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Predicate;

@Getter
@Entity
@Table(name = "Person_name")
@SuppressWarnings(
    //  The PatientNameHistoryListener is an entity listener specifically for instances of this class
    {"javaarchitecture:S7027", "javaarchitecture:S7091"}
)
@EntityListeners(PatientNameHistoryListener.class)
public class PersonName {

  public static Predicate<PersonName> active() {
    return input -> input.recordStatus.status().equals("ACTIVE");
  }

  public static Predicate<PersonName> havingType(final String use) {
    return input -> Objects.equals(input.type(), use);
  }

  @EmbeddedId
  private PersonNameId id;

  @MapsId("personUid")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "person_uid", nullable = false)
  private Person personUid;

  @Column(name = "first_nm", length = 50)
  private String firstNm;

  @Column(name = "first_nm_sndx", length = 50)
  private String firstNameSoundex;

  @Column(name = "last_nm", length = 50)
  private String lastNm;

  @Column(name = "last_nm_sndx", length = 50)
  private String lastNameSoundex;

  @Column(name = "last_nm2", length = 50)
  private String lastNm2;

  @Column(name = "last_nm2_sndx", length = 50)
  private String secondLastNameSoundex;

  @Column(name = "middle_nm", length = 50)
  private String middleNm;

  @Column(name = "middle_nm2", length = 50)
  private String middleNm2;

  @Column(name = "nm_degree", length = 20)
  private String nmDegree;

  @Column(name = "nm_prefix", length = 20)
  private String nmPrefix;

  @Convert(converter = SuffixConverter.class)
  @Column(name = "nm_suffix", length = 20)
  private Suffix nmSuffix;

  @Column(name = "nm_use_cd", length = 20)
  private String nmUseCd;

  @Column(name = "as_of_date")
  private LocalDate asOfDate;

  @Embedded
  private Audit audit;

  @Embedded
  private RecordStatus recordStatus;

  @Embedded
  private Status status;

  protected PersonName() {

  }

  public PersonName(
      final PersonNameId identifier,
      final Person person,
      final SoundexResolver resolver,
      final PatientCommand.AddName added
  ) {
    this.asOfDate = added.asOf();

    this.id = identifier;
    this.personUid = person;

    this.nmPrefix = added.prefix();
    applyFirstName(added.first(), resolver);
    this.middleNm = added.middle();
    this.middleNm2 = added.secondMiddle();
    applyLastName(added.last(), resolver);
    applySecondLastName(added.secondLast(), resolver);
    this.nmSuffix = Suffix.resolve(added.suffix());
    this.nmDegree = added.degree();
    this.nmUseCd = added.type();

    this.recordStatus = new RecordStatus(added.requestedOn());
    this.status = new Status(added.requestedOn());
    this.audit = new Audit(added.requester(), added.requestedOn());
  }

  private void applyFirstName(final String value, final SoundexResolver resolver) {
    this.firstNm = value;
    this.firstNameSoundex = resolver.resolve(value);
  }

  private void applyLastName(final String value, final SoundexResolver resolver) {
    this.lastNm = value;
    this.lastNameSoundex = resolver.resolve(value);
  }

  private void applySecondLastName(final String value, final SoundexResolver resolver) {
    this.lastNm2 = value;
    this.secondLastNameSoundex = resolver.resolve(value);
  }

  public LocalDate asOf() {
    return this.asOfDate;
  }

  public String type() {
    return this.nmUseCd;
  }

  public PersonName update(final SoundexResolver resolver, final PatientCommand.UpdateNameInfo info) {
    this.asOfDate = info.asOf();
    this.nmPrefix = info.prefix();
    applyFirstName(info.first(), resolver);
    this.middleNm = info.middle();
    this.middleNm2 = info.secondMiddle();
    applyLastName(info.last(), resolver);
    applySecondLastName(info.secondLast(), resolver);
    this.nmSuffix = Suffix.resolve(info.suffix());
    this.nmDegree = info.degree();
    this.nmUseCd = info.type();

    changed(info);

    return this;
  }

  public void delete(final PatientCommand.DeleteNameInfo deleted) {
    this.recordStatus.inactivate(deleted.requestedOn());
    changed(deleted);
  }

  protected void changed(final PatientCommand command) {
    if (this.audit == null) {
      this.audit = new Audit(command.requester(), command.requestedOn());
    }

    this.audit.changed(command.requester(), command.requestedOn());
  }

  public RecordStatus recordStatus() {
    return recordStatus;
  }

  @Override
  public String toString() {
    return "PersonName{" + "id=" + id + '}';
  }
}
