package gov.cdc.nbs.patient;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.QPerson;
import org.springframework.stereotype.Component;

@Component
class QueryDSLPatientAssociationCountFinder implements PatientAssociationCountFinder {

  private final JPAQueryFactory factory;
  private final QPerson revision;

  QueryDSLPatientAssociationCountFinder(final JPAQueryFactory factory) {
    this.factory = factory;
    this.revision = QPerson.person;
  }

  @Override
  public long count(long patient) {
    Long revisions = this.factory.select(this.revision.count())
        .from(this.revision)
        .where(
            this.revision.personParentUid.id.eq(patient),
            // In addition to counting associations we also check that the person is not deleted
            this.revision.recordStatus.status.ne("LOG_DEL"),
            this.revision.id.ne(patient)
        ).fetchOne();

    return revisions == null ? 0 : revisions;
  }
}
