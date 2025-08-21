package gov.cdc.nbs.event.search.labreport;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQuery;
import com.google.common.collect.Streams;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.message.enums.PregnancyStatus;
import gov.cdc.nbs.time.FlexibleInstantConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
class LabReportSearchCriteriaFilterResolver {

  public static final String VERSION = "version_ctrl_nbr";

  Query resolve(final LabReportFilter criteria, final PermissionScope scope) {
    return Stream.of(
            withPermission(scope),
            withProgramAreas(criteria),
            withJurisdictions(criteria),
            withPregnancyStatus(criteria),
            withAccessionNumber(criteria),
            withReportedOn(criteria),
            withCollectedOn(criteria),
            withReceivedOn(criteria),
            withCreatedBy(criteria),
            withCreatedOn(criteria),
            withUpdatedBy(criteria),
            withUpdatedOn(criteria),
            withEntry(criteria),
            withStatus(criteria),
            withEventStatus(criteria),
            withOrderingFacility(criteria),
            withReportingFacility(criteria),
            withOrderingProvider(criteria),
            withPatient(criteria)
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

  private Optional<QueryVariant> withProgramAreas(final LabReportFilter criteria) {

    List<String> programAreas = criteria.getProgramAreas();

    if (!programAreas.isEmpty()) {
      TermsQuery areas = programAreas
          .stream()
          .map(FieldValue::of)
          .collect(
              Collectors.collectingAndThen(
                  Collectors.toList(),
                  collected -> TermsQuery.of(
                      query -> query.field("program_area_cd")
                          .terms(terms -> terms.value(collected))
                  )
              )
          );
      return Optional.of(areas);
    }
    return Optional.empty();
  }

  private Optional<QueryVariant> withJurisdictions(final LabReportFilter criteria) {

    List<Long> jurisdictions = criteria.getJurisdictions();

    if (!jurisdictions.isEmpty()) {
      TermsQuery areas = jurisdictions
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
          );
      return Optional.of(areas);
    }
    return Optional.empty();
  }

  private Optional<QueryVariant> withPregnancyStatus(final LabReportFilter criteria) {
    PregnancyStatus status = criteria.getPregnancyStatus();

    if (status == null) {
      return Optional.empty();

    }

    return Optional.of(
        TermQuery.of(
            term -> term.field("pregnant_ind_cd").value(status.value())
        )
    );

  }

  private Optional<QueryVariant> withAccessionNumber(final LabReportFilter criteria) {
    return criteria.accession().map(
        accession -> NestedQuery.of(
            nested -> nested.path("act_ids")
                .scoreMode(ChildScoreMode.Avg)
                .query(
                    query -> query.bool(
                        bool -> bool.filter(
                            filter -> filter.term(
                                term -> term.field("act_ids.type_cd")
                                    .value("FN")
                            )
                        )
                    )
                )
        )
    );
  }

  private Optional<QueryVariant> withReportedOn(final LabReportFilter criteria) {
    return criteria.reportedOn().map(
        reported ->
            RangeQuery.of(
                range -> range.term(
                    term -> term.field("activity_to_time")
                        .gte(FlexibleInstantConverter.toString(reported.getFrom()))
                        .lte(FlexibleInstantConverter.toString(reported.getTo()))
                )
            )
    );
  }

  private Optional<QueryVariant> withCollectedOn(final LabReportFilter criteria) {
    return criteria.collectedOn().map(
        reported ->
            RangeQuery.of(
                range -> range.term(
                    term -> term.field("effective_from_time")
                        .gte(FlexibleInstantConverter.toString(reported.getFrom()))
                        .lte(FlexibleInstantConverter.toString(reported.getTo()))
                )
            )
    );
  }

  private Optional<QueryVariant> withReceivedOn(final LabReportFilter criteria) {
    return criteria.receivedOn().map(
        reported ->
            RangeQuery.of(
                range -> range.term(
                    term -> term.field("rpt_to_state_time")
                        .gte(FlexibleInstantConverter.toString(reported.getFrom()))
                        .lte(FlexibleInstantConverter.toString(reported.getTo()))
                )
            )
    );
  }

  private Optional<QueryVariant> withCreatedBy(final LabReportFilter criteria) {
    Long createdBy = criteria.getCreatedBy();

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

  private Optional<QueryVariant> withCreatedOn(final LabReportFilter criteria) {
    return criteria.createdOn().map(
        reported ->
            RangeQuery.of(
                range -> range.term(
                    term -> term.field("add_time")
                        .gte(FlexibleInstantConverter.toString(reported.getFrom()))
                        .lte(FlexibleInstantConverter.toString(reported.getTo()))
                )
            )
    );
  }

  private Optional<QueryVariant> withUpdatedBy(final LabReportFilter criteria) {
    Long updatedBy = criteria.getLastUpdatedBy();

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

  private Optional<QueryVariant> withUpdatedOn(final LabReportFilter criteria) {
    return criteria.updatedOn().map(
        reported ->
            RangeQuery.of(
                range -> range.term(term -> term.field("observation_last_chg_time")
                    .gte(FlexibleInstantConverter.toString(reported.getFrom()))
                    .lte(FlexibleInstantConverter.toString(reported.getTo()))
                )
            )
    );
  }

  private Optional<QueryVariant> withEntry(final LabReportFilter criteria) {

    if (criteria.getEntryMethods().isEmpty() && criteria.getEnteredBy().isEmpty()) {
      return Optional.empty();
    }

    TermsQuery indicators = Streams.concat(
            criteria.getEntryMethods()
                .stream()
                .map(LabReportFilter.EntryMethod::value),
            criteria.getEnteredBy()
                .stream()
                .map(LabReportFilter.UserType::value)
        )
        .filter(Objects::nonNull)
        .map(FieldValue::of)
        .collect(
            Collectors.collectingAndThen(
                Collectors.toList(),
                collected -> TermsQuery.of(
                    query -> query.field("electronic_ind")
                        .terms(terms -> terms.value(collected))
                )
            )
        );

    return Optional.of(indicators);
  }

  private Optional<QueryVariant> withStatus(final LabReportFilter criteria) {

    if (criteria.getProcessingStatus().isEmpty()) {
      return Optional.empty();
    }

    TermsQuery statuses = criteria.getProcessingStatus()
        .stream()
        .map(LabReportFilter.ProcessingStatus::name)
        .map(FieldValue::of)
        .collect(
            Collectors.collectingAndThen(
                Collectors.toList(),
                collected -> TermsQuery.of(
                    query -> query.field("record_status_cd")
                        .terms(terms -> terms.value(collected))
                )
            )
        );

    return Optional.of(statuses);


  }

  private Optional<QueryVariant> withOrderingFacility(final LabReportFilter criteria) {
    return criteria.orderingFacility().map(
        facility -> NestedQuery.of(
            nested -> nested.path("organization_participations")
                .scoreMode(ChildScoreMode.None)
                .query(
                    query -> query.bool(
                        bool -> bool.filter(
                            filter -> filter.term(
                                term -> term.field("organization_participations.type_cd")
                                    .value("ORD")
                            )
                        ).must(
                            must -> must.term(
                                term -> term.field("organization_participations.entity_id")
                                    .value(facility)
                            )
                        )
                    )
                )
        )
    );
  }

  private Optional<QueryVariant> withReportingFacility(final LabReportFilter criteria) {
    return criteria.reportingFacility().map(
        facility -> NestedQuery.of(
            nested -> nested.path("organization_participations")
                .scoreMode(ChildScoreMode.None)
                .query(
                    query -> query.bool(
                        bool -> bool.filter(
                            filter -> filter.term(
                                term -> term.field("organization_participations.type_cd")
                                    .value("AUT")
                            )
                        ).must(
                            must -> must.term(
                                term -> term.field("organization_participations.entity_id")
                                    .value(facility)
                            )
                        )
                    )
                )
        )
    );
  }

  private Optional<QueryVariant> withOrderingProvider(final LabReportFilter criteria) {
    return criteria.orderingProvider().map(
        facility -> NestedQuery.of(
            nested -> nested.path("person_participations")
                .scoreMode(ChildScoreMode.None)
                .query(
                    query -> query.bool(
                        bool -> bool.filter(
                            filter -> filter.term(
                                term -> term.field("person_participations.type_cd")
                                    .value("ORD")
                            )
                        ).filter(
                            filter -> filter.term(
                                term -> term.field("person_participations.subject_class_cd")
                                    .value("PSN")
                            )
                        ).must(
                            must -> must.term(
                                term -> term.field("person_participations.entity_id")
                                    .value(facility)
                            )
                        )
                    )
                )
        )
    );
  }

  private Optional<QueryVariant> withPatient(final LabReportFilter criteria) {

    Long patient = criteria.getPatientId();

    if (patient == null) {
      return Optional.empty();
    }

    return Optional.of(
        NestedQuery.of(
            nested -> nested.path("person_participations")
                .scoreMode(ChildScoreMode.None)
                .query(
                    query -> query.bool(
                        bool -> bool.filter(
                            filter -> filter.term(
                                term -> term.field("person_participations.type_cd")
                                    .value("PATSBJ")
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

  private Optional<QueryVariant> withEventStatus(final LabReportFilter criteria) {
    if (criteria.getEventStatus().isEmpty()) {
      return Optional.empty();
    }

    boolean includeNew = criteria.includeNew();
    boolean includeUpdated = criteria.includeUpdated();

    if (includeUpdated && includeNew) {
      return Optional.of(
          RangeQuery.of(
              range -> range.term(term -> term.field(VERSION).gte("1")
              )
          )
      );
    } else if (includeNew) {
      return Optional.of(
          TermQuery.of(
              term -> term.field(VERSION)
                  .value(1)
          )
      );
    } else {
      return Optional.of(
          RangeQuery.of(
              range -> range.term(term -> term.field(VERSION).gt("1")
              )
          )
      );
    }
  }
}
