package gov.cdc.nbs.patient.document;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.NbsDocument;
import gov.cdc.nbs.entity.odse.QNbsDocument;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
class TestDocumentCleaner {

  public static final QNbsDocument DOCUMENT = QNbsDocument.nbsDocument;
  private final EntityManager entityManager;
  private final JPAQueryFactory factory;

  TestDocumentCleaner(final EntityManager entityManager, final JPAQueryFactory factory) {
    this.entityManager = entityManager;
    this.factory = factory;
  }

  /**
   * Removes any Documents from the database with an id greater than the given {@code starting} value
   * @param starting
   */
  @Transactional
  void clean(final long starting) {
    this.factory.select(DOCUMENT)
        .from(DOCUMENT)
        .where(criteria(starting))
        .fetch()
        .forEach(this::remove);
  }

  private BooleanExpression criteria(final long starting) {
    BooleanExpression threshold = DOCUMENT.id.goe(starting);
    return starting < 0
        ? threshold.and(DOCUMENT.id.lt(0))
        : threshold;
  }

  private void remove(final NbsDocument document) {
    removeParticipations(document);

    this.entityManager.remove(document);
  }

  private void removeParticipations(final NbsDocument document) {
    //  The all document participation instances are associated with the Act entity.  Removing the Act will remove any
    //  associations.
    Act existing = this.entityManager.find(Act.class, document.getId());

    if (existing != null) {
      this.entityManager.remove(existing);
    }
  }
}
