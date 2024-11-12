package gov.cdc.nbs.search.redirect.simple;

import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.time.FlexibleLocalDateConverter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

class SimplePatientSearchCriteriaResolver {

  private static final String NBS_LAST_NAME = "patientSearchVO.lastName";
  private static final String NBS_FIRST_NAME = "patientSearchVO.firstName";
  private static final String NBS_DATE_OF_BIRTH = "patientSearchVO.birthTime";
  private static final String NBS_SEX = "patientSearchVO.currentSex";
  private static final String NBS_ID = "patientSearchVO.localID";
  private static final String TYPE = "patientSearchVO.actType";
  private static final String IDENTIFIER = "patientSearchVO.actId";

  private static final String ABCS_CASE_ID = "P10000";
  private static final String INVESTIGATION_ID = "P10001";
  private static final String LAB_ID = "P10002";
  private static final String MORBIDITY_REPORT_ID = "P10003";
  private static final String STATE_CASE_ID = "P10004";
  private static final String TREATMENT_ID = "P10005";
  private static final String VACCINATION_ID = "P10006";
  private static final String CITY_COUNTY_CASE_ID = "P10008";
  private static final String ACCESSION_NUMBER_ID = "P10009";
  private static final String DOCUMENT_ID = "P10010";
  private static final String NOTIFICATION_ID = "P10013";

  private String fromIdentifier(final Map<String, String> criteria, final String type) {
    return maybe(criteria, TYPE).filter(type::equals).flatMap(s -> maybe(criteria, IDENTIFIER))
        .orElse(null);
  }

  Optional<SimplePatientSearchCriteria> resolve(final Map<String, String> criteria) {
    String last = maybe(criteria, NBS_LAST_NAME)
        .orElse(null);

    String first = maybe(criteria, NBS_FIRST_NAME)
        .orElse(null);

    LocalDate dateOfBirth = maybe(criteria, NBS_DATE_OF_BIRTH)
        .map(FlexibleLocalDateConverter::fromString)
        .orElse(null);

    Option gender = maybe(criteria, NBS_SEX).map(Gender::resolve)
        .map(found -> new Option(found.value(), found.display()))
        .orElse(null);

    String id = maybe(criteria, NBS_ID).orElse(null);

    String morbidity = fromIdentifier(criteria, MORBIDITY_REPORT_ID);
    String document = fromIdentifier(criteria, DOCUMENT_ID);
    String stateCase = fromIdentifier(criteria, STATE_CASE_ID);
    String abcCase = fromIdentifier(criteria, ABCS_CASE_ID);
    String cityCountyCase = fromIdentifier(criteria, CITY_COUNTY_CASE_ID);
    String notification = fromIdentifier(criteria, NOTIFICATION_ID);
    String labReport = fromIdentifier(criteria, LAB_ID);
    String accessionNumber = fromIdentifier(criteria, ACCESSION_NUMBER_ID);
    String investigation = fromIdentifier(criteria, INVESTIGATION_ID);
    String treatment = fromIdentifier(criteria, TREATMENT_ID);
    String vaccination = fromIdentifier(criteria, VACCINATION_ID);

    return anyExist(last, first, dateOfBirth, gender, id, morbidity, document, stateCase, abcCase, cityCountyCase,
        notification, labReport, accessionNumber, investigation, treatment, vaccination)
        ? Optional.of(
        new SimplePatientSearchCriteria(
            last,
            first,
            dateOfBirth,
            gender,
            id,
            morbidity,
            document,
            stateCase,
            abcCase,
            cityCountyCase,
            notification,
            labReport,
            accessionNumber,
            investigation,
            treatment,
            vaccination
        )
    )
        : Optional.empty();
  }

  private static boolean anyExist(final Object first, Object... rest) {
    if (first != null) {
      return true;
    }

    for (Object o : rest) {
      if (o != null) {
        return true;
      }
    }
    return false;
  }


  private Optional<String> maybe(final Map<String, String> map, final String key) {
    String value = map.get(key);

    return StringUtils.hasText(value)
        ? Optional.of(value)
        : Optional.empty();
  }
}
