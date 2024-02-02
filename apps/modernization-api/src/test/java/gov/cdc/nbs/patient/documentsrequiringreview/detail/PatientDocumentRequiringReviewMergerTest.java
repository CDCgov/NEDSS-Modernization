package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PatientDocumentRequiringReviewMergerTest {


  @Test
  void should_merge_with_values_of_current_only() {

    DocumentRequiringReview current = new DocumentRequiringReview(
        971L,
        "current-local",
        "current-type",
        Instant.parse("2023-12-26T14:32:34Z"),
        Instant.parse("2014-01-27T14:32:34Z"),
        true,
        false,
        null,
        List.of()
    );

    DocumentRequiringReview next = new DocumentRequiringReview(
        971L,
        "next-local",
        "next-type",
        Instant.parse("2022-03-25T14:32:34Z"),
        Instant.parse("2022-02-27T15:32:34Z"),
        false,
        true,
        null,
        List.of()
    );

    PatientDocumentRequiringReviewMerger merger = new PatientDocumentRequiringReviewMerger();

    DocumentRequiringReview actual = merger.merge(current, next);

    assertThat(actual.id()).isEqualTo(971L);
    assertThat(actual.localId()).isEqualTo("current-local");
    assertThat(actual.type()).isEqualTo("current-type");
    assertThat(actual.eventDate()).isEqualTo("2023-12-26T14:32:34Z");
    assertThat(actual.dateReceived()).isEqualTo("2014-01-27T14:32:34Z");
    assertThat(actual.isElectronic()).isTrue();
    assertThat(actual.isUpdate()).isFalse();

  }

  @Test
  void should_merge_with_reporting_facility_of_the_current_only() {
    DocumentRequiringReview current = new DocumentRequiringReview(
        971L,
        "current-local",
        "current-type",
        null,
        Instant.now(),
        true,
        false,
        new DocumentRequiringReview.FacilityProviders(
            new DocumentRequiringReview.ReportingFacility("current-reporting-facility"),
            null,
            null
        ),
        List.of()
    );

    DocumentRequiringReview next = new DocumentRequiringReview(
        971L,
        "next-local",
        "next-type",
        null,
        Instant.now(),
        false,
        true,
        new DocumentRequiringReview.FacilityProviders(
            new DocumentRequiringReview.ReportingFacility("next-reporting-facility"),
            new DocumentRequiringReview.OrderingProvider("next-ordering-provider"),
            new DocumentRequiringReview.SendingFacility("next-sending-facility")
        ),
        List.of()
    );

    PatientDocumentRequiringReviewMerger merger = new PatientDocumentRequiringReviewMerger();

    DocumentRequiringReview actual = merger.merge(current, next);

    assertThat(actual).extracting(DocumentRequiringReview::facilityProviders)
        .satisfies(providers -> assertAll(
                () -> assertThat(providers.getOrderingProvider()).isNull(),
                () -> assertThat(providers.getSendingFacility()).isNull()
            )
        ).extracting(DocumentRequiringReview.FacilityProviders::getReportingFacility)
        .satisfies(
            reporting -> assertThat(reporting.name()).isEqualTo("current-reporting-facility")
        );
  }

  @Test
  void should_merge_with_ordering_provider_of_the_current_only() {
    DocumentRequiringReview current = new DocumentRequiringReview(
        971L,
        "current-local",
        "current-type",
        null,
        Instant.now(),
        true,
        false,
        new DocumentRequiringReview.FacilityProviders(
            null,
            new DocumentRequiringReview.OrderingProvider("current-ordering-provider"),
            null
        ),
        List.of()
    );

    DocumentRequiringReview next = new DocumentRequiringReview(
        971L,
        "next-local",
        "next-type",
        null,
        Instant.now(),
        false,
        true,
        new DocumentRequiringReview.FacilityProviders(
            new DocumentRequiringReview.ReportingFacility("next-reporting-facility"),
            new DocumentRequiringReview.OrderingProvider("next-ordering-provider"),
            new DocumentRequiringReview.SendingFacility("next-sending-facility")
        ),
        List.of()
    );

    PatientDocumentRequiringReviewMerger merger = new PatientDocumentRequiringReviewMerger();

    DocumentRequiringReview actual = merger.merge(current, next);

    assertThat(actual).extracting(DocumentRequiringReview::facilityProviders)
        .satisfies(providers -> assertAll(
                () -> assertThat(providers.getReportingFacility()).isNull(),
                () -> assertThat(providers.getSendingFacility()).isNull()
            )
        )
        .extracting(DocumentRequiringReview.FacilityProviders::getOrderingProvider)
        .satisfies(
            provider -> assertThat(provider.name()).isEqualTo("current-ordering-provider")
        );
  }

  @Test
  void should_merge_with_sending_facility_of_the_current_only() {
    DocumentRequiringReview current = new DocumentRequiringReview(
        971L,
        "current-local",
        "current-type",
        null,
        Instant.now(),
        true,
        false,
        new DocumentRequiringReview.FacilityProviders(
            null,
            null,
            new DocumentRequiringReview.SendingFacility("current-sending-facility")
        ),
        List.of()
    );

    DocumentRequiringReview next = new DocumentRequiringReview(
        971L,
        "next-local",
        "next-type",
        null,
        Instant.now(),
        false,
        true,
        new DocumentRequiringReview.FacilityProviders(
            new DocumentRequiringReview.ReportingFacility("next-reporting-facility"),
            new DocumentRequiringReview.OrderingProvider("next-ordering-provider"),
            new DocumentRequiringReview.SendingFacility("next-sending-facility")
        ),
        List.of()
    );

    PatientDocumentRequiringReviewMerger merger = new PatientDocumentRequiringReviewMerger();

    DocumentRequiringReview actual = merger.merge(current, next);

    assertThat(actual).extracting(DocumentRequiringReview::facilityProviders)
        .satisfies(providers -> assertAll(
                () -> assertThat(providers.getReportingFacility()).isNull(),
                () -> assertThat(providers.getOrderingProvider()).isNull()
            )
        ).extracting(DocumentRequiringReview.FacilityProviders::getSendingFacility)
        .satisfies(
            facility -> assertThat(facility.name()).isEqualTo("current-sending-facility")
        );
  }

  @Test
  void should_merge_with_descriptions_from_both() {
    DocumentRequiringReview current = new DocumentRequiringReview(
        971L,
        "current-local",
        "current-type",
        null,
        Instant.now(),
        true,
        false,
        new DocumentRequiringReview.FacilityProviders(),
        List.of(
            new DocumentRequiringReview.Description(
                "current-one-title",
                "current-one-value"
            ),
            new DocumentRequiringReview.Description(
                "current-two-title",
                "current-two-value"
            )
        )
    );

    DocumentRequiringReview next = new DocumentRequiringReview(
        971L,
        "next-local",
        "next-type",
        null,
        Instant.now(),
        false,
        true,
        new DocumentRequiringReview.FacilityProviders(),
        List.of(
            new DocumentRequiringReview.Description(
                "next-one-title",
                "next-one-value"
            ),
            new DocumentRequiringReview.Description(
                "next-two-title",
                "next-two-value"
            ),
            new DocumentRequiringReview.Description(
                "next-three-title",
                "next-three-value"
            )
        )
    );

    PatientDocumentRequiringReviewMerger merger = new PatientDocumentRequiringReviewMerger();

    DocumentRequiringReview merged = merger.merge(current, next);

    assertThat(merged.descriptions()).satisfiesExactlyInAnyOrder(
        actual -> assertThat(actual)
            .returns(
                "current-one-title",
                DocumentRequiringReview.Description::title
            ).returns(
                "current-one-value",
                DocumentRequiringReview.Description::value
            ),
        actual -> assertThat(actual)
            .returns(
                "current-two-title",
                DocumentRequiringReview.Description::title
            ).returns(
                "current-two-value",
                DocumentRequiringReview.Description::value
            ),
        actual -> assertThat(actual)
            .returns(
                "next-one-title",
                DocumentRequiringReview.Description::title
            ).returns(
                "next-one-value",
                DocumentRequiringReview.Description::value
            ),
        actual -> assertThat(actual)
            .returns(
                "next-two-title",
                DocumentRequiringReview.Description::title
            ).returns(
                "next-two-value",
                DocumentRequiringReview.Description::value
            ),
        actual -> assertThat(actual)
            .returns(
                "next-three-title",
                DocumentRequiringReview.Description::title
            ).returns(
                "next-three-value",
                DocumentRequiringReview.Description::value
            )
    );
  }
}
