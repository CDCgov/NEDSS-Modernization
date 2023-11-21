package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.message.enums.Gender;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Component
class PatientSearchQueryFilterResolver {

  QueryBuilder resolve(final PatientFilter criteria) {
    return Stream.of(
            Optional.of(QueryBuilders.termQuery(ElasticsearchPerson.CD_FIELD, "PAT")),
            applyPatientStatusCriteria(criteria),
            applyGenderCriteria(criteria),
            applyDeceasedCriteria(criteria),
            applyRaceCriteria(criteria),
            applyEthnicityCriteria(criteria),
            applyIdentificationCriteria(criteria),
            applyStateCriteria(criteria),
            applyCountryCriteria(criteria)
        ).flatMap(Optional::stream)
        .reduce(
            QueryBuilders.boolQuery(),
            BoolQueryBuilder::filter,
            BoolQueryBuilder::filter
        );
  }

  private Optional<QueryBuilder> applyPatientStatusCriteria(final PatientFilter criteria) {
    List<String> statuses = criteria.adjustedStatus()
        .stream()
        .map(RecordStatus::name)
        .toList();
    return Optional.of(QueryBuilders.termsQuery(ElasticsearchPerson.RECORD_STATUS_CD, statuses));
  }

  private Optional<QueryBuilder> applyGenderCriteria(final PatientFilter filter) {
    Gender gender = Gender.resolve(filter.getGender());
    if (gender == null) {
      return Optional.empty();
    } else if (!Objects.equals(gender, Gender.U)) {
      return Optional.of(QueryBuilders.termQuery(ElasticsearchPerson.CURR_SEX_CD, gender));
    } else {
      return Optional.of(
          QueryBuilders.boolQuery()
              .should(QueryBuilders.termQuery(ElasticsearchPerson.CURR_SEX_CD, gender))
              .should(
                  QueryBuilders.boolQuery()
                      .mustNot(QueryBuilders.existsQuery(ElasticsearchPerson.CURR_SEX_CD))
              )
      );
    }
  }

  private Optional<QueryBuilder> applyDeceasedCriteria(final PatientFilter criteria) {
    if (criteria.getDeceased() != null) {
      return Optional.of(
          QueryBuilders.termQuery(
              ElasticsearchPerson.DECEASED_IND_CD,
              criteria.getDeceased()
          )
      );
    }

    return Optional.empty();
  }

  private Optional<QueryBuilder> applyRaceCriteria(final PatientFilter filter) {
    if (filter.getRace() != null) {
      return Optional.of(
          QueryBuilders.nestedQuery(
              ElasticsearchPerson.RACE_FIELD,
              QueryBuilders.termQuery("race.raceCategoryCd.keyword", filter.getRace()),
              ScoreMode.Avg
          )
      );
    }
    return Optional.empty();
  }

  private Optional<QueryBuilder> applyEthnicityCriteria(final PatientFilter filter) {
    if (filter.getEthnicity() != null) {
      return Optional.of(
          QueryBuilders.termQuery(
              "ethnic_group_ind",
              filter.getEthnicity()
          )
      );
    }

    return Optional.empty();
  }

  private Optional<QueryBuilder> applyIdentificationCriteria(final PatientFilter filter) {

    PatientFilter.Identification identification = filter.getIdentification();

    String type = identification.getIdentificationType();
    String value = identification.getIdentificationNumber();

    if (type != null && value != null) {

      return Optional.of(
          QueryBuilders.nestedQuery(
              ElasticsearchPerson.ENTITY_ID_FIELD,
              QueryBuilders.boolQuery()
                  .filter(QueryBuilders.termQuery("entity_id.typeCd.keyword", type))
              ,
              ScoreMode.Avg
          ));
    }

    return Optional.empty();

  }

  private Optional<QueryBuilder> applyStateCriteria(final PatientFilter criteria) {
    if (criteria.getState() != null && !criteria.getState().isEmpty()) {
      return Optional.of(
          QueryBuilders.nestedQuery(
              ElasticsearchPerson.ADDRESS_FIELD,
              QueryBuilders.termQuery("address.state.keyword", criteria.getState()),
              ScoreMode.Avg
          )
      );
    }

    return Optional.empty();
  }

  private Optional<QueryBuilder> applyCountryCriteria(final PatientFilter criteria) {
    if (criteria.getCountry() != null && !criteria.getCountry().isEmpty()) {
      return Optional.of(
          QueryBuilders.nestedQuery(
              ElasticsearchPerson.ADDRESS_FIELD,
              QueryBuilders.termQuery("address.cntryCd.keyword", criteria.getCountry()),
              ScoreMode.Avg
          )
      );
    }

    return Optional.empty();
  }
}
