package gov.cdc.nbs.patient.contact;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Coalesce;
import gov.cdc.nbs.entity.odse.QCtContact;
import gov.cdc.nbs.entity.odse.QInterview;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import gov.cdc.nbs.entity.srte.QConditionCode;
import gov.cdc.nbs.event.search.investigation.association.AssociatedWith;
import gov.cdc.nbs.event.search.investigation.association.AssociatedWithTupleMapper;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.NameRenderer;

import java.time.Instant;
import java.util.Objects;

class PatientNamedByContactTupleMapper {

    record Tables(
            QCtContact tracing,
            QPerson subject,
            QPerson contact,
            AssociatedWithTupleMapper.Tables associatedWith,
            QInterview interview,
            Coalesce<Instant> namedOn

    ) {
        Tables {
            namedOn = new Coalesce<>(Instant.class)
                    .add(tracing.namedOnDate)
                    .add(interview.interviewDate)
                    .add(tracing.addTime);
        }

        Tables() {
            this(
                    new QCtContact("tracing"),
                    new QPerson("subject"),
                    new QPerson("contact"),
                    new AssociatedWithTupleMapper.Tables(
                            QPublicHealthCase.publicHealthCase,
                            new QConditionCode("condition")),
                    new QInterview("interview"),
                    null);
        }


    }


    private final Tables tables;
    private final AssociatedWithTupleMapper associatedMapper;

    PatientNamedByContactTupleMapper(final Tables tables) {
        this.tables = tables;
        this.associatedMapper = new AssociatedWithTupleMapper(tables.associatedWith());
    }

    PatientContacts.NamedByContact map(final Tuple tuple) {
        Long identifier = Objects.requireNonNull(
                tuple.get(this.tables.tracing().id),
                "A tracing identifier is required");
        Instant createdOn = tuple.get(this.tables.tracing().addTime);
        PatientContacts.Condition condition = mapCondition(tuple);

        PatientContacts.NamedContact contact = mapContact(tuple);

        Instant namedOn = tuple.get(this.tables.namedOn());

        String event = tuple.get(this.tables.tracing().localId);
        AssociatedWith associatedWith = this.associatedMapper.map(tuple);

        return new PatientContacts.NamedByContact(
                identifier,
                createdOn,
                condition,
                contact,
                namedOn,
                event,
                associatedWith);
    }

    private PatientContacts.NamedContact mapContact(final Tuple tuple) {
        String identifier = tuple.get(this.tables.subject().localId);
        String prefix = tuple.get(this.tables.subject().nmPrefix);
        String first = tuple.get(this.tables.subject().firstNm);
        String last = tuple.get(this.tables.subject().lastNm);
        Suffix suffix = tuple.get(this.tables.subject().nmSuffix);

        String name = NameRenderer.render(
                prefix,
                first,
                last,
                suffix);

        return new PatientContacts.NamedContact(
                Objects.requireNonNull(identifier, "A subject identifier is required."),
                name);
    }

    private PatientContacts.Condition mapCondition(final Tuple tuple) {
        String identifier = tuple.get(this.tables.associatedWith().condition().id);
        String description = tuple.get(this.tables.associatedWith().condition().conditionShortNm);

        return new PatientContacts.Condition(identifier, description);
    }

}
