package gov.cdc.nbs.patient.delete;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.QPerson;
import org.springframework.stereotype.Component;

@Component
public class PatientIsDeletableResolver {

    private final JPAQueryFactory factory;
    private final QPerson revision;

    public PatientIsDeletableResolver(final JPAQueryFactory factory) {
        this.factory = factory;
        this.revision = new QPerson("revision");
    }

    public boolean canDelete(final long patient) {
        Long revisions = this.factory.select(this.revision.count())
            .from(this.revision)
            .where(
                this.revision.personParentUid.id.eq(patient),
                this.revision.recordStatusCd.ne(RecordStatus.LOG_DEL),
                this.revision.id.ne(patient)
            ).fetchOne();

        return revisions == null || revisions == 0;

    }
}
