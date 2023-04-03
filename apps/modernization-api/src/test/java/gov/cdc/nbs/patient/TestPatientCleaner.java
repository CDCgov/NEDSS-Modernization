package gov.cdc.nbs.patient;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.QPerson;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
class TestPatientCleaner {

  public static final QPerson PATIENT = QPerson.person;
  private final EntityManager entityManager;

  private final JPAQueryFactory factory;


  TestPatientCleaner(final EntityManager entityManager, final JPAQueryFactory factory) {
    this.entityManager = entityManager;
    this.factory = factory;
  }

  @Transactional
  void clean(final long starting) {
    this.factory.select(PATIENT)
        .from(PATIENT)
        .where(criteria(starting))
        .fetch()
        .forEach(this.entityManager::remove);
  }

  private BooleanExpression criteria(final long starting) {
    BooleanExpression threshold = PATIENT.id.goe(starting);
    return starting < 0
        ? threshold.and(PATIENT.id.lt(0))
        : threshold;
  }

}
