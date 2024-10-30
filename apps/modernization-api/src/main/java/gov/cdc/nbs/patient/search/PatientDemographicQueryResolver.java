package gov.cdc.nbs.patient.search;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.codec.language.Soundex;
import org.springframework.stereotype.Component;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.PrefixQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.json.JsonData;
import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierResolver;
import gov.cdc.nbs.search.AdjustStrings;
import gov.cdc.nbs.search.WildCards;
import gov.cdc.nbs.search.criteria.date.DateCriteria;
import gov.cdc.nbs.search.criteria.date.DateCriteria.Between;
import gov.cdc.nbs.search.criteria.date.DateCriteria.Equals;
import gov.cdc.nbs.time.FlexibleInstantConverter;

@Component
class PatientDemographicQueryResolver {
  private static final String NAMES = "name";
  private static final String PHONES = "phone";
  private static final String EMAILS = "email";
  private static final String IDENTIFICATIONS = "entity_id";
  private static final String BIRTHDAY = "birth_time";
  private static final String ADDRESSES = "address";
  private static final String NAME_USE_CD = "name.nm_use_cd.keyword";
  private static final String LAST_NAME = "name.lastNm";
  private final PatientSearchSettings settings;
  private final PatientLocalIdentifierResolver resolver;
  private final Soundex soundex;

  PatientDemographicQueryResolver(
      final PatientSearchSettings settings,
      final PatientLocalIdentifierResolver resolver) {
    this.settings = settings;
    this.resolver = resolver;
    this.soundex = new Soundex();
  }

  Stream<Query> resolve(final PatientFilter criteria) {
    return Stream.of(
        applyPatientIdentifierCriteria(criteria),
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
        applyZipcodeCriteria(criteria)).flatMap(Optional::stream)
        .map(QueryVariant::_toQuery);
  }

  private Optional<QueryVariant> applyPatientIdentifierCriteria(final PatientFilter criteria) {
    if (criteria.getId() != null) {
      String shortOrLongIdStripped = criteria.getId().strip();

      if (shortOrLongIdStripped.isEmpty()) {
        return Optional.empty();
      }

      if (Character.isDigit(shortOrLongIdStripped.charAt(0))) {
        // This may be a short id, resolve the local id and then search for it
        try {
          long shortId = Long.parseLong(shortOrLongIdStripped);

          String localId = resolver.resolve(shortId);

          return applyLocalId(localId);

        } catch (NumberFormatException exception) {
          // skip these criteria. it's not a short id or long id
        }
      } else {
        return applyLocalId(shortOrLongIdStripped);
      }
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyLocalId(final String local) {
    return Optional.of(
        TermQuery.of(
            query -> query.field("local_id")
                .value(local)));
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
                                                  .field("name.firstNm")
                                                  .query(name)
                                                  .boost(settings.first().primary())

                                          )))))).should(
                                              should -> should.nested(
                                                  nested -> nested.path(NAMES)
                                                      .scoreMode(ChildScoreMode.Avg)
                                                      .query(
                                                          nonPrimary -> nonPrimary.simpleQueryString(
                                                              queryString -> queryString
                                                                  .fields("name.firstNm")
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
    if (criteria.getLastNameOperator() != null) {
      return applyLastNameWithOperatorCriteria(criteria);
    }

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

  private Optional<QueryVariant> applyLastNameWithOperatorCriteria(final PatientFilter criteria) {
    String name = AdjustStrings.withoutHyphens(criteria.getLastName());
    String operator = criteria.getLastNameOperator().toLowerCase();

    return switch (operator) {
      case "equal" -> Optional.of(
          BoolQuery.of(
              bool -> bool.must(
                  must -> must.nested(
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
                                                  .boost(settings.first().primary())))))))));

      case "starts_with" -> Optional.of(
          BoolQuery.of(
              bool -> bool.must(
                  must -> must.nested(
                      nested -> nested.path(NAMES)
                          .scoreMode(ChildScoreMode.Max)
                          .query(
                              query -> query.bool(
                                  legal -> legal.filter(
                                      filter -> filter.term(
                                          term -> term.field(NAME_USE_CD)
                                              .value("L")))
                                      .must(
                                          primary -> primary.simpleQueryString(
                                              nonPrimary -> nonPrimary
                                                  .fields(LAST_NAME)
                                                  .query(WildCards.startsWith(name))
                                                  .boost(settings.first().nonPrimary())))))))));
      case "contains" -> Optional.of(
          BoolQuery.of(
              bool -> bool.must(
                  must -> must.nested(
                      nested -> nested.path(NAMES)
                          .scoreMode(ChildScoreMode.Max)
                          .query(
                              query -> query.bool(
                                  legal -> legal.filter(
                                      filter -> filter.term(
                                          term -> term.field(NAME_USE_CD)
                                              .value("L")))
                                      .must(
                                          primary -> primary.wildcard(
                                              nonPrimary -> nonPrimary.field(LAST_NAME)
                                                  .value(WildCards.contains(name))
                                                  .boost(settings.first().nonPrimary())))))))));
      case "sounds_like" -> Optional.of(
          BoolQuery.of(
              bool -> bool.must(
                  must -> must.nested(
                      nested -> nested.path(NAMES)
                          .scoreMode(ChildScoreMode.Max)
                          .query(
                              query -> query.bool(
                                  legal -> legal.filter(
                                      filter -> filter.term(
                                          term -> term.field(NAME_USE_CD)
                                              .value("L")))
                                      .must(
                                          primary -> primary.term(
                                              term -> term
                                                  .field("name.lastNmSndx.keyword")
                                                  .value(soundex.encode(name.trim()))))))))));
      case "not_equal" -> Optional.of(
          BoolQuery.of(
              bool -> bool.must(
                  must -> must.nested(
                      nested -> nested.path(NAMES)
                          .scoreMode(ChildScoreMode.Max)
                          .query(
                              query -> query.bool(
                                  legal -> legal.filter(
                                      filter -> filter.term(
                                          term -> term.field(NAME_USE_CD)
                                              .value("L")))
                                      .mustNot(
                                          primary -> primary.match(
                                              match -> match
                                                  .field(LAST_NAME)
                                                  .query(name)
                                                  .boost(settings.first().primary())))))))));
      default -> Optional.empty();
    };
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
                                  must -> must.prefix(
                                      prefix -> prefix.field("entity_id.rootExtensionTxt").caseInsensitive(true)
                                          .value(value)))))));
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
