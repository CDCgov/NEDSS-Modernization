package gov.cdc.nbs.patient.contact;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Coalesce;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.QCtContact;
import gov.cdc.nbs.entity.odse.QInterview;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.message.enums.Suffix;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Component
class ContactNamedByPatientFinder {
    private static final QCtContact TRACING = new QCtContact("tracing");
    private static final QPerson SUBJECT = new QPerson("subject");
    private static final QPublicHealthCase INVESTIGATION = new QPublicHealthCase("investigation");
    private static final QPerson CONTACT = new QPerson("contact");
    private static final QCodeValueGeneral PRIORITY = new QCodeValueGeneral("priority");
    private static final QCodeValueGeneral DISPOSITION = new QCodeValueGeneral("disposition");
    private static final QInterview INTERVIEW = new QInterview("interview");
    private static final Coalesce<Instant> NAMED_ON = new Coalesce<>(Instant.class)
            .add(TRACING.namedOnDate)
            .add(INTERVIEW.interviewDate)
            .add(TRACING.addTime);

    private final JPAQueryFactory factory;

    ContactNamedByPatientFinder(final JPAQueryFactory factory) {
        this.factory = factory;

    }

    List<PatientContacts.NamedByPatient> find(final long patient) {
        return this.factory.selectDistinct(
                TRACING.id,
                TRACING.addTime,
                TRACING.localId,
                PRIORITY.codeDescTxt,
                DISPOSITION.codeDescTxt,
                CONTACT.id,
                CONTACT.nmPrefix,
                CONTACT.firstNm,
                CONTACT.lastNm,
                CONTACT.nmSuffix,
                INVESTIGATION.id,
                INVESTIGATION.localId,
                INVESTIGATION.cdDescTxt,
                NAMED_ON)
                .from(TRACING)
                .join(SUBJECT).on(
                        TRACING.subjectNBSEntityUid.id.eq(SUBJECT.id))
                .join(INVESTIGATION).on(
                        INVESTIGATION.id.eq(TRACING.subjectEntityPhcUid.id))
                .join(CONTACT).on(
                        CONTACT.id.eq(TRACING.contactNBSEntityUid.id))
                .leftJoin(INTERVIEW).on(
                        INTERVIEW.id.eq(TRACING.namedDuringInterviewUid))
                .leftJoin(PRIORITY).on(
                        PRIORITY.id.code.eq(TRACING.priorityCd))
                .leftJoin(DISPOSITION).on(
                        DISPOSITION.id.code.eq(TRACING.dispositionCd))
                .where(TRACING.recordStatusCd.eq("ACTIVE"),
                        SUBJECT.personParentUid.id.eq(patient))
                .fetch()
                .stream()
                .map(this::map)
                .toList();
    }

    private PatientContacts.NamedByPatient map(final Tuple tuple) {
        Long identifier = tuple.get(TRACING.id);
        Instant createdOn = tuple.get(TRACING.addTime);
        String condition = tuple.get(INVESTIGATION.cdDescTxt);
        PatientContacts.NamedContact contact = mapContact(tuple);
        Instant namedOn = tuple.get(NAMED_ON);

        String priority = tuple.get(PRIORITY.codeDescTxt);
        String disposition = tuple.get(DISPOSITION.codeDescTxt);
        String event = tuple.get(TRACING.localId);

        return new PatientContacts.NamedByPatient(
                Objects.requireNonNull(identifier),
                createdOn,
                condition,
                contact,
                namedOn,
                priority,
                disposition,
                event);
    }

    private PatientContacts.NamedContact mapContact(final Tuple tuple) {

        Long identifier = tuple.get(CONTACT.id);
        String prefix = tuple.get(CONTACT.nmPrefix);
        String first = tuple.get(CONTACT.firstNm);
        String last = tuple.get(CONTACT.lastNm);
        Suffix suffix = tuple.get(CONTACT.nmSuffix);

        String name = NameRenderer.render(
                prefix,
                first,
                last,
                suffix);

        return new PatientContacts.NamedContact(
                Objects.requireNonNull(identifier, "A contact identifier is required."),
                name);
    }

}
