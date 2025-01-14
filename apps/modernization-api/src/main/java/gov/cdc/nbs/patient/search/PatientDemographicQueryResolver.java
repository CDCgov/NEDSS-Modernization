package gov.cdc.nbs.patient.search;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.PrefixQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQueryField;
import co.elastic.clients.json.JsonData;
import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierResolver;
import gov.cdc.nbs.search.AdjustStrings;
import gov.cdc.nbs.search.WildCards;
import gov.cdc.nbs.search.criteria.date.DateCriteria;
import gov.cdc.nbs.search.criteria.date.DateCriteria.Between;
import gov.cdc.nbs.search.criteria.date.DateCriteria.Equals;
import gov.cdc.nbs.search.criteria.text.TextCriteria;
import gov.cdc.nbs.time.FlexibleInstantConverter;
import org.apache.commons.codec.language.Soundex;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import static gov.cdc.nbs.search.criteria.text.TextCriteriaNestedQueryResolver.*;

@Component
@SuppressWarnings("squid:S3516")
class PatientDemographicQueryResolver {
  private static final String NAMES = "name";
  private static final String PHONES = "phone";
  private static final String EMAILS = "email";
  private static final String IDENTIFICATIONS = "entity_id";
  private static final String BIRTHDAY = "birth_time";
  private static final String ADDRESSES = "address";
  private static final String NAME_USE_CD = "name.nm_use_cd.keyword";
  private static final String LAST_NAME = "name.lastNm";
  private static final String LOCAL_ID = "local_id";
  private static final String FIRST_NAME = "name.firstNm";
  private final PatientSearchSettings settings;
  private final PatientLocalIdentifierResolver resolver;
  private final PatientNameDemographicQueryResolver nameQueryResolver;
  private final PatientLocationQueryResolver locationQueryResolver;
  private final Soundex soundex;


  PatientDemographicQueryResolver(
      final PatientSearchSettings settings,
      final PatientLocalIdentifierResolver resolver,
      final PatientNameDemographicQueryResolver nameQueryResolver,
      final PatientLocationQueryResolver locationQueryResolver) {
    this.settings = settings;
    this.resolver = resolver;
    this.nameQueryResolver = nameQueryResolver;
    this.locationQueryResolver = locationQueryResolver;
    this.soundex = new Soundex();
  }

  Stream<Query> resolve(final PatientFilter criteria) {
    return Stream.concat(Stream.concat(resolveDemographicCriteria(criteria),
        nameQueryResolver.resolve(criteria)), locationQueryResolver.resolve(criteria))
        .map(QueryVariant::_toQuery);
  }

  private Stream<QueryVariant> resolveDemographicCriteria(final PatientFilter criteria) {
    return Stream.of(
        applyPatientIdentifierCriteria(criteria),
        applyPatientIdFilterCriteria(criteria),
        applyPatientNameFilterCriteria(criteria),
        applyFirstNameCriteria(criteria),
        applyLastNameCriteria(criteria),
        applyPhoneNumberCriteria(criteria),
        applyEmailCriteria(criteria),
        applyIdentificationCriteria(criteria),
        applyDateOfBirthCriteria(criteria),
        applyStreetAddressCriteria(criteria),
        applyCityCriteria(criteria),
        applyDateOfBirthHighRangeCriteria(criteria),
        applyDateOfBirthLowRangeCriteria(criteria),
        applyZipcodeCriteria(criteria))
        .flatMap(Optional::stream);
  }

  private Optional<QueryVariant> applyPatientIdFilterCriteria(final PatientFilter criteria) {
    if (criteria.getFilter().id() == null) {
      return Optional.empty();
    }

    String adjusted = WildCards.contains(criteria.getFilter().id());
    return Optional.of(BoolQuery.of(
        bool -> bool.must(
            query -> query.queryString(
                simple -> simple.fields(LOCAL_ID)
                    .query(adjusted)))));

  }

  private Optional<QueryVariant> applyPatientNameFilterCriteria(final PatientFilter criteria) {
    if (criteria.getFilter().name() == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(new TextCriteria(null, null, null, criteria.getFilter().name(), null))
        .flatMap(TextCriteria::maybeContains)
        .map(value -> containsInOneOfTwoFields(NAMES, FIRST_NAME, LAST_NAME, value));
  }

  private Optional<QueryVariant> applyPatientIdentifierCriteria(final PatientFilter criteria) {
    if (criteria.getId() != null) {
      String shortOrLongIdStripped = criteria.getId().strip();

      if (shortOrLongIdStripped.isEmpty()) {
        return Optional.empty();
      }

      List<String> shortOrLongIdStrippedIds = Arrays.asList(shortOrLongIdStripped.split("[\\s,;]+"));
      if (shortOrLongIdStrippedIds.isEmpty()) {
        return Optional.empty();
      }

      List<String> localIds = new ArrayList<>();
      shortOrLongIdStrippedIds.forEach(shortOrLongIdStrippedId -> {
        if (Character.isDigit(shortOrLongIdStrippedId.charAt(0))) {
          // This may be a short id, resolve the local id and then search for it
          try {
            long shortId = Long.parseLong(shortOrLongIdStrippedId);
            String localId = resolver.resolve(shortId);
            localIds.add(localId);
          } catch (NumberFormatException exception) {
            // skip these criteria. it's not a short id or long id
          }
        } else {
          localIds.add(shortOrLongIdStrippedId);
        }
      });

      return applyLocalIds(localIds);
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyLocalIds(final List<String> localIds) {
    TermsQueryField localIdTerms = new TermsQueryField.Builder()
        .value(localIds.stream().map(FieldValue::of).toList())
        .build();

    return Optional.of(
        TermsQuery.of(
            query -> query.field(LOCAL_ID).terms(localIdTerms)));
  }

  private Optional<QueryVariant> applyFirstNameCriteria(final PatientFilter criteria) {
    String name = criteria.getFirstName();

    if (name != null && !name.isBlank()) {

      String encoded;
      if (criteria.isDisableSoundex()) {
        encoded = "";
      } else {
        encoded = soundex.encode(name.trim());
      }

      return Optional.of(
          BoolQuery.of(
              bool -> bool.should(
                  should -> should.nested(
                      nested -> nested.path(NAMES)
                          .scoreMode(ChildScoreMode.Max)
                          .query(
                              query -> query.bool(
                                  legal -> legal.filter(
                                      filter -> filter.term(
                                          term -> term.field(NAME_USE_CD)
                                              .value("L")))
                                      .must(
                                          primary -> primary.match(
                                              match -> match
                                                  .field(FIRST_NAME)
                                                  .query(name)
                                                  .boost(settings.first().primary())

                                          ))))))
                  .should(
                      should -> should.nested(
                          nested -> nested.path(NAMES)
                              .scoreMode(ChildScoreMode.Avg)
                              .query(
                                  nonPrimary -> nonPrimary.simpleQueryString(
                                      queryString -> queryString
                                          .fields(FIRST_NAME)
                                          .query(WildCards.startsWith(name))
                                          .boost(settings.first().nonPrimary())))))
                  .should(
                      should -> should.nested(
                          nested -> nested.path(NAMES)
                              .scoreMode(ChildScoreMode.Avg)
                              .boost(settings.first().soundex())
                              .query(
                                  query -> query.term(
                                      term -> term
                                          .field("name.firstNmSndx.keyword")
                                          .value(encoded)))))));
    }
    return Optional.empty();
  }

  private Optional<QueryVariant> applyLastNameCriteria(final PatientFilter criteria) {
    String name = AdjustStrings.withoutHyphens(criteria.getLastName());

    if (name != null && !name.isBlank()) {
      String encoded;
      if (criteria.isDisableSoundex()) {
        encoded = "";
      } else {
        encoded = soundex.encode(name.trim());
      }

      return Optional.of(
          BoolQuery.of(
              bool -> bool.should(
                  should -> should.nested(
                      nested -> nested.path(NAMES)
                          .scoreMode(ChildScoreMode.Max)
                          .query(
                              query -> query.bool(
                                  legal -> legal.filter(
                                      filter -> filter.term(
                                          term -> term.field(NAME_USE_CD)
                                              .value("L")))
                                      .must(
                                          primary -> primary.match(
                                              match -> match
                                                  .field(LAST_NAME)
                                                  .query(name)
                                                  .boost(settings.first().primary())))))))
                  .should(
                      should -> should.nested(
                          nested -> nested.path(NAMES)
                              .scoreMode(ChildScoreMode.Avg)
                              .query(
                                  query -> query.simpleQueryString(
                                      nonPrimary -> nonPrimary
                                          .fields(LAST_NAME)
                                          .query(WildCards.startsWith(name))
                                          .boost(settings.first().nonPrimary())))))
                  .should(
                      should -> should.nested(
                          nested -> nested.path(NAMES)
                              .scoreMode(ChildScoreMode.Avg)
                              .boost(settings.first().soundex())
                              .query(
                                  query -> query.term(
                                      term -> term
                                          .field("name.lastNmSndx.keyword")
                                          .value(encoded)))))));
    }
    return Optional.empty();
  }

  private Optional<QueryVariant> applyPhoneNumberCriteria(final PatientFilter criteria) {

    String number = AdjustStrings.withoutSpecialCharacters(criteria.getPhoneNumber());

    if (number != null && !number.isEmpty()) {

      return Optional.of(
          NestedQuery.of(
              nested -> nested.path(PHONES)
                  .scoreMode(ChildScoreMode.Avg)
                  .query(
                      query -> query.wildcard(
                          wildcard -> wildcard.field("phone.telephoneNbr")
                              .value(WildCards.contains(number))))));

    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyEmailCriteria(final PatientFilter criteria) {

    String email = criteria.getEmail();
    if (email != null && !email.isEmpty()) {

      return Optional.of(
          NestedQuery.of(
              nested -> nested.path(EMAILS)
                  .scoreMode(ChildScoreMode.Avg)
                  .query(
                      query -> query.simpleQueryString(
                          queryString -> queryString.fields("email.emailAddress")
                              .defaultOperator(Operator.And)
                              .query(email)))));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyIdentificationCriteria(final PatientFilter criteria) {

    PatientFilter.Identification identification = criteria.getIdentification();

    String type = identification.getIdentificationType();
    String value = AdjustStrings.withoutSpecialCharacters(identification.getIdentificationNumber());

    if (type != null && (value != null && !value.isEmpty())) {
      return Optional.of(
          NestedQuery.of(
              nested -> nested.path(IDENTIFICATIONS)
                  .scoreMode(ChildScoreMode.Avg)
                  .query(
                      query -> query.bool(
                          bool -> bool.filter(
                              filter -> filter.term(
                                  term -> term.field("entity_id.typeCd.keyword")
                                      .value(type)))
                              .must(
                                  must -> must.wildcard(
                                      match -> match.field("entity_id.rootExtensionTxt").caseInsensitive(true)
                                          .value(WildCards.contains(value))))))));
    }
    return Optional.empty();
  }

  private Optional<QueryVariant> applyDateOfBirthCriteria(final PatientFilter criteria) {

    LocalDate dateOfBirth = criteria.getDateOfBirth();

    if (dateOfBirth != null) {
      String operator = resolveDateOperator(criteria.getDateOfBirthOperator());
      String value = FlexibleInstantConverter.toString(dateOfBirth);

      return switch (operator) {
        case "before" -> Optional.of(
            RangeQuery.of(
                range -> range.field(BIRTHDAY)
                    .lt(JsonData.of(value))));
        case "after" -> Optional.of(
            RangeQuery.of(
                range -> range.field(BIRTHDAY)
                    .gt(JsonData.of(value))));
        default -> Optional.of(
            MatchQuery.of(
                match -> match.field(BIRTHDAY)
                    .query(value)));
      };
    }

    DateCriteria dateCriteria = criteria.getBornOn();
    if (dateCriteria != null && dateCriteria.equals() != null && dateCriteria.equals().isCompleteDate()) {
      Equals equals = dateCriteria.equals();
      String value = FlexibleInstantConverter.toString(LocalDate.of(equals.year(), equals.month(), equals.day()));
      return Optional.of(
          MatchQuery.of(
              match -> match.field(BIRTHDAY)
                  .query(value)));

    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyDateOfBirthLowRangeCriteria(final PatientFilter criteria) {
    DateCriteria dateCriteria = criteria.getBornOn();
    if (dateCriteria == null) {
      return Optional.empty();
    }
    Between betweenDate = dateCriteria.between();
    if (betweenDate == null || betweenDate.from() == null) {
      return Optional.empty();
    }


    String value = FlexibleInstantConverter.toString(betweenDate.from());

    return Optional.of(
        RangeQuery.of(
            range -> range.field(BIRTHDAY)
                .gte(JsonData.of(value))));
  }

  private Optional<QueryVariant> applyDateOfBirthHighRangeCriteria(final PatientFilter criteria) {
    DateCriteria dateCriteria = criteria.getBornOn();
    if (dateCriteria == null) {
      return Optional.empty();
    }
    Between betweenDate = dateCriteria.between();
    if (betweenDate == null || betweenDate.to() == null) {
      return Optional.empty();
    }

    String value = FlexibleInstantConverter.toString(betweenDate.to());

    return Optional.of(
        RangeQuery.of(
            range -> range.field(BIRTHDAY)
                .lte(JsonData.of(value))));
  }


  private String resolveDateOperator(final String operator) {
    return operator == null ? "equal" : operator.toLowerCase();
  }

  private Optional<QueryVariant> applyStreetAddressCriteria(final PatientFilter criteria) {

    String address = criteria.getAddress();
    if (address != null && !address.isEmpty()) {

      String result = address.replace("(", "").replace(")", "");

      return Optional.of(
          NestedQuery.of(
              nested -> nested.path(ADDRESSES)
                  .scoreMode(ChildScoreMode.Avg)
                  .query(
                      query -> query.simpleQueryString(
                          queryString -> queryString.fields("address.streetAddr1")
                              .defaultOperator(Operator.And)
                              .query(WildCards.startsWith(result))))));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyCityCriteria(final PatientFilter criteria) {

    String city = criteria.getCity();
    if (city != null && !city.isEmpty()) {

      return Optional.of(
          NestedQuery.of(
              nested -> nested.path(ADDRESSES)
                  .scoreMode(ChildScoreMode.Avg)
                  .query(
                      query -> query.simpleQueryString(
                          queryString -> queryString.fields("address.city")
                              .defaultOperator(Operator.And)
                              .query(WildCards.startsWith(city))))));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyZipcodeCriteria(final PatientFilter criteria) {

    String zipcode = criteria.getZip();
    if (zipcode != null && !zipcode.isEmpty()) {

      QueryVariant q = zipcode.length() < 5
          ? PrefixQuery.of(
              prefix -> prefix.field("address.zip")
                  .value(zipcode))
          : MatchQuery.of(
              match -> match.field("address.zip")
                  .query(zipcode));

      return Optional.of(
          NestedQuery.of(
              nested -> nested.path(ADDRESSES)
                  .scoreMode(ChildScoreMode.Avg)
                  .query(q._toQuery())));
    }

    return Optional.empty();
  }
}
