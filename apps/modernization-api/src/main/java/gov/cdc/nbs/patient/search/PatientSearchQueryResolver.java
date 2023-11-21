package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierResolver;
import gov.cdc.nbs.time.FlexibleInstantConverter;
import org.apache.commons.codec.language.Soundex;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
class PatientSearchQueryResolver {

  private final PatientSearchSettings settings;
  private final PatientLocalIdentifierResolver resolver;

  private final Soundex soundex;

  PatientSearchQueryResolver(
      final PatientSearchSettings settings,
      final PatientLocalIdentifierResolver resolver
  ) {
    this.settings = settings;
    this.resolver = resolver;
    this.soundex = new Soundex();
  }

  QueryBuilder resolve(final PatientFilter criteria) {

    return Stream.of(
            applyPatientIdentifierCriteria(criteria),
            applyFirstNameCriteria(criteria),
            applyLastNameCriteria(criteria),
            applyDateOfBirthCriteria(criteria),
            applyPhoneNumberCriteria(criteria),
            applyEmailCriteria(criteria),
            applyStreetAddressCriteria(criteria),
            applyCityCriteria(criteria),
            applyZipcode(criteria),
            applyIdentificationCriteria(criteria)
        ).flatMap(Optional::stream)
        .reduce(
            QueryBuilders.boolQuery(),
            BoolQueryBuilder::must,
            BoolQueryBuilder::must
        );
  }

  private Optional<QueryBuilder> applyPatientIdentifierCriteria(final PatientFilter filter) {

    if (filter.getId() != null) {
      String shortOrLongIdStripped = filter.getId().strip();
      if (Character.isDigit(shortOrLongIdStripped.charAt(0))) {
        try {
          long shortId = Long.parseLong(filter.getId());

          String localId = resolver.resolve(shortId);
          return Optional.of(QueryBuilders.matchQuery(ElasticsearchPerson.LOCAL_ID, localId));
        } catch (NumberFormatException exception) {
          // skip these criteria. it's not a short id or long id
        }
      } else {
        return Optional.of(QueryBuilders.matchQuery(ElasticsearchPerson.LOCAL_ID, filter.getId()));
      }
    }

    return Optional.empty();
  }

  private Optional<QueryBuilder> applyFirstNameCriteria(final PatientFilter filter) {
    if (filter.getFirstName() != null && !filter.getFirstName().isBlank()) {

      String value = addWildcards(filter.getFirstName());
      String encoded = soundex.encode(filter.getFirstName().trim());

      BoolQueryBuilder firstNameBuilder = QueryBuilders.boolQuery()
          .should(
              QueryBuilders.queryStringQuery(value)
                  .defaultField(ElasticsearchPerson.FIRST_NM)
                  .defaultOperator(Operator.AND)
                  .boost(settings.first().primary())
          ).should(
              QueryBuilders.nestedQuery(
                  ElasticsearchPerson.NAME_FIELD,
                  QueryBuilders.queryStringQuery(value)
                      .defaultField("name.firstNm")
                      .defaultOperator(Operator.AND),
                  ScoreMode.Avg
              ).boost(settings.first().nonPrimary())
          ).should(
              QueryBuilders.nestedQuery(
                  ElasticsearchPerson.NAME_FIELD,
                  QueryBuilders.queryStringQuery(encoded)
                      .defaultField("name.firstNmSndx"),
                  ScoreMode.Avg
              ).boost(settings.first().soundex())
          );

      return Optional.of(firstNameBuilder);
    }
    return Optional.empty();
  }

  private Optional<QueryBuilder> applyLastNameCriteria(final PatientFilter filter) {
    if (filter.getLastName() != null && !filter.getLastName().isBlank()) {

      String value = addWildcards(filter.getLastName());
      String encoded = soundex.encode(filter.getLastName().trim());

      BoolQueryBuilder lastNameBuilder = QueryBuilders.boolQuery()
          .should(
              QueryBuilders.queryStringQuery(value)
                  .defaultField(ElasticsearchPerson.LAST_NM)
                  .defaultOperator(Operator.AND)
                  .boost(settings.last().primary())
          )
          .should(
              QueryBuilders.nestedQuery(ElasticsearchPerson.NAME_FIELD,
                  QueryBuilders.queryStringQuery(value)
                      .defaultField("name.lastNm")
                      .defaultOperator(Operator.AND),
                  ScoreMode.Avg
              ).boost(settings.last().nonPrimary())
          ).should(
              QueryBuilders.nestedQuery(
                  ElasticsearchPerson.NAME_FIELD,
                  QueryBuilders.queryStringQuery(encoded).defaultField("name.lastNmSndx"),
                  ScoreMode.Avg
              ).boost(settings.last().soundex())
          );

      return Optional.of(lastNameBuilder);
    }

    return Optional.empty();
  }

  private Optional<QueryBuilder> applyPhoneNumberCriteria(final PatientFilter filter) {
    if (filter.getPhoneNumber() != null) {
      String value = filter.getPhoneNumber().replaceAll("\\W", "");
      if (!value.isEmpty()) {
        return Optional.of(
            QueryBuilders.nestedQuery(ElasticsearchPerson.PHONE_FIELD,
                QueryBuilders.queryStringQuery(addWildcards(value))
                    .defaultField("phone.telephoneNbr")
                    .defaultOperator(Operator.AND),
                ScoreMode.Avg
            )
        );
      }
    }

    return Optional.empty();
  }

  private Optional<QueryBuilder> applyEmailCriteria(final PatientFilter filter) {
    if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
      return Optional.of(
          QueryBuilders.nestedQuery(ElasticsearchPerson.EMAIL_FIELD,
              QueryBuilders.queryStringQuery(filter.getEmail())
                  .defaultField("email.emailAddress")
                  .defaultOperator(Operator.AND),
              ScoreMode.Avg
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

      String adjusted = value.replaceAll("\\W", "");

      return Optional.of(
          QueryBuilders.nestedQuery(
              ElasticsearchPerson.ENTITY_ID_FIELD,
              QueryBuilders.boolQuery()
                  .filter(QueryBuilders.termQuery("entity_id.typeCd.keyword", type))
                  .must(
                      QueryBuilders.queryStringQuery(addWildcards(adjusted))
                          .defaultField("entity_id.rootExtensionTxt")
                  )
              ,
              ScoreMode.Avg
          ));
    }

    return Optional.empty();

  }

  private Optional<QueryBuilder> applyDateOfBirthCriteria(final PatientFilter filter) {
    if (filter.getDateOfBirth() != null) {
      String operator = filter.getDateOfBirthOperator();
      String value = FlexibleInstantConverter.toString(filter.getDateOfBirth());


      if (operator == null || operator.equalsIgnoreCase("equal")) {
        return Optional.of(QueryBuilders.matchQuery(ElasticsearchPerson.BIRTH_TIME, value));
      } else if (operator.equalsIgnoreCase("before")) {
        return Optional.of(QueryBuilders.rangeQuery(ElasticsearchPerson.BIRTH_TIME).lt(value));
      } else if (operator.equalsIgnoreCase("after")) {
        return Optional.of(QueryBuilders.rangeQuery(ElasticsearchPerson.BIRTH_TIME).gt(value));
      }
    }

    return Optional.empty();
  }

  private Optional<QueryBuilder> applyStreetAddressCriteria(final PatientFilter filter) {
    if (filter.getAddress() != null && !filter.getAddress().isEmpty()) {
      return Optional.of(
          QueryBuilders.nestedQuery(
              ElasticsearchPerson.ADDRESS_FIELD,
              QueryBuilders.queryStringQuery(addWildcards(filter.getAddress()))
                  .defaultField("address.streetAddr1")
                  .defaultOperator(Operator.AND),
              ScoreMode.Avg
          )
      );
    }

    return Optional.empty();
  }

  private Optional<QueryBuilder> applyCityCriteria(final PatientFilter filter) {
    if (filter.getCity() != null && !filter.getCity().isEmpty()) {
      return Optional.of(
          QueryBuilders.nestedQuery(
              ElasticsearchPerson.ADDRESS_FIELD,
              QueryBuilders.queryStringQuery(addWildcards(filter.getCity()))
                  .defaultField("address.city")
                  .defaultOperator(Operator.AND),
              ScoreMode.Avg
          )
      );
    }

    return Optional.empty();
  }

  private Optional<QueryBuilder> applyZipcode(final PatientFilter filter) {
    if (filter.getZip() != null && !filter.getZip().isEmpty()) {

      String value = filter.getZip();

      String adjusted = value.length() < 5 ? addWildcards(value) : value;

      return Optional.of(
          QueryBuilders.nestedQuery(
              ElasticsearchPerson.ADDRESS_FIELD,
              QueryBuilders.queryStringQuery(adjusted)
                  .defaultField("address.zip")
                  .defaultOperator(Operator.AND),
              ScoreMode.Avg
          )
      );
    }
    return Optional.empty();
  }

  private String addWildcards(final String searchString) {
    // wildcard does not default to case-insensitive searching
    return searchString.toLowerCase().trim() + "*";
  }

}
