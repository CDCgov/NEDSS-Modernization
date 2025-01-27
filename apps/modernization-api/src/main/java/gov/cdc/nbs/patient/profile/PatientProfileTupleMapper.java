package gov.cdc.nbs.patient.profile;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPerson;

import java.util.Objects;

class PatientProfileTupleMapper {

  record Tables(QPerson patient) {
    Tables() {
      this(QPerson.person);
    }
  }


  private final Tables tables;

  PatientProfileTupleMapper(final Tables tables) {
    this.tables = tables;
  }

  PatientProfile map(final Tuple tuple) {
    long identifier = Objects.requireNonNull(
        tuple.get(this.tables.patient().personParentUid.id),
        "A patient id is required"
    );

    String local = tuple.get(this.tables.patient().personParentUid.localId);
    short version =
        Objects.requireNonNull(
            tuple.get(this.tables.patient().personParentUid.versionCtrlNbr),
            "A patient version is required"
        );
    String recordStatus = mapRecordStatus(tuple);

    return new PatientProfile(
        identifier,
        local,
        version,
        recordStatus
    );
  }

  private String mapRecordStatus(final Tuple tuple) {
    String value =
        Objects.requireNonNull(
            tuple.get(this.tables.patient().personParentUid.recordStatus.status),
            "A patient record status is required"
        );

    return switch (value) {
      case "LOG_DEL" -> PatientProfile.STATUS_INACTIVE;
      case "SUPERCEDED" -> PatientProfile.STATUS_SUPERSEDED;
      default -> value;
    };
  }
}
