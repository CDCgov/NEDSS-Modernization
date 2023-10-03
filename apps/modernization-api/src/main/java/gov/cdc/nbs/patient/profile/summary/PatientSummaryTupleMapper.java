package gov.cdc.nbs.patient.profile.summary;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.entity.odse.QPostalLocator;
import gov.cdc.nbs.entity.odse.QTeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.QTeleLocator;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.entity.srte.QCountryCode;
import gov.cdc.nbs.entity.srte.QStateCode;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Suffix;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

class PatientSummaryTupleMapper {

    record Tables(
        QPerson patient,

        QPersonName name,

        QCodeValueGeneral prefix,

        QCodeValueGeneral ethnicity,
        QTeleEntityLocatorParticipation phone,
        QTeleLocator phoneNumber,
        QCodeValueGeneral phoneUse,
        QTeleEntityLocatorParticipation net,
        QTeleLocator email,
        QCodeValueGeneral emailUse,
        QEntityLocatorParticipation home,
        QPostalLocator address,
        QStateCode state,
        QCountryCode country
    ) {

        Tables() {
            this(
                QPerson.person,
                QPersonName.personName,
                new QCodeValueGeneral("prefix"),
                new QCodeValueGeneral("ethnicity"),
                new QTeleEntityLocatorParticipation("phone"),
                new QTeleLocator("phone_number"),
                new QCodeValueGeneral("phone_use"),
                new QTeleEntityLocatorParticipation("net"),
                new QTeleLocator("email"),
                new QCodeValueGeneral("email_use"),
                new QEntityLocatorParticipation("home"),
                QPostalLocator.postalLocator,
                QStateCode.stateCode,
                QCountryCode.countryCode
            );
        }
    }


    private final Clock clock;
    private final Tables tables;

    PatientSummaryTupleMapper(final Tables tables) {
        this(Clock.systemDefaultZone(), tables);
    }

    public PatientSummaryTupleMapper(final Clock clock, final Tables tables) {
        this.clock = clock;
        this.tables = tables;
    }

    PatientSummary map(final Tuple tuple) {

        long patient = Objects.requireNonNull(
            tuple.get(this.tables.patient().personParentUid.id),
            "A summary patient is required"
        );

        PatientSummary.Name name = maybeMapName(tuple);

        String gender = resolveGender(tuple);

        LocalDate birthday = resolveBirthday(tuple);

        Integer age = resolveAge(birthday);

        String ethnicity = tuple.get(tables.ethnicity().codeShortDescTxt);

        Collection<PatientSummary.Phone> phone = maybeMapPhone(tuple);
        Collection<PatientSummary.Email> email = maybeMapEmail(tuple);
        PatientSummary.Address home = maybeMapAddress(tuple);

        return new PatientSummary(
            patient,
            name,
            birthday,
            age,
            gender,
            ethnicity,
            phone,
            email,
            home
        );
    }

    private PatientSummary.Name maybeMapName(final Tuple tuple) {
        String prefix = tuple.get(tables.prefix().codeShortDescTxt);
        String first = tuple.get(tables.name().firstNm);
        String middle = tuple.get(tables.name().middleNm);
        String last = tuple.get(tables.name().lastNm);
        String suffix = resolveSuffix(tuple);

        boolean present = (prefix != null || first != null || middle != null || last != null || suffix != null);

        return present
            ? new PatientSummary.Name(prefix, first, middle, last, suffix)
            : null;
    }

    private String resolveGender(final Tuple tuple) {
        Gender gender = tuple.get(tables.patient().currSexCd);
        return gender == null ? null : gender.display();
    }

    private String resolveSuffix(final Tuple tuple) {
        Suffix suffix = tuple.get(tables.name().nmSuffix);
        return suffix == null ? null : suffix.display();
    }

    private LocalDate resolveBirthday(final Tuple tuple) {

        Instant value = tuple.get(tables.patient().birthTime);

        return value == null
            ? null
            : LocalDate.ofInstant(value, ZoneId.of("UTC"));

    }

    private Integer resolveAge(final LocalDate birthday) {
        LocalDate now = LocalDate.now(this.clock);

        return birthday == null
            ? null
            : (int) ChronoUnit.YEARS.between(birthday, now);
    }

    private Collection<PatientSummary.Phone> maybeMapPhone(final Tuple tuple) {
        String use = tuple.get(this.tables.phoneUse().codeShortDescTxt);
        String number = tuple.get(this.tables.phoneNumber().phoneNbrTxt);

        return number == null
            ? List.of()
            : List.of(
            new PatientSummary.Phone(
                use,
                number
            )
        );
    }

    private Collection<PatientSummary.Email> maybeMapEmail(final Tuple tuple) {
        String use = tuple.get(this.tables.emailUse().codeShortDescTxt);
        String number = tuple.get(this.tables.email().emailAddress);

        return number == null
            ? List.of()
            : List.of(
            new PatientSummary.Email(
                use,
                number
            )
        );
    }

    private PatientSummary.Address maybeMapAddress(final Tuple tuple) {
        String street = tuple.get(this.tables.address().streetAddr1);
        String city = tuple.get(this.tables.address().cityDescTxt);
        String state = tuple.get(this.tables.state().stateNm);
        String zip = tuple.get(this.tables.address().zipCd);
        String country = tuple.get(this.tables.country().codeDescTxt);

        boolean present = (street != null || city != null || state != null || zip != null || country != null);

        return present
            ? new PatientSummary.Address(street, city, state, zip, country)
            : null;
    }
}
