package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class LabTestSummaryDescriptionMapper {

  static Optional<DocumentRequiringReview.Description> maybeMap(final LabTestSummary summary) {
    return summary.name() == null ? Optional.empty() : Optional.of(map(summary));
  }


  private static DocumentRequiringReview.Description map(final LabTestSummary summary) {
    String title = summary.name();
    String numeric = resolveNumericDisplay(summary);
    String value = Stream.of(summary.coded(), numeric)
        .filter(Objects::nonNull)
        .collect(Collectors.joining("\n"));

    return new DocumentRequiringReview.Description(
        title,
        value
    );
  }

  private static String resolveNumericDisplay(final LabTestSummary summary) {

    StringBuilder builder = new StringBuilder();

    if (summary.numeric() != null) {
      builder.append(summary.numeric().toPlainString());

      if (summary.unit() != null) {
        builder.append(" ").append(summary.unit());
      }

      if (!builder.isEmpty()) {
        String range = resolveRangeDisplay(summary);

        if (range != null) {
          builder.append("\n")
              .append(range);
        }
      }

    }
    return builder.isEmpty() ? null : builder.toString();
  }

  private static String resolveRangeDisplay(final LabTestSummary summary) {
    StringBuilder builder = new StringBuilder();

    if (summary.high() != null || summary.low() != null) {
      String range = Stream.of(summary.low(), summary.high())
          .filter(Objects::nonNull)
          .collect(
              Collectors.joining(
                  "-",
                  "Reference Range - (",
                  ")"
              )
          );

      builder.append(range);

      if (summary.status() != null) {
        builder.append(" - (")
            .append(summary.status())
            .append(")");
      }
    }

    return builder.isEmpty() ? null : builder.toString();
  }

  private LabTestSummaryDescriptionMapper() {
  }

}
