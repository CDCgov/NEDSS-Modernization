package gov.cdc.nbs.patient.profile.names;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.message.enums.Suffix;

import java.time.LocalDate;
import java.util.Objects;

class PatientNameTupleMapper {

  record Tables(
      QPerson patient,
      QPersonName name,
      QCodeValueGeneral use,
      QCodeValueGeneral prefix,
      QCodeValueGeneral degree
  ) {

    Tables() {
      this(
          QPerson.person,
          QPersonName.personName,
          new QCodeValueGeneral("use"),
          new QCodeValueGeneral("prefix"),
          new QCodeValueGeneral("degree")
      );
    }


  }


  private final Tables tables;

  PatientNameTupleMapper(final Tables tables) {
    this.tables = tables;
  }

  PatientName map(final Tuple tuple) {

    long identifier = Objects.requireNonNull(
        tuple.get(tables.name().id.personUid),
        "A name identifier is required"
    );

    short version =
        Objects.requireNonNull(
            tuple.get(this.tables.patient().versionCtrlNbr),
            "A name version is required"
        );

    short sequence = Objects.requireNonNull(
        tuple.get(tables.name().id.personNameSeq),
        "A name sequence is required"
    );

    LocalDate asOf = Objects.requireNonNull(
        tuple.get(tables.name().asOfDate),
        "A name as of is required"
    );

    PatientName.Use use = mapUse(tuple);

    PatientName.Prefix prefix = maybeMapPrefix(tuple);

    String first = tuple.get(tables.name().firstNm);

    String middle = tuple.get(tables.name().middleNm);
    String secondMiddle = tuple.get(tables.name().middleNm2);

    String last = tuple.get(tables.name().lastNm);
    String secondLast = tuple.get(tables.name().lastNm2);

    PatientName.Suffix suffix = maybeMapSuffix(tuple);

    PatientName.Degree degree = maybeMapDegree(tuple);

    return new PatientName(
        identifier,
        version,
        sequence,
        asOf,
        use,
        prefix,
        first,
        middle,
        secondMiddle,
        last,
        secondLast,
        suffix,
        degree
    );
  }

  private PatientName.Use mapUse(final Tuple tuple) {
    String id = Objects.requireNonNull(
        tuple.get(tables.name().nmUseCd),
        "Name use is required"
    );

    String description = tuple.get(tables.use().codeShortDescTxt);

    return new PatientName.Use(
        id,
        description
    );
  }

  private PatientName.Prefix maybeMapPrefix(final Tuple tuple) {
    String id = tuple.get(tables.name().nmPrefix);
    String description = tuple.get(tables.prefix().codeShortDescTxt);

    return id == null
        ? null
        : new PatientName.Prefix(
        id,
        description
    );
  }

  private PatientName.Suffix maybeMapSuffix(final Tuple tuple) {
    Suffix suffix = tuple.get(tables.name().nmSuffix);

    return suffix == null
        ? null
        : new PatientName.Suffix(
        suffix.name(),
        suffix.display()
    );
  }

  private PatientName.Degree maybeMapDegree(final Tuple tuple) {
    String id = tuple.get(tables.name().nmDegree);
    String description = tuple.get(tables.degree().codeDescTxt);

    return id == null
        ? null
        : new PatientName.Degree(
        id,
        description
    );
  }
}
