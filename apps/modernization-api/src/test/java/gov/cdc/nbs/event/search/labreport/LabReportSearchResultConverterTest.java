package gov.cdc.nbs.event.search.labreport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class LabReportSearchResultConverterTest {

  @Test
  void should_convert_to_result() {

    SearchableLabReport searchable =
        new SearchableLabReport(
            1019L,
            "class-code-value",
            "mood-value",
            "program-area-value",
            "jurisdiction-value",
            971L,
            "pregnancy-status",
            "local-value",
            LocalDate.of(2002, Month.FEBRUARY, 17),
            LocalDate.of(2002, Month.MARCH, 19),
            LocalDate.of(2003, Month.MAY, 23),
            179L,
            LocalDate.of(2005, Month.JULY, 29),
            857L,
            LocalDate.of(2007, Month.NOVEMBER, 2),
            1,
            "status-value",
            "entry-value");

    LabReportSearchResult converted =
        LabReportSearchResultConverter.convert(searchable, 101d, null);

    assertThat(converted.relevance()).isEqualTo(101f);
    assertThat(converted.id()).isEqualTo("1019");
    assertThat(converted.jurisdictionCd()).isEqualTo("jurisdiction-value");
    assertThat(converted.localId()).isEqualTo("local-value");
    assertThat(converted.addTime()).isEqualTo("2005-07-29");
  }

  @Test
  void should_convert_to_result_with_default_score_when_score_is_null() {
    SearchableLabReport searchable = mock(SearchableLabReport.class);
    when(searchable.identifier()).thenReturn(509L);
    when(searchable.people()).thenReturn(Collections.emptyList());
    when(searchable.tests()).thenReturn(Collections.emptyList());
    when(searchable.associated()).thenReturn(Collections.emptyList());
    when(searchable.organizations()).thenReturn(Collections.emptyList());

    LabReportSearchResult converted =
        LabReportSearchResultConverter.convert(searchable, null, null);

    assertThat(converted.relevance()).isZero();
  }

  @Test
  void should_convert_to_result_with_patient() {
    SearchableLabReport searchable = mock(SearchableLabReport.class);
    when(searchable.identifier()).thenReturn(509L);
    when(searchable.tests()).thenReturn(Collections.emptyList());
    when(searchable.associated()).thenReturn(Collections.emptyList());
    when(searchable.organizations()).thenReturn(Collections.emptyList());

    when(searchable.people())
        .thenReturn(
            List.of(
                new SearchableLabReport.Person.Patient(
                    443L,
                    "local-one-value",
                    "type-one-value",
                    "subject-type-one-value",
                    "first-name-one-value",
                    "last-name-one-value",
                    "gender-value",
                    LocalDate.of(2009, Month.NOVEMBER, 13))));

    LabReportSearchResult converted =
        LabReportSearchResultConverter.convert(searchable, null, null);

    assertThat(converted.personParticipations())
        .satisfiesExactly(
            actual ->
                assertAll(
                    () -> assertThat(actual.birthTime()).isEqualTo("2009-11-13"),
                    () -> assertThat(actual.currSexCd()).isEqualTo("gender-value"),
                    () -> assertThat(actual.typeCd()).isEqualTo("type-one-value"),
                    () -> assertThat(actual.firstName()).isEqualTo("first-name-one-value"),
                    () -> assertThat(actual.lastName()).isEqualTo("last-name-one-value"),
                    () -> assertThat(actual.personCd()).isEqualTo("PAT"),
                    () -> assertThat(actual.personParentUid()).isEqualTo(443L),
                    () -> assertThat(actual.local()).isEqualTo("local-one-value")));
  }

  @Test
  void should_convert_to_result_with_provider() {
    SearchableLabReport searchable = mock(SearchableLabReport.class);
    when(searchable.identifier()).thenReturn(509L);
    when(searchable.tests()).thenReturn(Collections.emptyList());
    when(searchable.associated()).thenReturn(Collections.emptyList());
    when(searchable.organizations()).thenReturn(Collections.emptyList());

    when(searchable.people())
        .thenReturn(
            List.of(
                new SearchableLabReport.Person.Provider(
                    857L,
                    "type-two-value",
                    "subject-type-two-value",
                    "first-name-two-value",
                    "last-name-two-value")));

    LabReportSearchResult converted =
        LabReportSearchResultConverter.convert(searchable, null, null);

    assertThat(converted.personParticipations())
        .satisfiesExactly(
            actual ->
                assertAll(
                    () -> assertThat(actual.birthTime()).isNull(),
                    () -> assertThat(actual.currSexCd()).isNull(),
                    () -> assertThat(actual.typeCd()).isEqualTo("type-two-value"),
                    () -> assertThat(actual.firstName()).isEqualTo("first-name-two-value"),
                    () -> assertThat(actual.lastName()).isEqualTo("last-name-two-value"),
                    () -> assertThat(actual.personCd()).isEqualTo("PRV"),
                    () -> assertThat(actual.personParentUid()).isEqualTo(857L),
                    () -> assertThat(actual.local()).isNull()));
  }

  @Test
  void should_convert_to_result_with_organizations() {
    SearchableLabReport searchable = mock(SearchableLabReport.class);
    when(searchable.identifier()).thenReturn(509L);
    when(searchable.people()).thenReturn(Collections.emptyList());
    when(searchable.tests()).thenReturn(Collections.emptyList());
    when(searchable.associated()).thenReturn(Collections.emptyList());

    when(searchable.organizations())
        .thenReturn(
            List.of(
                new SearchableLabReport.Organization(
                    787L, "type-one-value", "subject-type-one-value", "name-one-value"),
                new SearchableLabReport.Organization(
                    829L, "type-two-value", "subject-type-two-value", "name-two-value")));

    LabReportSearchResult converted =
        LabReportSearchResultConverter.convert(searchable, null, null);

    assertThat(converted.organizationParticipations())
        .satisfiesExactly(
            actual ->
                assertAll(
                    () -> assertThat(actual.typeCd()).isEqualTo("type-one-value"),
                    () -> assertThat(actual.name()).isEqualTo("name-one-value")),
            actual ->
                assertAll(
                    () -> assertThat(actual.typeCd()).isEqualTo("type-two-value"),
                    () -> assertThat(actual.name()).isEqualTo("name-two-value")));
  }

  @Test
  void should_convert_to_result_with_observations() {

    SearchableLabReport searchable = mock(SearchableLabReport.class);
    when(searchable.identifier()).thenReturn(509L);
    when(searchable.people()).thenReturn(Collections.emptyList());
    when(searchable.organizations()).thenReturn(Collections.emptyList());
    when(searchable.tests()).thenReturn(Collections.emptyList());
    when(searchable.associated()).thenReturn(Collections.emptyList());

    when(searchable.tests())
        .thenReturn(
            List.of(
                new SearchableLabReport.LabTest(
                    "name-one-value", "result-one-value", "alternative-one-value"),
                new SearchableLabReport.LabTest(
                    "name-two-value", "result-two-value", "alternative-two-value")));

    LabReportSearchResult converted =
        LabReportSearchResultConverter.convert(searchable, null, null);

    assertThat(converted.observations())
        .satisfiesExactly(
            actual ->
                assertAll(
                    () -> assertThat(actual.cdDescTxt()).isEqualTo("name-one-value"),
                    () -> assertThat(actual.altCd()).isEqualTo("alternative-one-value"),
                    () -> assertThat(actual.displayName()).isEqualTo("result-one-value")),
            actual ->
                assertAll(
                    () -> assertThat(actual.cdDescTxt()).isEqualTo("name-two-value"),
                    () -> assertThat(actual.altCd()).isEqualTo("alternative-two-value"),
                    () -> assertThat(actual.displayName()).isEqualTo("result-two-value")));
  }

  @Test
  void should_convert_to_result_without_associated_investigations() {
    SearchableLabReport searchable = mock(SearchableLabReport.class);
    when(searchable.identifier()).thenReturn(509L);
    when(searchable.people()).thenReturn(Collections.emptyList());
    when(searchable.organizations()).thenReturn(Collections.emptyList());
    when(searchable.tests()).thenReturn(Collections.emptyList());

    LabReportSearchResult converted =
        LabReportSearchResultConverter.convert(searchable, null, null);

    assertThat(converted.associatedInvestigations()).isEmpty();
  }
}
