package gov.cdc.nbs.patient.documentsrequiringreview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.authentication.NbsAuthority;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.entity.odse.QObsValueCoded;
import gov.cdc.nbs.entity.odse.QObservation;
import gov.cdc.nbs.entity.odse.QOrganization;
import gov.cdc.nbs.entity.odse.QParticipation;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview.Description;
import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview.FacilityProviders;
import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview.OrderingProvider;
import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview.ReportingFacility;
import gov.cdc.nbs.service.SecurityService;

@ExtendWith(MockitoExtension.class)
class ReviewDocumentFinderTest {

    @Mock
    private UserDetailsProvider detailsProvider;

    @Mock
    private JPAQueryFactory queryFactory;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private ReviewDocumentFinder finder;

    @Test
    void should_contain_three_where_clauses() {
        // given the user has all permissions
        NbsUserDetails userDetails = allPermissions();

        // When I get the 'WHERE' clauses for the request
        List<BooleanExpression> clauses = finder.getWhereClauses(userDetails);

        // Then all three 'WHERE' clauses are returned
        assertNotNull(clauses);
        assertEquals(3, clauses.size());
    }

    @Test
    void should_only_contain_document_where_clauses() {
        // given the user has all permissions
        NbsUserDetails userDetails = documentPermission();

        // When I get the 'WHERE' clauses for the request
        List<BooleanExpression> clauses = finder.getWhereClauses(userDetails);

        // one 'WHERE' clause is returned
        assertNotNull(clauses);
        assertEquals(1, clauses.size());
        assertTrue(clauses.get(0).toString().contains("nbsDocument.recordStatusCd"));
    }

    @Test
    void should_only_contain_lab_where_clauses() {
        // given the user has all permissions
        NbsUserDetails userDetails = labPermission();

        // When I get the 'WHERE' clauses for the request
        List<BooleanExpression> clauses = finder.getWhereClauses(userDetails);

        // one 'WHERE' clause is returned
        assertNotNull(clauses);
        assertEquals(1, clauses.size());
        assertTrue(clauses.get(0).toString().contains("participation.id.typeCd = PATSBJ"));
    }

    @Test
    void should_only_contain_morb_where_clauses() {
        // given the user has all permissions
        NbsUserDetails userDetails = morbPermission();

        // When I get the 'WHERE' clauses for the request
        List<BooleanExpression> clauses = finder.getWhereClauses(userDetails);

        // one 'WHERE' clause is returned
        assertNotNull(clauses);
        assertEquals(1, clauses.size());
        assertTrue(clauses.get(0).toString().contains("participation.id.typeCd = SubjOfMorbReport"));
    }

    @Test
    void should_contain_no_where_clauses() {
        // given the user has all permissions
        NbsUserDetails userDetails = NbsUserDetails.builder().build();

        // When I get the 'WHERE' clauses for the request
        List<BooleanExpression> clauses = finder.getWhereClauses(userDetails);

        // no 'WHERE' clause is returned
        assertNotNull(clauses);
        assertEquals(0, clauses.size());
    }

    @ParameterizedTest
    @CsvSource({
            "type,ASC",
            "type,DESC",
            "dateReceived,ASC",
            "dateReceived,DESC",
            "eventDate,ASC",
            "eventDate,DESC",
            "localId,ASC",
            "localId,DESC"
    })
    void should_set_sort(String field, String direction) {
        // Given a sort criteria
        Direction d = Direction.fromString(direction);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(d, field));

        // When the orderSpecifier is created
        OrderSpecifier<?> order = finder.getSort(pageable);

        // Then it is created with the proper values
        assertEquals(field, order.getTarget().toString());
        assertEquals(d.toString(), order.getOrder().name());
    }

    @Test
    void should_set_sort_from_null() {
        // Given a null sort criterua
        OrderSpecifier<?> order = finder.getSort(null);

        // Then a default is set
        assertEquals("type", order.getTarget().toString());
        assertEquals("DESC", order.getOrder().name());
    }

    @Test
    void should_set_sort_from_invalid_option() {
        // Given an invalid sort parameter
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "not supported"));
        OrderSpecifier<?> order = finder.getSort(pageable);

        // Then a default is set
        assertEquals("type", order.getTarget().toString());
        assertEquals("DESC", order.getOrder().name());
    }


    @Test
    void should_set_lab_data() {
        // Given a DocumentRequiringReview for a lab report
        Page<DocumentRequiringReview> docs = new PageImpl<>(Collections.singletonList(labDocumentRequiringReview()));

        // And extended data for a lab report
        var tuple = mockLabMorbExtendedQuery();
        List<Tuple> results = labReportData(1L);
        when(tuple.fetch()).thenReturn(results);

        // When I add extended data to it
        finder.setLabAndMorbReportData(docs);

        // Then the proper data is set
        assertNotNull(docs);
        DocumentRequiringReview actual = docs.getContent().get(0);

        OrderingProvider orderingProvider = actual.facilityProviders().getOrderingProvider();
        assertEquals("labPrefix labFirstName labLastName ESQ", orderingProvider.name());

        ReportingFacility reportingFacility = actual.facilityProviders().getReportingFacility();
        assertEquals("labReportingFacility", reportingFacility.name());

        assertEquals(1, actual.descriptions().size());
        Description description = actual.descriptions().get(0);
        assertEquals("Title", description.title());
        assertEquals("Value", description.value());
    }

    @Test
    void should_set_morb_data() {
        // Given a DocumentRequiringReview for a lab report
        Page<DocumentRequiringReview> docs = new PageImpl<>(Collections.singletonList(morbDocumentRequiringReview()));

        // And extended data for a lab report
        var tuple = mockLabMorbExtendedQuery();
        List<Tuple> results = morbReportData(1L);
        when(tuple.fetch()).thenReturn(results);

        // When I add extended data to it
        finder.setLabAndMorbReportData(docs);

        // Then the proper data is set
        assertNotNull(docs);
        DocumentRequiringReview actual = docs.getContent().get(0);

        OrderingProvider orderingProvider = actual.facilityProviders().getOrderingProvider();
        assertEquals("morbPrefix morbFirstName morbLastName", orderingProvider.name());

        ReportingFacility reportingFacility = actual.facilityProviders().getReportingFacility();
        assertEquals("morbReportingFacility", reportingFacility.name());

        assertEquals(1, actual.descriptions().size());
        Description description = actual.descriptions().get(0);
        assertEquals("Title", description.title());
        assertEquals("Value", description.value());
    }

    @Test
    void should_throw_exception_if_no_id() {
        // Given a DocumentRequiringReview for a lab report
        Page<DocumentRequiringReview> docs = new PageImpl<>(Collections.singletonList(morbDocumentRequiringReview()));

        // And extended data for a lab report that has an incorrect Id
        JPAQuery<Tuple> query = mockLabMorbExtendedQuery();
        Tuple row = Mockito.mock(Tuple.class);
        when(row.get(QObservation.observation.id)).thenReturn(9L);

        List<Tuple> results = Collections.singletonList(row);
        when(query.fetch()).thenReturn(results);

        // When I add extended data to it
        // Then an exception should be thrown
        assertThrows(RuntimeException.class, () -> finder.setLabAndMorbReportData(docs));
    }

    private DocumentRequiringReview labDocumentRequiringReview() {
        return new DocumentRequiringReview(
                1L,
                null,
                "LabReport",
                null,
                null,
                false,
                false,
                new FacilityProviders(),
                new ArrayList<>());
    }

    private DocumentRequiringReview morbDocumentRequiringReview() {
        return new DocumentRequiringReview(
                1L,
                null,
                "MorbReport",
                null,
                null,
                false,
                false,
                new FacilityProviders(),
                new ArrayList<>());
    }

    private NbsUserDetails allPermissions() {
        NbsAuthority documentView = authority("DOCUMENT", "VIEW");
        NbsAuthority labView = authority("OBSERVATIONLABREPORT", "VIEW");
        NbsAuthority morbView = authority("OBSERVATIONMORBIDITYREPORT", "VIEW");
        return NbsUserDetails.builder()
                .authorities(Stream.of(documentView, labView, morbView).collect(Collectors.toCollection(HashSet::new)))
                .build();
    }

    private NbsUserDetails documentPermission() {
        NbsAuthority documentView = authority("DOCUMENT", "VIEW");
        return NbsUserDetails.builder()
                .authorities(Collections.singleton(documentView))
                .build();
    }

    private NbsUserDetails labPermission() {
        NbsAuthority documentView = authority("OBSERVATIONLABREPORT", "VIEW");
        return NbsUserDetails.builder()
                .authorities(Collections.singleton(documentView))
                .build();
    }

    private NbsUserDetails morbPermission() {
        NbsAuthority documentView = authority("OBSERVATIONMORBIDITYREPORT", "VIEW");
        return NbsUserDetails.builder()
                .authorities(Collections.singleton(documentView))
                .build();
    }

    private NbsAuthority authority(String object, String operation) {
        return NbsAuthority.builder()
                .businessObject(object)
                .businessOperation(operation)
                .build();
    }

    @SuppressWarnings("unchecked")
    private JPAQuery<Tuple> mockLabMorbExtendedQuery() {
        JPAQuery<Tuple> tuple = Mockito.mock(JPAQuery.class);
        QObservation OBSERVATION = QObservation.observation;
        QObservation OBSERVATION_2 = new QObservation("obs2");
        QParticipation PARTICIPATION = QParticipation.participation;
        QOrganization ORGANIZATION = QOrganization.organization;
        QObsValueCoded OBS_VALUE_CODED = QObsValueCoded.obsValueCoded;
        QPersonName PERSON_NAME = QPersonName.personName;

        when(queryFactory.select(
                OBSERVATION.id,
                PARTICIPATION.subjectClassCd,
                PARTICIPATION.id.typeCd,
                PARTICIPATION.id.subjectEntityUid,
                ORGANIZATION.displayNm,
                PERSON_NAME.nmPrefix,
                PERSON_NAME.firstNm,
                PERSON_NAME.lastNm,
                PERSON_NAME.nmSuffix,
                OBS_VALUE_CODED.displayName,
                OBSERVATION_2.cdDescTxt)).thenReturn(tuple);

        when(tuple.from(OBSERVATION)).thenReturn(tuple);
        when(tuple.leftJoin(Mockito.any(EntityPathBase.class))).thenReturn(tuple);
        when(tuple.join(Mockito.any(EntityPathBase.class))).thenReturn(tuple);
        when(tuple.on(Mockito.any(BooleanExpression.class))).thenReturn(tuple);
        when(tuple.where(Mockito.any(BooleanExpression.class))).thenReturn(tuple);

        return tuple;
    }

    private List<Tuple> labReportData(long labId) {
        return Arrays.asList(
                labReportOrderingProvider(labId),
                labReportReportingFacility(labId),
                labMorbDescription(labId));
    }

    private List<Tuple> morbReportData(long morbId) {
        return Arrays.asList(
                morbReportOrderingProvider(morbId),
                morbReportReportingFacility(morbId),
                labMorbDescription(morbId));
    }

    private Tuple labReportOrderingProvider(long id) {
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(QParticipation.participation.id.typeCd)).thenReturn("ORD");
        when(tuple.get(QObservation.observation.id)).thenReturn(id);
        when(tuple.get(QPersonName.personName.nmPrefix)).thenReturn("labPrefix");
        when(tuple.get(QPersonName.personName.firstNm)).thenReturn("labFirstName");
        when(tuple.get(QPersonName.personName.lastNm)).thenReturn("labLastName");
        when(tuple.get(QPersonName.personName.nmSuffix)).thenReturn(Suffix.ESQ);
        return tuple;
    }

    private Tuple morbReportOrderingProvider(long id) {
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(QParticipation.participation.id.typeCd)).thenReturn("PhysicianOfMorb");
        when(tuple.get(QObservation.observation.id)).thenReturn(id);
        when(tuple.get(QPersonName.personName.nmPrefix)).thenReturn("morbPrefix");
        when(tuple.get(QPersonName.personName.firstNm)).thenReturn("morbFirstName");
        when(tuple.get(QPersonName.personName.lastNm)).thenReturn("morbLastName");
        when(tuple.get(QPersonName.personName.nmSuffix)).thenReturn(null);
        return tuple;
    }

    private Tuple labReportReportingFacility(long id) {
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(QObservation.observation.id)).thenReturn(id);
        when(tuple.get(QParticipation.participation.id.typeCd)).thenReturn("AUT");
        when(tuple.get(QOrganization.organization.displayNm)).thenReturn("labReportingFacility");
        return tuple;
    }

    private Tuple morbReportReportingFacility(long id) {
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(QObservation.observation.id)).thenReturn(id);
        when(tuple.get(QParticipation.participation.id.typeCd)).thenReturn("ReporterOfMorbReport");
        when(tuple.get(QOrganization.organization.displayNm)).thenReturn("morbReportingFacility");

        return tuple;
    }

    private Tuple labMorbDescription(long id) {
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(QObservation.observation.id)).thenReturn(id);
        when(tuple.get(QParticipation.participation.id.typeCd)).thenReturn("SPC");
        when(tuple.get(new QObservation("obs2").cdDescTxt)).thenReturn("Title");
        when(tuple.get(QObsValueCoded.obsValueCoded.displayName)).thenReturn("Value");
        return tuple;
    }

}
