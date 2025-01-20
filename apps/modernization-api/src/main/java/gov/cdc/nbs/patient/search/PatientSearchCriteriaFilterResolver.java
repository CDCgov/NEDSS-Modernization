package gov.cdc.nbs.patient.search;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ScriptQuery;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.search.criteria.date.DateCriteria;
import gov.cdc.nbs.search.criteria.date.DateCriteria.Equals;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
class PatientSearchCriteriaFilterResolver {

  private static final String CURRENT_GENDER = "curr_sex_cd";
  private static final String STATUS = "record_status_cd";
  private static final String DECEASED = "deceased_ind_cd";
  private static final String PERSON_TYPE = "cd";
  private static final String PERSON_TYPE_PATIENT = "PAT";
  private static final String PAINLESS = "painless";
  private static final String NOT_FOUND_GENDER_CODE = "x";

  Query resolve(final PatientFilter criteria) {
    return Stream.of(
        onlyPatients(),
        applyStatusCriteria(criteria),
        applyGenderCriteria(criteria),
        applyDeceasedCriteria(criteria),
        applyRaceCriteria(criteria),
        applyEthnicityCriteria(criteria),
        applyIdentificationCriteria(criteria),
        applyStateCriteria(criteria),
        applyDateOfBirthDayCriteria(criteria),
        applyDateOfBirthMonthCriteria(criteria),
        applyDateOfBirthYearCriteria(criteria),
        applyCountryCriteria(criteria)).flatMap(Optional::stream)
        .map(QueryVariant::_toQuery)
        .reduce(
            new BoolQuery.Builder(),
            BoolQuery.Builder::filter,
            (one, two) -> one.filter(two.build().filter()))
        .build()._toQuery();
  }

  private Optional<QueryVariant> onlyPatients() {
    return Optional.of(
        TermQuery.of(
            query -> query.field(PERSON_TYPE)
                .value(PERSON_TYPE_PATIENT)));
  }

  private Optional<QueryVariant> applyStatusCriteria(final PatientFilter criteria) {
    TermsQuery statuses = criteria.adjustedStatus()
        .stream()
        .map(RecordStatus::name)
        .map(FieldValue::of)
        .collect(
            Collectors.collectingAndThen(
                Collectors.toList(),
                collected -> TermsQuery.of(
                    query -> query.field(STATUS)
                        .terms(terms -> terms.value(collected)))));

    return Optional.of(statuses);
  }

  private String getImpliedGenderFromSexFilter(String sexFilter) {
    if (sexFilter == null)
      return null;
    String lowerSexFilter = sexFilter.toLowerCase();
    if (lowerSexFilter.equals(Gender.F.display().toLowerCase())
        || lowerSexFilter.equals(Gender.F.value().toLowerCase())) {
      return Gender.F.value();
    }
    if (lowerSexFilter.equals(Gender.M.display().toLowerCase())
        || lowerSexFilter.equals(Gender.M.value().toLowerCase())) {
      return Gender.M.value();
    }
    if (lowerSexFilter.equals(Gender.U.display().toLowerCase())
        || lowerSexFilter.equals(Gender.U.value().toLowerCase())) {
      return Gender.U.value();
    }
    return NOT_FOUND_GENDER_CODE;
  }

  private Optional<QueryVariant> applyGenderCriteria(final PatientFilter criteria) {

    Gender gender = Gender.resolve(criteria.getGender());
    String genderValue = gender == null ? null : gender.value();
    String sexFilter = criteria.getFilter().sex();
    if (sexFilter != null) {
      String impliedSex = getImpliedGenderFromSexFilter(sexFilter);
      if (impliedSex != null) {
        if (genderValue == null) {
          genderValue = impliedSex;
        } else if (!genderValue.equals(impliedSex)) {
          genderValue = NOT_FOUND_GENDER_CODE;
        }
      }
    }
    final String termValue = genderValue;
    return (termValue == null) ? Optional.empty()
        : Optional.of(
            TermQuery.of(
                query -> query.field(CURRENT_GENDER).value(termValue)));
  }

  private Optional<QueryVariant> applyDeceasedCriteria(final PatientFilter criteria) {
    if (criteria.getDeceased() != null) {
      return Optional.of(
          TermQuery.of(
              query -> query.field(DECEASED).value(criteria.getDeceased().value())));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyRaceCriteria(final PatientFilter criteria) {
    if (criteria.getRace() != null) {
      return Optional.of(
          NestedQuery.of(
              nested -> nested.path("race")
                  .scoreMode(ChildScoreMode.Avg)
                  .query(
                      query -> query.term(
                          term -> term.field("race.raceCategoryCd.keyword")
                              .value(criteria.getRace())))));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyEthnicityCriteria(final PatientFilter criteria) {
    if (criteria.getEthnicity() != null) {
      return Optional.of(
          TermQuery.of(
              term -> term.field("ethnic_group_ind")
                  .value(criteria.getEthnicity())));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyDateOfBirthDayCriteria(final PatientFilter criteria) {
    DateCriteria dateCriteria = criteria.getBornOn();
    if (dateCriteria == null) {
      return Optional.empty();
    }
    Equals equalsDate = dateCriteria.equals();
    if (equalsDate == null || !equalsDate.isPartialDate() || equalsDate.day() == null) {
      return Optional.empty();
    }

    return Optional.of(ScriptQuery.of(q -> q.script(
        Script.of(s -> s
            .inline(inline -> inline
                .source(
                    "doc['birth_time'].size()!=0 && doc['birth_time'].value.getDayOfMonth() == " + equalsDate.day())
                .lang(PAINLESS))))));
  }

  private Optional<QueryVariant> applyDateOfBirthMonthCriteria(final PatientFilter criteria) {
    DateCriteria dateCriteria = criteria.getBornOn();
    if (dateCriteria == null) {
      return Optional.empty();
    }
    Equals equalsDate = dateCriteria.equals();
    if (equalsDate == null || !equalsDate.isPartialDate() || equalsDate.month() == null) {
      return Optional.empty();
    }

    return Optional.of(ScriptQuery.of(q -> q.script(
        Script.of(s -> s
            .inline(inline -> inline
                .source(
                    "doc['birth_time'].size()!=0 && doc['birth_time'].value.getMonthValue() == " + equalsDate.month())
                .lang(PAINLESS))))));
  }

  private Optional<QueryVariant> applyDateOfBirthYearCriteria(final PatientFilter criteria) {
    DateCriteria dateCriteria = criteria.getBornOn();
    if (dateCriteria == null) {
      return Optional.empty();
    }
    Equals equalsDate = dateCriteria.equals();
    if (equalsDate == null || !equalsDate.isPartialDate() || equalsDate.year() == null) {
      return Optional.empty();
    }

    return Optional.of(ScriptQuery.of(q -> q.script(
        Script.of(s -> s
            .inline(inline -> inline
                .source("doc['birth_time'].size()!=0 && doc['birth_time'].value.getYear() == " + equalsDate.year())
                .lang(PAINLESS))))));
  }

  private Optional<QueryVariant> applyIdentificationCriteria(final PatientFilter criteria) {

    PatientFilter.Identification identification = criteria.getIdentification();

    String type = identification.getIdentificationType();
    String value = identification.getIdentificationNumber();

    if (type != null && value != null) {
      return Optional.of(
          NestedQuery.of(
              nested -> nested.path("entity_id")
                  .scoreMode(ChildScoreMode.Avg)
                  .query(
                      query -> query.bool(
                          bool -> bool.filter(
                              filter -> filter.term(
                                  term -> term.field("entity_id.typeCd.keyword")
                                      .value(type)))))));
    }
    return Optional.empty();
  }

  private Optional<QueryVariant> applyStateCriteria(final PatientFilter criteria) {
    if (criteria.getState() != null && !criteria.getState().isEmpty()) {
      return Optional.of(
          NestedQuery.of(
              nested -> nested.path("address")
                  .scoreMode(ChildScoreMode.Avg)
                  .query(
                      query -> query.term(
                          term -> term.field("address.state.keyword")
                              .value(criteria.getState())))));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyCountryCriteria(final PatientFilter criteria) {
    if (criteria.getCountry() != null && !criteria.getCountry().isEmpty()) {
      return Optional.of(
          NestedQuery.of(
              nested -> nested.path("address")
                  .scoreMode(ChildScoreMode.Avg)
                  .query(
                      query -> query.term(
                          term -> term.field("address.cntryCd.keyword")
                              .value(criteria.getCountry())))));
    }

    return Optional.empty();
  }
}
