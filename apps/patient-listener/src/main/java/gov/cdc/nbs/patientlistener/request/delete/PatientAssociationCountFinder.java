package gov.cdc.nbs.patientlistener.request.delete;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.QPerson;
import org.springframework.stereotype.Component;

@Component
class PatientAssociationCountFinder {

    private final JPAQueryFactory factory;
    private final QPerson revision;

    PatientAssociationCountFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.revision = new QPerson("revision");
    }

    long count(final long patient) {
        Long revisions = this.factory.select(this.revision.count())
            .from(this.revision)
            .where(
                this.revision.personParentUid.id.eq(patient),
                this.revision.recordStatusCd.ne(RecordStatus.LOG_DEL),
                this.revision.id.ne(patient)
            ).fetchOne();

        return revisions == null ? 0 : revisions;

    }
}
