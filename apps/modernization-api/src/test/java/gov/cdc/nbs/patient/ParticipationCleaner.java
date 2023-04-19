package gov.cdc.nbs.patient;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.QAct;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;


@Component
public class ParticipationCleaner {

    private final JPAQueryFactory factory;
    private final EntityManager entityManager;

    public ParticipationCleaner(
        final JPAQueryFactory factory,
        final EntityManager entityManager
    ) {
        this.factory = factory;
        this.entityManager = entityManager;
    }

    public void clean(final long identifier, final String classCode) {
        this.factory.from(QAct.act)
            .where(
                QAct.act.classCd.eq(classCode),
                QAct.act.id.eq(identifier)
            ).fetch()
            //  All participation instances are associated with the Act entity that
            //  shares the same identifier.  Removing the Act will remove any associations.
            .forEach(entityManager::remove);

    }

}
