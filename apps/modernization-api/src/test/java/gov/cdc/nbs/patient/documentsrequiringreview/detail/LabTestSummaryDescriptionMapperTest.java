package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LabTestSummaryDescriptionMapperTest {

  @Test
  void should_not_map_to_description_when_name_not_present() {

    LabTestSummary summary = mock(LabTestSummary.class);

    Optional<DocumentRequiringReview.Description> actual = LabTestSummaryDescriptionMapper.maybeMap(summary);

    assertThat(actual).isEmpty();
  }

  @Test
  void should_map_to_description() {

    LabTestSummary summary = mock(LabTestSummary.class);
    when(summary.name()).thenReturn("name-value");

    Optional<DocumentRequiringReview.Description> mapped = LabTestSummaryDescriptionMapper.maybeMap(summary);

    assertThat(mapped).hasValueSatisfying(
        actual -> assertAll(
            () -> assertThat(actual.title()).isEqualTo("name-value"),
            () -> assertThat(actual.value()).isEmpty()
        )

    );
  }

  @Test
  void should_map_with_coded_result() {

    LabTestSummary summary = mock(LabTestSummary.class);
    when(summary.name()).thenReturn("name-value");
    when(summary.coded()).thenReturn("coded-value");

    Optional<DocumentRequiringReview.Description> mapped = LabTestSummaryDescriptionMapper.maybeMap(summary);

    assertThat(mapped).hasValueSatisfying(
        actual -> assertAll(
            () -> assertThat(actual.title()).isEqualTo("name-value"),
            () -> assertThat(actual.value()).contains("coded-value")
        )

    );
  }

  @Test
  void should_map_with_numeric_result() {

    LabTestSummary summary = mock(LabTestSummary.class);
    when(summary.name()).thenReturn("name-value");
    when(summary.numeric()).thenReturn(new BigDecimal("45.5"));

    Optional<DocumentRequiringReview.Description> mapped = LabTestSummaryDescriptionMapper.maybeMap(summary);

    assertThat(mapped).hasValueSatisfying(
        actual -> assertAll(
            () -> assertThat(actual.title()).isEqualTo("name-value"),
            () -> assertThat(actual.value()).isEqualTo("45.5")
        )

    );
  }

  @Test
  void should_map_numeric_result_with_unit() {

    LabTestSummary summary = mock(LabTestSummary.class);
    when(summary.name()).thenReturn("name-value");
    when(summary.numeric()).thenReturn(new BigDecimal("45.5"));
    when(summary.unit()).thenReturn("unit");

    Optional<DocumentRequiringReview.Description> mapped = LabTestSummaryDescriptionMapper.maybeMap(summary);

    assertThat(mapped).hasValueSatisfying(
        actual -> assertThat(actual.value()).isEqualTo("45.5 unit")
    );
  }

  @Test
  void should_map_numeric_result_with_range() {

    LabTestSummary summary = mock(LabTestSummary.class);
    when(summary.name()).thenReturn("name-value");
    when(summary.numeric()).thenReturn(new BigDecimal("45.5"));
    when(summary.high()).thenReturn("high");
    when(summary.low()).thenReturn("low");
    when(summary.status()).thenReturn("status");

    Optional<DocumentRequiringReview.Description> mapped = LabTestSummaryDescriptionMapper.maybeMap(summary);

    assertThat(mapped).hasValueSatisfying(
        actual -> assertThat(actual.value()).isEqualTo("45.5\nReference Range - (high-low) - (status)")
    );
  }

  @Test
  void should_not_map_range_without_numeric_result() {

    LabTestSummary summary = mock(LabTestSummary.class);
    when(summary.name()).thenReturn("name-value");
    when(summary.high()).thenReturn("high");
    when(summary.low()).thenReturn("low");
    when(summary.status()).thenReturn("status");

    Optional<DocumentRequiringReview.Description> mapped = LabTestSummaryDescriptionMapper.maybeMap(summary);

    assertThat(mapped).hasValueSatisfying(
        actual -> assertThat(actual.value()).isEmpty()
    );
  }
}

