package gov.cdc.nbs.service;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import gov.cdc.nbs.config.security.SecurityUtil.BusinessObjects;
import gov.cdc.nbs.config.security.SecurityUtil.Operations;
import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.ObservationRepository;
import gov.cdc.nbs.repository.ParticipationRepository;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.util.Constants;



@Service
public class EventService {
    private static final String HAS_AUTHORITY = "hasAuthority('";

    private static final String VIEW_MORBIDITY_REPORT = HAS_AUTHORITY + Operations.VIEW + "-"
            + BusinessObjects.OBSERVATIONMORBIDITYREPORT
            + "')";


    private final Integer maxPageSize;

    @PersistenceContext
    private final EntityManager entityManager;
    private final PersonRepository personReposity;
    private final ParticipationRepository participationRepository;
    private final ObservationRepository oboservationRepository;

    public EventService(
            @Value("${nbs.max-page-size: 50}") Integer maxPageSize,
            EntityManager entityManager,
            PersonRepository personReposity,
            ParticipationRepository participationRepository,
            ObservationRepository oboservationRepository) {
        this.maxPageSize = maxPageSize;
        this.entityManager = entityManager;
        this.personReposity = personReposity;
        this.participationRepository = participationRepository;
        this.oboservationRepository = oboservationRepository;
    }

    @PreAuthorize(VIEW_MORBIDITY_REPORT)
    public Page<Observation> findMorbidtyReportForPatient(Long patientId, GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, maxPageSize);
        List<Observation> reports = findMorbidityReportsForPatient(patientId);
        return new PageImpl<>(reports, pageable, reports.size());
    }

    public List<Observation> findMorbidityReportsForPatient(Long patientId) {
        List<Long> results = personReposity.getPersonIdsByPersonParentId(patientId);
        List<Long> actIdResults = participationRepository
                .findIdActUidByIdTypeCdAndIdSubjectEntityUidIn(Constants.REPORT_TYPE, results);
        return oboservationRepository.findByIdIn(actIdResults);

    }

}
