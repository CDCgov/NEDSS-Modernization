package gov.cdc.nbs.patient.profile.report.lab;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.entity.odse.QObservation;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Component
class TestLabReportCleaner {

    private static final QObservation LAB = QObservation.observation;
    private final EntityManager entityManager;
    private final JPAQueryFactory factory;

    TestLabReportCleaner(final EntityManager entityManager, final JPAQueryFactory factory) {
        this.entityManager = entityManager;
        this.factory = factory;
    }

    @Transactional
    void clean(final long starting) {
        this.factory.select(LAB)
                .from(LAB)
                .where(criteria(starting), LAB.ctrlCdDisplayForm.eq("LabReport"))
                .fetch()
                .forEach(this::remove);
    }

    private BooleanExpression criteria(final long starting) {
        BooleanExpression threshold = LAB.id.goe(starting);
        return starting < 0
                ? threshold.and(LAB.id.lt(0))
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
