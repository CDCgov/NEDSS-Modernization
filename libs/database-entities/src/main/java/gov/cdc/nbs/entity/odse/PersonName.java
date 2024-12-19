package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.enums.converter.SuffixConverter;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientNameHistoryListener;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.function.Predicate;

@Getter
@Entity
@Table(name = "Person_name")
@SuppressWarnings(
    //  The PatientNameHistoryListener is an entity listener specifically for instances of this class
    {"javaarchitecture:S7027","javaarchitecture:S7091"}
)
@EntityListeners(PatientNameHistoryListener.class)
public class PersonName {

  public static Predicate<PersonName> active() {
    return input -> Objects.equals(input.recordStatusCd, RecordStatus.ACTIVE.name());
  }

  public static Predicate<PersonName> havingType(final String use) {
    return input -> Objects.equals(input.type(),use);
  }

  @EmbeddedId
  private PersonNameId id;

  @MapsId("personUid")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "person_uid", nullable = false)
  private Person personUid;

  @Column(name = "first_nm", length = 50)
  private String firstNm;

  @Column(name = "last_nm", length = 50)
  private String lastNm;

  @Column(name = "last_nm2", length = 50)
  private String lastNm2;

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

  @Column(name = "record_status_cd", length = 20)
  private String recordStatusCd;

  @Column(name = "record_status_time")
  private Instant recordStatusTime;

  @Column(name = "status_cd", nullable = false)
  private Character statusCd;

  @Column(name = "status_time", nullable = false)
  private Instant statusTime;

  @Column(name = "as_of_date")
  private Instant asOfDate;

  @Embedded
  private Audit audit;

  protected PersonName() {

  }

  public PersonName(
      final PersonNameId identifier,
      final Person person,
      final PatientCommand.AddName added) {
    this.asOfDate = added.asOf();

    this.statusCd = 'A';
    this.statusTime = added.requestedOn();

    this.recordStatusCd = "ACTIVE";
    this.recordStatusTime = added.requestedOn();

    this.id = identifier;
    this.personUid = person;

    this.nmPrefix = added.prefix();
    this.firstNm = added.first();
    this.middleNm = added.middle();
    this.middleNm2 = added.secondMiddle();
    this.lastNm = added.last();
    this.lastNm2 = added.secondLast();
    this.nmSuffix = Suffix.resolve(added.suffix());
    this.nmDegree = added.degree();
    this.nmUseCd = added.type();

    this.audit = new Audit(added.requester(), added.requestedOn());
  }

  public LocalDate asOf() {
    return this.asOfDate.atZone(ZoneOffset.UTC).toLocalDate();
  }

  public String type() {
    return this.nmUseCd;
  }

  public void update(final PatientCommand.UpdateNameInfo info) {
    this.asOfDate = info.asOf();
    this.nmPrefix = info.prefix();
    this.firstNm = info.first();
    this.middleNm = info.middle();
    this.middleNm2 = info.secondMiddle();
    this.lastNm = info.last();
    this.lastNm2 = info.secondLast();
    this.nmSuffix = Suffix.resolve(info.suffix());
    this.nmDegree = info.degree();
    this.nmUseCd = info.type();

    this.audit.changed(info.requester(), info.requestedOn());
  }

  public void delete(final PatientCommand.DeleteNameInfo deleted) {
    this.recordStatusCd = RecordStatus.INACTIVE.name();
    this.recordStatusTime = deleted.requestedOn();
    this.audit.changed(deleted.requester(), deleted.requestedOn());
  }

  @Override
  public String toString() {
    return "PersonName{" +
        "id=" + id +
        ", recordStatusCd='" + recordStatusCd + '\'' +
        '}';
  }
}
