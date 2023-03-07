package gov.cdc.nbs.patient.contact;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Coalesce;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.QCtContact;
import gov.cdc.nbs.entity.odse.QInterview;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.NameRenderer;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Component
class PatientNamedByContactFinder {

    private static final QCtContact TRACING = new QCtContact("tracing");
    private static final QPerson SUBJECT = new QPerson("subject");
    private static final QPerson CONTACT = new QPerson("contact");
    private static final QPublicHealthCase INVESTIGATION = new QPublicHealthCase("investigation");

    private static final QInterview INTERVIEW = new QInterview("interview");

    private static final Coalesce<Instant> NAMED_ON = new Coalesce<>(Instant.class)
            .add(TRACING.namedOnDate)
            .add(INTERVIEW.interviewDate)
            .add(TRACING.addTime);

    private final JPAQueryFactory factory;

    PatientNamedByContactFinder(final JPAQueryFactory factory) {
        this.factory = factory;
    }

    List<PatientContacts.NamedByContact> find(final long patient) {
        return this.factory.selectDistinct(
                        TRACING.id,
                        TRACING.addTime,
                        TRACING.localId,
                        SUBJECT.id,
                        SUBJECT.nmPrefix,
                        SUBJECT.firstNm,
                        SUBJECT.lastNm,
                        SUBJECT.nmSuffix,
                        INVESTIGATION.id,
                        INVESTIGATION.localId,
                        INVESTIGATION.cdDescTxt,
                        NAMED_ON
                ).from(TRACING)
                .join(INVESTIGATION).on(
                        INVESTIGATION.id.eq(TRACING.subjectEntityPhcUid.id)
                )
                .join(CONTACT).on(
                        CONTACT.id.eq(TRACING.contactNBSEntityUid.id)
                )
                .join(SUBJECT).on(
                        TRACING.subjectNBSEntityUid.id.eq(SUBJECT.id)
                )
                .leftJoin(INTERVIEW).on(
                        INTERVIEW.id.eq(TRACING.namedDuringInterviewUid)
                )
                .where(TRACING.recordStatusCd.eq("ACTIVE"),
                        CONTACT.personParentUid.id.eq(patient)
                )
                .fetch()
                .stream()
                .map(this::map)
                .toList();
    }

    private PatientContacts.NamedByContact map(final Tuple tuple) {
        Long identifier = Objects.requireNonNull(tuple.get(TRACING.id));
        Instant createdOn = tuple.get(TRACING.addTime);
        String condition = tuple.get(INVESTIGATION.cdDescTxt);

        PatientContacts.NamedContact contact = mapContact(tuple);

        Instant namedOn = tuple.get(NAMED_ON);

        String event = tuple.get(TRACING.localId);

        PatientContacts.Investigation investigation = mapInvestigation(tuple);

        return new PatientContacts.NamedByContact(
                identifier,
                createdOn,
                condition,
                contact,
                namedOn,
                event,
                investigation
        );
    }

    private PatientContacts.NamedContact mapContact(final Tuple tuple) {
        Long identifier = tuple.get(SUBJECT.id);
        String prefix = tuple.get(SUBJECT.nmPrefix);
        String first = tuple.get(SUBJECT.firstNm);
        String last = tuple.get(SUBJECT.lastNm);
        Suffix suffix = tuple.get(SUBJECT.nmSuffix);

        String name = NameRenderer.render(
                prefix,
                first,
                last,
                suffix
        );

        return new PatientContacts.NamedContact(
                Objects.requireNonNull(identifier, "A subject identifier is required."),
                name
        );
    }

    private PatientContacts.Investigation mapInvestigation(final Tuple tuple) {
        Long identifier =
                Objects.requireNonNull(tuple.get(INVESTIGATION.id), "An investigation identifier is required.");
        String local = tuple.get(INVESTIGATION.localId);
        String condition = tuple.get(INVESTIGATION.cdDescTxt);

        return new PatientContacts.Investigation(
                identifier,
                local,
                condition
        );
    }
}
