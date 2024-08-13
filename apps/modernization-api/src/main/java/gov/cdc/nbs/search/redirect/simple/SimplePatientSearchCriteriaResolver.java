package gov.cdc.nbs.search.redirect.simple;

import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.time.FlexibleLocalDateConverter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static gov.cdc.nbs.accumulation.Accumulator.accumulating;

class SimplePatientSearchCriteriaResolver {

  private static final String NBS_LAST_NAME = "patientSearchVO.lastName";
  private static final String NBS_FIRST_NAME = "patientSearchVO.firstName";
  private static final String NBS_DATE_OF_BIRTH = "patientSearchVO.birthTime";
  private static final String NBS_SEX = "patientSearchVO.currentSex";
  private static final String NBS_ID = "patientSearchVO.localID";

  private static SimplePatientSearchCriteria fromLastName(final String value) {
    return new SimplePatientSearchCriteria(value, null, null, null, null);
  }

  private static SimplePatientSearchCriteria fromFirstName(final String value) {
    return new SimplePatientSearchCriteria(null, value, null, null, null);
  }

  private static SimplePatientSearchCriteria fromDateOfBirth(final String value) {
    LocalDate birthday = FlexibleLocalDateConverter.fromString(value);
    return new SimplePatientSearchCriteria(null, null, birthday, null, null);
  }

  private static SimplePatientSearchCriteria fromGender(final String value) {
    Gender gender = Gender.resolve(value);
    return new SimplePatientSearchCriteria(null, null, null, new Option(gender.value(), gender.display()), null);
  }

  private static SimplePatientSearchCriteria fromId(final String value) {
    return new SimplePatientSearchCriteria(null, null, null, null, value);
  }

  Optional<SimplePatientSearchCriteria> resolve(final Map<String, String> criteria) {
    return Stream.of(
            maybe(criteria, NBS_LAST_NAME).map(SimplePatientSearchCriteriaResolver::fromLastName),
            maybe(criteria, NBS_FIRST_NAME).map(SimplePatientSearchCriteriaResolver::fromFirstName),
            maybe(criteria, NBS_DATE_OF_BIRTH).map(SimplePatientSearchCriteriaResolver::fromDateOfBirth),
            maybe(criteria, NBS_SEX).map(SimplePatientSearchCriteriaResolver::fromGender),
            maybe(criteria, NBS_ID).map(SimplePatientSearchCriteriaResolver::fromId)
        ).flatMap(Optional::stream)
        .collect(accumulating(SimplePatientSearchCriteriaMerger::merge));
  }

  private Optional<String> maybe(final Map<String, String> map, final String key) {
    String value = map.get(key);

    return StringUtils.hasText(value)
        ? Optional.of(value)
        : Optional.empty();
  }


}
