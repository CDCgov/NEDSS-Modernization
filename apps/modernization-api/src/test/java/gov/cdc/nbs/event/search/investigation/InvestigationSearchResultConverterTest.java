package gov.cdc.nbs.event.search.investigation;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InvestigationSearchResultConverterTest {

  @Test
  void should_convert_to_result() {
    SearchableInvestigation searchable = new SearchableInvestigation(
        971L,
        "class-code-value",
        "mood-value",
        "program-area-value",
        "jurisdiction-value",
        "jurisdiction-name-value",
        797L,
        "case-class-value",
        "case-type-value",
        "outbreak-value",
        "condition-name-value",
        "condition-value",
        "pregnancy-status-value",
        "local-value",
        313L,
        LocalDate.of(2002, Month.MARCH, 5),
        613L,
        LocalDate.of(2003, Month.APRIL, 7),
        LocalDate.of(2005, Month.MAY, 11),
        LocalDate.of(2007, Month.JUNE, 13),
        LocalDate.of(2011, Month.JULY, 17),
        "processing-value",
        "status-value",
        "notification-value",
        LocalDate.of(2013, Month.AUGUST, 19),
        "notification-status-value",
        "investigator-value",
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList());

    InvestigationSearchResult converted = InvestigationSearchResultConverter.convert(searchable, 56.3d);

    assertThat(converted.relevance()).isEqualTo(56.3d);
    assertThat(converted.id()).isEqualTo("971");
    assertThat(converted.cdDescTxt()).isEqualTo("condition-name-value");
    assertThat(converted.jurisdictionCd()).isEqualTo("jurisdiction-value");
    assertThat(converted.jurisdictionCodeDescTxt()).isEqualTo("jurisdiction-name-value");
    assertThat(converted.localId()).isEqualTo("local-value");
    assertThat(converted.addTime()).isEqualTo("2002-03-05");
    assertThat(converted.investigationStatusCd()).isEqualTo("status-value");
    assertThat(converted.notificationRecordStatusCd()).isEqualTo("notification-status-value");

    assertThat(converted.personParticipations()).isEmpty();
  }

  @Test
  void should_convert_to_result_with_default_score_when_score_is_null() {
    SearchableInvestigation searchable = mock(SearchableInvestigation.class);
    when(searchable.identifier()).thenReturn(677L);
    when(searchable.people()).thenReturn(Collections.emptyList());

    InvestigationSearchResult converted = InvestigationSearchResultConverter.convert(searchable, null);

    assertThat(converted.relevance()).isZero();
  }

  @Test
  void should_convert_to_result_with_patient() {
    SearchableInvestigation searchable = mock(SearchableInvestigation.class);
    when(searchable.identifier()).thenReturn(1021L);

    when(searchable.people()).thenReturn(
        List.of(
            new SearchableInvestigation.Person.Patient(
                443L,
                "local-one-value",
                "type-one-value",
                "subject-type-one-value",
                "first-name-one-value",
                "last-name-one-value",
                "gender-value",
                LocalDate.of(2009, Month.NOVEMBER, 13))));

    InvestigationSearchResult converted = InvestigationSearchResultConverter.convert(searchable, null);

    assertThat(converted.personParticipations()).satisfiesExactly(
        actual -> assertAll(
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
    SearchableInvestigation searchable = mock(SearchableInvestigation.class);
    when(searchable.identifier()).thenReturn(509L);

    when(searchable.people()).thenReturn(
        List.of(
            new SearchableInvestigation.Person.Provider(
                857L,
                "type-two-value",
                "first-name-two-value",
                "last-name-two-value")));

    InvestigationSearchResult converted = InvestigationSearchResultConverter.convert(searchable, null);

    assertThat(converted.personParticipations()).satisfiesExactly(
        actual -> assertAll(
            () -> assertThat(actual.birthTime()).isNull(),
            () -> assertThat(actual.currSexCd()).isNull(),
            () -> assertThat(actual.typeCd()).isEqualTo("type-two-value"),
            () -> assertThat(actual.firstName()).isEqualTo("first-name-two-value"),
            () -> assertThat(actual.lastName()).isEqualTo("last-name-two-value"),
            () -> assertThat(actual.personCd()).isEqualTo("PRV"),
            () -> assertThat(actual.personParentUid()).isEqualTo(857L),
            () -> assertThat(actual.local()).isNull()));
  }
}
