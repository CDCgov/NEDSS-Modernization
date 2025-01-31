package gov.cdc.nbs.patient.profile.race;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPersonRace;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.entity.srte.QRaceCode;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

class PatientRaceTupleMapper {

    record Tables(
        QPerson patient,
        QPersonRace personRace,
        QCodeValueGeneral category,
        QRaceCode race
    ) {

        Tables() {
            this(
                QPerson.person,
                QPersonRace.personRace,
                new QCodeValueGeneral("category"),
                new QRaceCode("detailed_race")
            );
        }
    }


    private final Tables tables;

    PatientRaceTupleMapper(final Tables tables) {
        this.tables = tables;
    }

    PatientRace map(final Tuple tuple) {
        long patient = Objects.requireNonNull(
            tuple.get(this.tables.patient().personParentUid.id),
            "A race patient is required"
        );

        long identifier = Objects.requireNonNull(
            tuple.get(tables.patient().id),
            "A race identifier is required"
        );

        short version =
            Objects.requireNonNull(
                tuple.get(this.tables.patient().versionCtrlNbr),
                "A race version is required"
            );

        LocalDate asOf = tuple.get(this.tables.personRace().asOfDate);

        PatientRace.Race category = maybeMapCategory(tuple);

        Collection<PatientRace.Race> detailed = mapRace(category, tuple);

        return new PatientRace(
            patient,
            identifier,
            version,
            asOf,
            category,
            detailed
        );
    }

    private PatientRace.Race maybeMapCategory(final Tuple tuple) {
        String id = tuple.get(this.tables.category().id.code);
        String description = tuple.get(this.tables.category().codeShortDescTxt);

        return asRace(id, description);
    }

    private PatientRace.Race asRace(final String id, final String description) {
        return id == null
            ? null
            : new PatientRace.Race(
            id,
            description
        );
    }

    private Collection<PatientRace.Race> mapRace(final PatientRace.Race category, final Tuple tuple) {
        String id = tuple.get(this.tables.race().id);
        String description = tuple.get(this.tables.race().codeShortDescTxt);

        if (id == null  || (category != null && Objects.equals(id, category.id()))) {
            return List.of();
        }

        return List.of(asRace(id, description));
    }
}
