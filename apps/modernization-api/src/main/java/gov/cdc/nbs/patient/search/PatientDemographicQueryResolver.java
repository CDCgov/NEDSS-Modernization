package gov.cdc.nbs.patient.search;

import static gov.cdc.nbs.search.criteria.text.TextCriteriaNestedQueryResolver.contains;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierResolver;
import gov.cdc.nbs.search.AdjustStrings;
import gov.cdc.nbs.search.WildCards;
import gov.cdc.nbs.search.criteria.date.DateCriteria;
import gov.cdc.nbs.search.criteria.date.DateCriteria.Between;
import gov.cdc.nbs.search.criteria.date.DateCriteria.Equals;
import gov.cdc.nbs.search.criteria.text.TextCriteria;
import gov.cdc.nbs.time.FlexibleInstantConverter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.commons.codec.language.Soundex;
import org.springframework.stereotype.Component;

@Component
class PatientDemographicQueryResolver {
  private static final String BIRTHDAY = "birth_time";
  private static final String LOCAL_ID = "local_id";
  private static final String SHORT_ID = "short_id";
  private static final String NAMES = "name";
  private static final String NAME_USE_FIELD = "name.nm_use_cd.keyword";
  private static final String LAST_NAME_FIELD = "name.lastNm";
  private static final String LAST_NAME_SOUNDEX_FIELD = "name.lastNmSndx.keyword";
  private static final String FIRST_NAME_FIELD = "name.firstNm";
  private static final String FIRST_NAME_SOUNDEX_FIELD = "name.firstNmSndx.keyword";
  private static final String FULL_NAME = "name.full";
  private static final String PHONES = "phone";
  private static final String PHONE_NUMBER_FIELD = "phone.telephoneNbr";
  private static final String EMAILS = "email";
  private static final String EMAIL_ADDRESS = "email.emailAddress";
  private static final String ADDRESSES = "address";
  private static final String FULL_ADDRESS_FIELD = "address.full";
  private static final String STREET = "address.streetAddr1";
  private static final String CITY = "address.city";
  private static final String ZIP_CODE = "address.zip";
  private static final String IDENTIFICATIONS = "entity_id";
  private static final String IDENTIFICATION = "entity_id.rootExtensionTxt";

  private static final String PAINLESS = "painless";
  private static final String LEGAL_NAME_CODE = "L";

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

  Stream<Query> resolve(final PatientSearchCriteria criteria) {
    return Stream.concat(
            Stream.concat(
                resolveDemographicCriteria(criteria), nameQueryResolver.resolve(criteria)),
            locationQueryResolver.resolve(criteria))
        .map(QueryVariant::_toQuery);
  }

  private Stream<QueryVariant> resolveDemographicCriteria(final PatientSearchCriteria criteria) {
    return Stream.of(
            applyPatientIdentifierCriteria(criteria),
            applyPatientIdFilter(criteria),
            applyPatientNameFilter(criteria),
            applyFirstNameCriteria(criteria),
            applyLastNameCriteria(criteria),
            applyPhoneFilter(criteria),
            applyPhoneNumberCriteria(criteria),
            applyEmailCriteria(criteria),
            applyEmailFilter(criteria),
            applyIdentificationCriteria(criteria),
            applyIdentificationFilter(criteria),
            applyDateOfBirthCriteria(criteria),
            applyPatientAgeOrDateOfBirthFilter(criteria),
            applyStreetAddressCriteria(criteria),
            applyAddressFilter(criteria),
            applyCityCriteria(criteria),
            applyDateOfBirthHighRangeCriteria(criteria),
            applyDateOfBirthLowRangeCriteria(criteria),
            applyZipcodeCriteria(criteria))
        .flatMap(Optional::stream);
  }

  private Optional<QueryVariant> applyPatientIdentifierCriteria(
      final PatientSearchCriteria criteria) {
    if (criteria.getId() != null) {
      String shortOrLongIdStripped = criteria.getId().strip();

      if (shortOrLongIdStripped.isEmpty()) {
        return Optional.empty();
      }

      List<String> shortOrLongIdStrippedIds =
          Arrays.asList(shortOrLongIdStripped.split("[\\s,;]+"));
      if (shortOrLongIdStrippedIds.isEmpty()) {
        return Optional.empty();
      }

      List<String> localIds = new ArrayList<>();
      shortOrLongIdStrippedIds.forEach(
          shortOrLongIdStrippedId -> {
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
    TermsQueryField localIdTerms =
        new TermsQueryField.Builder().value(localIds.stream().map(FieldValue::of).toList()).build();

    return Optional.of(TermsQuery.of(query -> query.field(LOCAL_ID).terms(localIdTerms)));
  }

  private Optional<QueryVariant> applyFirstNameCriteria(final PatientSearchCriteria criteria) {
    return Optional.ofNullable(criteria.getFirstName())
        .filter(Predicate.not(String::isBlank))
        .map(
            value ->
                legalNameQuery(
                    FIRST_NAME_FIELD,
                    value,
                    FIRST_NAME_SOUNDEX_FIELD,
                    soundexEncoded(value, criteria.isDisableSoundex()),
                    settings.first()));
  }

  private Optional<QueryVariant> applyLastNameCriteria(final PatientSearchCriteria criteria) {
    return Optional.ofNullable(criteria.getLastName())
        .map(AdjustStrings::withoutHyphens)
        .filter(Predicate.not(String::isBlank))
        .map(
            value ->
                legalNameQuery(
                    LAST_NAME_FIELD,
                    value,
                    LAST_NAME_SOUNDEX_FIELD,
                    soundexEncoded(value, criteria.isDisableSoundex()),
                    settings.last()));
  }

  private String soundexEncoded(final String value, final boolean disabled) {
    return disabled ? "" : soundex.encode(value);
  }

  private QueryVariant legalNameQuery(
      final String field,
      final String value,
      final String encodedField,
      final String encodedValue,
      final PatientSearchSettings.NameBoost boost) {
    return BoolQuery.of(
        bool ->
            bool.should(
                    should ->
                        should.nested(
                            nested ->
                                nested
                                    .path(NAMES)
                                    .scoreMode(ChildScoreMode.Max)
                                    .query(
                                        query ->
                                            query.bool(
                                                legal ->
                                                    legal
                                                        .filter(
                                                            filter ->
                                                                filter.term(
                                                                    term ->
                                                                        term.field(NAME_USE_FIELD)
                                                                            .value(
                                                                                LEGAL_NAME_CODE)))
                                                        .must(
                                                            primary ->
                                                                primary.match(
                                                                    match ->
                                                                        match
                                                                            .field(field)
                                                                            .query(value)
                                                                            .boost(
                                                                                boost
                                                                                    .primary())))))))
                .should(
                    should ->
                        should.nested(
                            nested ->
                                nested
                                    .path(NAMES)
                                    .scoreMode(ChildScoreMode.Avg)
                                    .query(
                                        query ->
                                            query.simpleQueryString(
                                                nonPrimary ->
                                                    nonPrimary
                                                        .fields(field)
                                                        .query(WildCards.startsWith(value))
                                                        .boost(boost.nonPrimary())))))
                .should(
                    should ->
                        should.nested(
                            nested ->
                                nested
                                    .path(NAMES)
                                    .scoreMode(ChildScoreMode.Avg)
                                    .boost(boost.soundex())
                                    .query(
                                        query ->
                                            query.term(
                                                term ->
                                                    term.field(encodedField)
                                                        .value(encodedValue))))));
  }

  private Optional<QueryVariant> applyPhoneNumberCriteria(final PatientSearchCriteria criteria) {

    String number = AdjustStrings.withoutSpecialCharacters(criteria.getPhoneNumber());

    if (number != null && !number.isEmpty()) {

      return Optional.of(
          NestedQuery.of(
              nested ->
                  nested
                      .path(PHONES)
                      .scoreMode(ChildScoreMode.Avg)
                      .query(
                          query ->
                              query.wildcard(
                                  wildcard ->
                                      wildcard
                                          .field(PHONE_NUMBER_FIELD)
                                          .value(WildCards.contains(number))))));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyEmailCriteria(final PatientSearchCriteria criteria) {

    String email = criteria.getEmail();
    if (email != null && !email.isEmpty()) {

      return Optional.of(
          NestedQuery.of(
              nested ->
                  nested
                      .path(EMAILS)
                      .scoreMode(ChildScoreMode.Avg)
                      .query(
                          query ->
                              query.simpleQueryString(
                                  queryString ->
                                      queryString
                                          .fields(EMAIL_ADDRESS)
                                          .defaultOperator(Operator.And)
                                          .query(email)))));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyIdentificationCriteria(final PatientSearchCriteria criteria) {

    PatientSearchCriteria.Identification identification = criteria.getIdentification();

    String type = identification.identificationType();
    String value = AdjustStrings.withoutSpecialCharacters(identification.identificationNumber());

    if (type != null && (value != null && !value.isEmpty())) {
      return Optional.of(
          NestedQuery.of(
              nested ->
                  nested
                      .path(IDENTIFICATIONS)
                      .scoreMode(ChildScoreMode.Avg)
                      .query(
                          query ->
                              query.bool(
                                  bool ->
                                      bool.filter(
                                              filter ->
                                                  filter.term(
                                                      term ->
                                                          term.field("entity_id.typeCd.keyword")
                                                              .value(type)))
                                          .must(
                                              must ->
                                                  must.wildcard(
                                                      match ->
                                                          match
                                                              .field(IDENTIFICATION)
                                                              .caseInsensitive(true)
                                                              .value(
                                                                  WildCards.contains(value))))))));
    }
    return Optional.empty();
  }

  private Optional<QueryVariant> applyDateOfBirthCriteria(final PatientSearchCriteria criteria) {

    LocalDate dateOfBirth = criteria.getDateOfBirth();

    if (dateOfBirth != null) {
      String operator = resolveDateOperator(criteria.getDateOfBirthOperator());
      String value = FlexibleInstantConverter.toString(dateOfBirth);

      return switch (operator) {
        case "before" ->
            Optional.of(RangeQuery.of(range -> range.term(term -> term.field(BIRTHDAY).lt(value))));
        case "after" ->
            Optional.of(RangeQuery.of(range -> range.term(term -> term.field(BIRTHDAY).gt(value))));
        default -> Optional.of(MatchQuery.of(match -> match.field(BIRTHDAY).query(value)));
      };
    }

    DateCriteria dateCriteria = criteria.getBornOn();
    if (dateCriteria != null
        && dateCriteria.equals() != null
        && dateCriteria.equals().isCompleteDate()) {
      Equals equals = dateCriteria.equals();
      String value =
          FlexibleInstantConverter.toString(
              LocalDate.of(equals.year(), equals.month(), equals.day()));
      return Optional.of(MatchQuery.of(match -> match.field(BIRTHDAY).query(value)));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyDateOfBirthLowRangeCriteria(
      final PatientSearchCriteria criteria) {
    DateCriteria dateCriteria = criteria.getBornOn();
    if (dateCriteria == null) {
      return Optional.empty();
    }
    Between betweenDate = dateCriteria.between();
    if (betweenDate == null || betweenDate.from() == null) {
      return Optional.empty();
    }

    String value = FlexibleInstantConverter.toString(betweenDate.from());

    return Optional.of(RangeQuery.of(range -> range.term(term -> term.field(BIRTHDAY).gte(value))));
  }

  private Optional<QueryVariant> applyDateOfBirthHighRangeCriteria(
      final PatientSearchCriteria criteria) {
    DateCriteria dateCriteria = criteria.getBornOn();
    if (dateCriteria == null) {
      return Optional.empty();
    }
    Between betweenDate = dateCriteria.between();
    if (betweenDate == null || betweenDate.to() == null) {
      return Optional.empty();
    }

    String value = FlexibleInstantConverter.toString(betweenDate.to());

    return Optional.of(RangeQuery.of(range -> range.term(term -> term.field(BIRTHDAY).lte(value))));
  }

  private String resolveDateOperator(final String operator) {
    return operator == null ? "equal" : operator.toLowerCase();
  }

  private Optional<QueryVariant> applyStreetAddressCriteria(final PatientSearchCriteria criteria) {

    String address = criteria.getAddress();
    if (address != null && !address.isEmpty()) {

      String result = address.replace("(", "").replace(")", "");

      return Optional.of(
          NestedQuery.of(
              nested ->
                  nested
                      .path(ADDRESSES)
                      .scoreMode(ChildScoreMode.Avg)
                      .query(
                          query ->
                              query.simpleQueryString(
                                  queryString ->
                                      queryString
                                          .fields(STREET)
                                          .defaultOperator(Operator.And)
                                          .query(WildCards.startsWith(result))))));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyCityCriteria(final PatientSearchCriteria criteria) {

    String city = criteria.getCity();
    if (city != null && !city.isEmpty()) {

      return Optional.of(
          NestedQuery.of(
              nested ->
                  nested
                      .path(ADDRESSES)
                      .scoreMode(ChildScoreMode.Avg)
                      .query(
                          query ->
                              query.simpleQueryString(
                                  queryString ->
                                      queryString
                                          .fields(CITY)
                                          .defaultOperator(Operator.And)
                                          .query(WildCards.startsWith(city))))));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyZipcodeCriteria(final PatientSearchCriteria criteria) {

    String zipcode = criteria.getZip();
    if (zipcode != null && !zipcode.isEmpty()) {

      QueryVariant q =
          zipcode.length() < 5
              ? PrefixQuery.of(prefix -> prefix.field(ZIP_CODE).value(zipcode))
              : MatchQuery.of(match -> match.field(ZIP_CODE).query(zipcode));

      return Optional.of(
          NestedQuery.of(
              nested -> nested.path(ADDRESSES).scoreMode(ChildScoreMode.Avg).query(q._toQuery())));
    }

    return Optional.empty();
  }

  private Optional<QueryVariant> applyPatientIdFilter(final PatientSearchCriteria criteria) {
    return Optional.ofNullable(criteria.getFilter().id())
        .map(WildCards::contains)
        .map(
            value ->
                BoolQuery.of(
                    bool ->
                        bool.must(
                            query ->
                                query.queryString(
                                    simple ->
                                        simple
                                            .fields(SHORT_ID)
                                            .query(value)
                                            .defaultOperator(Operator.And)))));
  }

  private Optional<QueryVariant> applyPatientNameFilter(final PatientSearchCriteria criteria) {
    return Optional.ofNullable(criteria.getFilter().name())
        .map(TextCriteria::contains)
        .flatMap(TextCriteria::maybeContains)
        .map(value -> contains(NAMES, FULL_NAME, value));
  }

  private Script searchDateOfBirthScript(final String value) {
    return Script.of(
        script ->
            script
                .source(
                    "doc['birth_time'].size()!=0 && (doc['birth_time'].value.toString().substring(5,10)+'-'+doc['birth_time'].value.toString().substring(0,4)).contains('"
                        + value
                        + "')")
                .lang(PAINLESS));
  }

  Integer ageInYears(String value) {
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private Optional<QueryVariant> applyPatientAgeOrDateOfBirthFilter(
      final PatientSearchCriteria criteria) {
    String ageOrDateOfBirth = criteria.getFilter().ageOrDateOfBirth();
    if (ageOrDateOfBirth == null) {
      return Optional.empty();
    }

    final String value = ageOrDateOfBirth.replace("/", "-");
    Integer age = ageInYears(value);
    if (value.contains("-") || age == null) {
      return Optional.of(
          BoolQuery.of(
              bool ->
                  bool.should(
                      should -> should.script(s -> s.script(searchDateOfBirthScript(value))))));
    }

    return Optional.of(
        BoolQuery.of(
            bool ->
                bool.should(should -> should.script(s -> s.script(searchDateOfBirthScript(value))))
                    .should(
                        s ->
                            s.range(
                                RangeQuery.of(
                                    range ->
                                        range.term(
                                            term ->
                                                term.field(BIRTHDAY)
                                                    .gt("now-" + (age + 1) + "y/d")
                                                    .lt("now-" + age + "y/d")))))));
  }

  private Optional<QueryVariant> applyAddressFilter(final PatientSearchCriteria criteria) {
    return Optional.ofNullable(criteria.getFilter().address())
        .map(TextCriteria::contains)
        .flatMap(TextCriteria::maybeContains)
        .map(value -> contains(ADDRESSES, FULL_ADDRESS_FIELD, value));
  }

  private Optional<QueryVariant> applyPhoneFilter(final PatientSearchCriteria criteria) {
    return AdjustStrings.maybeOnlyDigits(criteria.getFilter().phone())
        .map(TextCriteria::contains)
        .flatMap(TextCriteria::maybeContains)
        .map(value -> contains(PHONES, PHONE_NUMBER_FIELD, value));
  }

  private Optional<QueryVariant> applyEmailFilter(final PatientSearchCriteria criteria) {
    return Optional.ofNullable(criteria.getFilter().email())
        .map(value -> value.replace("@", " "))
        .map(TextCriteria::contains)
        .flatMap(TextCriteria::maybeContains)
        .map(value -> contains(EMAILS, EMAIL_ADDRESS, value));
  }

  private Optional<QueryVariant> applyIdentificationFilter(final PatientSearchCriteria criteria) {
    return AdjustStrings.maybeWithoutSpecialCharacters(criteria.getFilter().identification())
        .map(TextCriteria::contains)
        .flatMap(TextCriteria::maybeContains)
        .map(value -> contains(IDENTIFICATIONS, IDENTIFICATION, value));
  }
}
