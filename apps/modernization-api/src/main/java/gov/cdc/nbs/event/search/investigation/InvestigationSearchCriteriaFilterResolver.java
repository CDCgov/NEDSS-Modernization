package gov.cdc.nbs.event.search.investigation;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQuery;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.time.FlexibleInstantConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
class InvestigationSearchCriteriaFilterResolver {

  Query resolve(final InvestigationFilter criteria, final PermissionScope scope) {
    return Stream.of(
            withPermission(scope),
            withConditions(criteria),
            withProgramAreas(criteria),
            withJurisdictions(criteria),
            withPregnancyStatus(criteria),
            withABCSCase(criteria),
            withCountyCase(criteria),
            withStateCase(criteria),
            withReportedOn(criteria),
            withCreatedBy(criteria),
            withCreatedOn(criteria),
            withUpdatedBy(criteria),
            withUpdatedOn(criteria),
            withStartedOn(criteria),
            withClosedOn(criteria),
            withNotifiedOn(criteria),
            withPatient(criteria),
            withInvestigator(criteria),
            withReportingFacility(criteria),
            withReportingProvider(criteria),
            withStatus(criteria),
            withOutbreaks(criteria),
            withCaseStatus(criteria),
            withProcessingStatus(criteria),
            withNotificationStatus(criteria)
        ).flatMap(Optional::stream)
        .map(QueryVariant::_toQuery)
        .reduce(
            new BoolQuery.Builder(),
            BoolQuery.Builder::filter,
            (one, two) -> one.filter(two.build().filter())
        ).build()._toQuery();
  }

  private Optional<QueryVariant> withPermission(final PermissionScope scope) {
    TermsQuery statuses = scope.any()
        .stream()
        .map(FieldValue::of)
        .collect(
            Collectors.collectingAndThen(
                Collectors.toList(),
                collected -> TermsQuery.of(
                    query -> query.field("program_jurisdiction_oid")
                        .terms(terms -> terms.value(collected))
                )
            )
        );

    return Optional.of(statuses);
  }

  private Optional<QueryVariant> withConditions(final InvestigationFilter criteria) {
    List<String> condition = criteria.getConditions();

    return condition.isEmpty()
        ? Optional.empty()
        : Optional.of(
        condition
            .stream()
            .map(FieldValue::of)
            .collect(
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    collected -> TermsQuery.of(
                        query -> query.field("condition")
                            .terms(terms -> terms.value(collected))
                    )
                )
            )
    );
  }

  private Optional<QueryVariant> withProgramAreas(final InvestigationFilter criteria) {
    List<String> programAreas = criteria.getProgramAreas();

    return programAreas.isEmpty()
        ? Optional.empty()
        : Optional.of(
        programAreas
            .stream()
            .map(FieldValue::of)
            .collect(
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    collected -> TermsQuery.of(
                        query -> query.field("prog_area_cd")
                            .terms(terms -> terms.value(collected))
                    )
                )
            )
    );
  }

  private Optional<QueryVariant> withJurisdictions(final InvestigationFilter criteria) {
    List<Long> jurisdictions = criteria.getJurisdictions();

    return jurisdictions.isEmpty()
        ? Optional.empty()
        : Optional.of(
        jurisdictions
            .stream()
            .map(FieldValue::of)
            .collect(
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    collected -> TermsQuery.of(
                        query -> query.field("jurisdiction_cd")
                            .terms(terms -> terms.value(collected))
                    )
                )
            )
    );
  }

  private Optional<QueryVariant> withPregnancyStatus(final InvestigationFilter criteria) {
    PregnancyStatus status = criteria.getPregnancyStatus();

    return (status == null)
        ? Optional.empty()
        : Optional.of(
        TermQuery.of(
            term -> term.field("pregnant_ind_cd")
                .value(status.value())
        )
    );
  }

  private Optional<QueryVariant> withABCSCase(final InvestigationFilter criteria) {
    return criteria.abcsCase().map(
        number -> NestedQuery.of(
            nested -> nested.path(SearchableInvestigation.IDENTIFICATION)
                .scoreMode(ChildScoreMode.Avg)
                .query(
                    query -> query.bool(
                        bool -> bool.filter(
                                filter -> filter.term(
                                    term -> term.field(SearchableInvestigation.IDENTIFICATION_TYPE)
                                        .value("STATE")
                                )
                            )
                            .filter(
                                filter -> filter.term(
                                    term -> term.field(SearchableInvestigation.IDENTIFICATION_SEQUENCE)
                                        .value(2)
                                )
                            )
                    )
                )
        )
    );
  }

  private Optional<QueryVariant> withCountyCase(final InvestigationFilter criteria) {
    return criteria.countyCase().map(
        number -> NestedQuery.of(
            nested -> nested.path(SearchableInvestigation.IDENTIFICATION)
                .scoreMode(ChildScoreMode.Avg)
                .query(
                    query -> query.bool(
                        bool -> bool.filter(
                            filter -> filter.term(
                                term -> term.field("act_ids.type_cd")
                                    .value("CITY")
                            )
                        )
                    )
                )
        )
    );
  }

  private Optional<QueryVariant> withStateCase(final InvestigationFilter criteria) {
    return criteria.stateCase().map(
        number -> NestedQuery.of(
            nested -> nested.path(SearchableInvestigation.IDENTIFICATION)
                .scoreMode(ChildScoreMode.Avg)
                .query(
                    query -> query.bool(
                        bool -> bool.filter(
                            filter -> filter.term(
                                term -> term.field(SearchableInvestigation.IDENTIFICATION_TYPE)
                                    .value("STATE")
                            )
                        )
                    )
                )
        )
    );
  }

  private Optional<QueryVariant> withReportedOn(final InvestigationFilter criteria) {
    return criteria.reportedOn().map(
        reported ->
            RangeQuery.of(
                range -> range.term(
                    term -> term.field("rpt_form_cmplt_time")
                        .from(FlexibleInstantConverter.toString(reported.getFrom()))
                        .to(FlexibleInstantConverter.toString(reported.getTo())
                        )
                )
            )
    );
  }

  private Optional<QueryVariant> withStartedOn(final InvestigationFilter criteria) {
    return criteria.startedOn().map(
        reported ->
            RangeQuery.of(
                range -> range.term(
                    term -> term.field("activity_from_time")
                        .from(FlexibleInstantConverter.toString(reported.getFrom()))
                        .to(FlexibleInstantConverter.toString(reported.getTo())
                        )
                )
            )
    );
  }

  private Optional<QueryVariant> withClosedOn(final InvestigationFilter criteria) {
    return criteria.closedOn().map(
        reported ->
            RangeQuery.of(
                range -> range.term(
                    term -> term.field("activity_to_time")
                        .from(FlexibleInstantConverter.toString(reported.getFrom()))
                        .to(FlexibleInstantConverter.toString(reported.getTo()))
                )
            )
    );
  }

  private Optional<QueryVariant> withNotifiedOn(final InvestigationFilter criteria) {
    return criteria.notifiedOn().map(
        reported ->
            RangeQuery.of(
                range -> range.term(
                    term -> term.field("notification_add_time")
                        .from(FlexibleInstantConverter.toString(reported.getFrom()))
                        .to(FlexibleInstantConverter.toString(reported.getTo()))
                )
            )
    );
  }

  private Optional<QueryVariant> withCreatedBy(final InvestigationFilter criteria) {
    String createdBy = criteria.getCreatedBy();

    if (createdBy == null) {
      return Optional.empty();
    }

    return Optional.of(
        TermQuery.of(
            term -> term.field("add_user_id")
                .value(createdBy)
        )
    );
  }

  private Optional<QueryVariant> withCreatedOn(final InvestigationFilter criteria) {
    return criteria.createdOn().map(
        reported ->
            RangeQuery.of(
                range -> range.term(
                    term -> term.field("add_time")
                        .from(FlexibleInstantConverter.toString(reported.getFrom()))
                        .to(FlexibleInstantConverter.toString(reported.getTo()))
                )
            )
    );
  }

  private Optional<QueryVariant> withUpdatedBy(final InvestigationFilter criteria) {
    String updatedBy = criteria.getLastUpdatedBy();

    if (updatedBy == null) {
      return Optional.empty();
    }

    return Optional.of(
        TermQuery.of(
            term -> term.field("last_chg_user_id")
                .value(updatedBy)
        )
    );
  }

  private Optional<QueryVariant> withUpdatedOn(final InvestigationFilter criteria) {
    return criteria.updatedOn().map(
        reported ->
            RangeQuery.of(
                range -> range.term(
                    term -> term.field("public_health_case_last_chg_time")
                        .from(FlexibleInstantConverter.toString(reported.getFrom()))
                        .to(FlexibleInstantConverter.toString(reported.getTo()))
                )
            )
    );
  }

  private Optional<QueryVariant> withPatient(final InvestigationFilter criteria) {
    Long patient = criteria.getPatientId();

    return (patient == null)
        ? Optional.empty()
        : Optional.of(
        NestedQuery.of(
            nested -> nested.path(SearchableInvestigation.PERSON)
                .scoreMode(ChildScoreMode.None)
                .query(
                    query -> query.bool(
                        bool -> bool.filter(
                            filter -> filter.term(
                                term -> term.field(SearchableInvestigation.PERSON_TYPE)
                                    .value("SubjOfPHC")
                            )
                        ).must(
                            must -> must.term(
                                term -> term.field("person_participations.person_parent_uid")
                                    .value(patient)
                            )
                        )
                    )
                )
        )
    );
  }

  private Optional<QueryVariant> withInvestigator(final InvestigationFilter criteria) {
    return criteria.getInvestigatorId() == null
        ? Optional.empty()
        : Optional.of(
        NestedQuery.of(
            nested -> nested.path(SearchableInvestigation.PERSON)
                .scoreMode(ChildScoreMode.None)
                .query(
                    query -> query.bool(
                        bool -> bool.filter(
                            filter -> filter.term(
                                term -> term.field(SearchableInvestigation.PERSON_TYPE)
                                    .value("InvestgrOfPHC")
                            )
                        ).must(
                            must -> must.term(
                                term -> term.field(SearchableInvestigation.PERSON_IDENTIFIER)
                                    .value(criteria.getInvestigatorId())
                            )
                        )
                    )
                )
        )
    );
  }

  private Optional<QueryVariant> withReportingFacility(final InvestigationFilter criteria) {
    return criteria.reportingFacility().map(
        facility -> NestedQuery.of(
            nested -> nested.path(SearchableInvestigation.ORGANIZATION)
                .scoreMode(ChildScoreMode.None)
                .query(
                    query -> query.bool(
                        bool -> bool.filter(
                            filter -> filter.term(
                                term -> term.field(SearchableInvestigation.ORGANIZATION_TYPE)
                                    .value("OrgAsReporterOfPHC")
                            )
                        ).must(
                            must -> must.term(
                                term -> term.field(SearchableInvestigation.ORGANIZATION_IDENTIFIER)
                                    .value(facility)
                            )
                        )
                    )
                )
        )
    );
  }

  private Optional<QueryVariant> withReportingProvider(final InvestigationFilter criteria) {
    return criteria.reportingProvider().map(
        provider -> NestedQuery.of(
            nested -> nested.path(SearchableInvestigation.PERSON)
                .scoreMode(ChildScoreMode.None)
                .query(
                    query -> query.bool(
                        bool -> bool.filter(
                            filter -> filter.term(
                                term -> term.field(SearchableInvestigation.PERSON_TYPE)
                                    .value("PerAsReporterOfPHC")
                            )
                        ).must(
                            must -> must.term(
                                term -> term.field(SearchableInvestigation.PERSON_IDENTIFIER)
                                    .value(provider)
                            )
                        )
                    )
                )
        )
    );
  }

  private Optional<QueryVariant> withStatus(final InvestigationFilter criteria) {
    return criteria.getInvestigationStatus() == null
        ? Optional.empty()
        : Optional.of(
        TermQuery.of(
            term -> term.field("investigation_status_cd")
                .value(criteria.getInvestigationStatus().value())
        )
    );
  }

  private Optional<QueryVariant> withOutbreaks(final InvestigationFilter criteria) {
    List<String> outbreaks = criteria.getOutbreakNames();

    return outbreaks.isEmpty()
        ? Optional.empty()
        : Optional.of(
        outbreaks
            .stream()
            .map(FieldValue::of)
            .collect(
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    collected -> TermsQuery.of(
                        query -> query.field("outbreak_name")
                            .terms(terms -> terms.value(collected))
                    )
                )
            )
    );
  }

  private Optional<QueryVariant> withCaseStatus(final InvestigationFilter criteria) {
    return criteria.getCaseStatuses().isEmpty()
        ? Optional.empty()
        : Optional.of(
        criteria.getCaseStatuses()
            .stream()
            .map(InvestigationFilter.CaseStatus::value)
            .map(FieldValue::of)
            .collect(
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    collected -> TermsQuery.of(
                        query -> query.field("case_class_cd")
                            .terms(terms -> terms.value(collected))
                    )
                )
            )
    );
  }

  private Optional<QueryVariant> withProcessingStatus(final InvestigationFilter criteria) {
    return criteria.getProcessingStatuses().isEmpty()
        ? Optional.empty()
        : Optional.of(
        criteria.getProcessingStatuses()
            .stream()
            .map(InvestigationFilter.ProcessingStatus::value)
            .map(FieldValue::of)
            .collect(
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    collected -> TermsQuery.of(
                        query -> query.field("curr_process_state_cd")
                            .terms(terms -> terms.value(collected))
                    )
                )
            )
    );
  }

  private Optional<QueryVariant> withNotificationStatus(final InvestigationFilter criteria) {
    return criteria.getNotificationStatuses().isEmpty()
        ? Optional.empty()
        : Optional.of(
        criteria.getNotificationStatuses()
            .stream()
            .map(InvestigationFilter.NotificationStatus::value)
            .map(FieldValue::of)
            .collect(
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    collected -> TermsQuery.of(
                        query -> query.field("notification_record_status_cd")
                            .terms(terms -> terms.value(collected))
                    )
                )
            )
    );
  }
}
