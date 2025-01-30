package gov.cdc.nbs.patient.profile.birth;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPostalLocator;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.entity.srte.QCountryCode;
import gov.cdc.nbs.entity.srte.QStateCode;
import gov.cdc.nbs.entity.srte.QStateCountyCodeValue;
import gov.cdc.nbs.geo.country.SimpleCountry;
import gov.cdc.nbs.geo.country.SimpleCountryTupleMapper;
import gov.cdc.nbs.geo.county.SimpleCounty;
import gov.cdc.nbs.geo.county.SimpleCountyTupleMapper;
import gov.cdc.nbs.geo.state.SimpleState;
import gov.cdc.nbs.geo.state.SimpleStateTupleMapper;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

class PatientBirthTupleMapper {

    record Tables(
        QPerson patient,
        QCodeValueGeneral multipleBirth,
        QPostalLocator address,
        QStateCode state,
        QStateCountyCodeValue county,
        QCountryCode country
    ) {

        Tables() {
            this(
                QPerson.person,
                new QCodeValueGeneral("multiple_birth"),
                QPostalLocator.postalLocator,
                QStateCode.stateCode,
                QStateCountyCodeValue.stateCountyCodeValue,
                QCountryCode.countryCode
            );
        }

    }


    private final PatientBirthTupleMapper.Tables tables;
    private final Clock clock;

    private final SimpleCountryTupleMapper countryMapper;
    private final SimpleStateTupleMapper stateMapper;
    private final SimpleCountyTupleMapper countyMapper;

    PatientBirthTupleMapper(
        final Clock clock,
        final PatientBirthTupleMapper.Tables tables
    ) {
        this.clock = clock;
        this.tables = tables;

        this.countryMapper = new SimpleCountryTupleMapper(
            new SimpleCountryTupleMapper.Tables(
                this.tables.address(),
                this.tables.country()
            )
        );

        this.stateMapper = new SimpleStateTupleMapper(
            new SimpleStateTupleMapper.Tables(
                this.tables.address(),
                this.tables.state()
            )
        );

        this.countyMapper = new SimpleCountyTupleMapper(
            new SimpleCountyTupleMapper.Tables(
                this.tables.address(),
                this.tables.county()
            )
        );
    }

    PatientBirthTupleMapper(final PatientBirthTupleMapper.Tables tables) {
        this(Clock.systemDefaultZone(), tables);
    }

    PatientBirth map(final Tuple tuple) {
        long patient = Objects.requireNonNull(
            tuple.get(this.tables.patient().personParentUid.id),
            "A birth patient is required"
        );

        long identifier = Objects.requireNonNull(
            tuple.get(this.tables.patient().id),
            "A birth identifier is required"
        );

        short version =
            Objects.requireNonNull(
                tuple.get(this.tables.patient().versionCtrlNbr),
                "A birth version is required"
            );

        LocalDate asOf = tuple.get(this.tables.patient().asOfDateSex);

        LocalDate birthday = resolveBirthday(tuple);

        Integer age = resolveAge(birthday);

        PatientBirth.MultipleBirth multipleBirth = resolveMultipleBirth(tuple);

        Short birthOrder = tuple.get(this.tables.patient.birthOrderNbr);

        String city = tuple.get(this.tables.address().cityDescTxt);

        SimpleState state = this.stateMapper.maybeMap(tuple).orElse(null);

        SimpleCounty county = this.countyMapper.maybeMap(tuple).orElse(null);

        SimpleCountry country = this.countryMapper.maybeMap(tuple).orElse(null);

        return new PatientBirth(
            patient,
            identifier,
            version,
            asOf,
            birthday,
            age,
            multipleBirth,
            birthOrder,
            city,
            state,
            county,
            country
        );
    }

    private LocalDate resolveBirthday(final Tuple tuple) {

        LocalDateTime value = tuple.get(tables.patient().birthTime);

        return value == null
            ? null
            : value.toLocalDate();

    }

    private Integer resolveAge(final LocalDate birthday) {
        LocalDate now = LocalDate.now(this.clock);

        return birthday == null
            ? null
            : (int) ChronoUnit.YEARS.between(birthday, now);
    }

    private PatientBirth.MultipleBirth resolveMultipleBirth(final Tuple tuple) {
        String id = tuple.get(this.tables.patient().multipleBirthInd);
        String description = tuple.get(this.tables.multipleBirth().codeShortDescTxt);

        return id == null
            ? null
            : new PatientBirth.MultipleBirth(
            id,
            description
        );
    }
}
