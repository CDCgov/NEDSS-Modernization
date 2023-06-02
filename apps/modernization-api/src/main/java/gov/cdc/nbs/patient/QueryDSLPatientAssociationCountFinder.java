package gov.cdc.nbs.patient;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.QPerson;
import org.springframework.stereotype.Component;

@Component
public class QueryDSLPatientAssociationCountFinder implements PatientAssociationCountFinder {

    private final JPAQueryFactory factory;
    private final QPerson revision;

    public QueryDSLPatientAssociationCountFinder(final JPAQueryFactory factory) {
        this.factory = factory;
        this.revision = QPerson.person;
    }

    @Override
    public long count(long patient) {
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
