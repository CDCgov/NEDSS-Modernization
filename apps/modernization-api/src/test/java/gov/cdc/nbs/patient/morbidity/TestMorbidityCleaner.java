package gov.cdc.nbs.patient.morbidity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.entity.odse.QObservation;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
class TestMorbidityCleaner {

  private static final QObservation MORBIDITY = QObservation.observation;
  private final EntityManager entityManager;
  private final JPAQueryFactory factory;

  TestMorbidityCleaner(final EntityManager entityManager, final JPAQueryFactory factory) {
    this.entityManager = entityManager;
    this.factory = factory;
  }

  @Transactional
  void clean(final long starting) {
    this.factory.select(MORBIDITY)
        .from(MORBIDITY)
        .where(criteria(starting))
        .fetch()
        .forEach(this::remove);
  }

  private BooleanExpression criteria(final long starting) {
    BooleanExpression threshold = MORBIDITY.id.goe(starting);
    return starting < 0
        ? threshold.and(MORBIDITY.id.lt(0))
        : threshold;
  }

  private void remove(final Observation observation) {
    removeParticipation(observation);

    this.entityManager.remove(observation);
  }

  private void removeParticipation(final Observation observation) {
    //  The all observation participation instances are associated with the Act entity.  Removing the Act will remove any
    //  associations.
    Act existing = this.entityManager.find(Act.class, observation.getId());

    if (existing != null) {
      this.entityManager.remove(existing);
    }
  }
}
