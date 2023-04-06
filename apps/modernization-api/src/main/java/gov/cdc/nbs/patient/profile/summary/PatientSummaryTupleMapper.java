package gov.cdc.nbs.patient.profile.summary;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Suffix;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

class PatientSummaryTupleMapper {

    private final Clock clock;
    private final PatientSummaryTables tables;

    PatientSummaryTupleMapper(final PatientSummaryTables tables) {
        this(Clock.systemDefaultZone(), tables);
    }

    public PatientSummaryTupleMapper(final Clock clock, final PatientSummaryTables tables) {
        this.clock = clock;
        this.tables = tables;
    }

    PatientSummary map(final Tuple tuple) {

        PatientSummary.Name name = mapName(tuple);

        String gender = resolveGender(tuple);

        LocalDate birthday = resolveBirthday(tuple);

        Integer age = resolveAge(birthday);

        String race = tuple.get(tables.race().codeShortDescTxt);

        String ethnicity = tuple.get(tables.ethnicity().codeShortDescTxt);

        return new PatientSummary(
            name,
            birthday,
            age,
            gender,
            ethnicity,
            race
        );
    }

    private PatientSummary.Name mapName(final Tuple tuple) {
        String prefix = tuple.get(tables.prefix().codeShortDescTxt);
        String first = tuple.get(tables.name().firstNm);
        String middle = tuple.get(tables.name().middleNm);
        String last = tuple.get(tables.name().lastNm);
        String suffix = resolveSuffix(tuple);

        return new PatientSummary.Name(
            prefix,
            first,
            middle,
            last,
            suffix
        );
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
}
